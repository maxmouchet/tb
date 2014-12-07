#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

// Functions are documented in headers
#include "words.h"
#include "lexique_liste.h"

#define USE_PREPEND 0

Information * findWord(Dictionary dictionary, char *word) {
  Information *nextInformation = dictionary->next;

  while (nextInformation != NULL) {
    if (strcmp(nextInformation->word, word) == 0) {
      return nextInformation;
    }
    nextInformation = nextInformation->next;
  }

  return NULL;
}

void appendInformation(Dictionary dictionary, Information *information) {
  Information *nextInformation = dictionary->next;

  if (nextInformation == NULL) {
    dictionary->next = information;
  } else {
    while (nextInformation->next != NULL) {
      nextInformation = nextInformation->next;
    }
    nextInformation->next = information;
  }
}

void prependInformation(Dictionary dictionary, Information *information) {
  information->next = dictionary->next;
  dictionary->next = information;
}

void updateDictionary(Dictionary dictionary, char *word) {
  Information *information = findWord(dictionary,  word);

  if (information == NULL) {
    information = malloc(sizeof(Information));

    information->word = strdup(word);
    information->occurences = 1;
    information->next = NULL;
   
    #if USE_PREPEND
    prependInformation(dictionary, information);
    #else
    appendInformation(dictionary, information);
    #endif
  } else {
    information->occurences++;
  }
}

Dictionary buildDictionary(FILE *file) {
  char word[1024];
  
  Dictionary dictionary = malloc(sizeof(Information));
  dictionary->next = NULL;

  while (nextWord(file, word) != 0) {
    updateDictionary(dictionary,  word);
  }

  return dictionary;
}

void printDictionary(Dictionary dictionary) {
  Information *nextInformation = dictionary->next;

  while (nextInformation != NULL) {
    printf("%s: %d\n", nextInformation->word, nextInformation->occurences);
    nextInformation = nextInformation->next;
  }
}

int main(int argc, char *argv[]) {
  FILE *textFile;

  if (argc != 2) {
    printf("Usage: %s TEXT\n", argv[0]);
    return 1;
  }

  if ((textFile = fopen(argv[1], "r")) == NULL) {
    printf("Failed to open %s\n", argv[1]);
    return 1;
  }

  Dictionary dictionary = buildDictionary(textFile);
  printDictionary(dictionary);

  free(dictionary);
}
