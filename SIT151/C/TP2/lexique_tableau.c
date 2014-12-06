#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

// Functions are documented in headers
#include "words.h"
#include "lexique_tableau.h"

#define DICTIONARY_SIZE 16384

int findWord(Dictionary dictionary, int length, char *word) {
  for (int i = 0; i < length; i++) {
    if (strcmp(dictionary[i].word, word) == 0) {
      return i;
    }
  }

  return -1;
}

int updateDictionary(Dictionary dictionary, int length, char *word) {
  int index;

  if ((index = findWord(dictionary, length, word)) == -1) {
    Information information;

    information.word = strdup(word);
    information.occurences = 1;
    
    dictionary[length] = information;
    length++;
  } else {
    dictionary[index].occurences++;
  }

  return length;
}

int buildDictionary(Dictionary dictionary, int length, FILE *file) {
  char word[1024];

  while (nextWord(file, word) != 0) {
    length = updateDictionary(dictionary, length, word);
  }

  return length;
}

void printDictionary(Dictionary dictionary, int length) {
  int i = 0;
  int empty = 0;

  while (!empty && i < length) {
    printf("%s: %d\n", dictionary[i].word, dictionary[i].occurences);
    
    i++;

    if (dictionary[i].occurences == 0) {
      empty = 1;
    }
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
  
  Dictionary dictionary = calloc(DICTIONARY_SIZE, sizeof(Information));
  
  buildDictionary(dictionary, 0, textFile);
  printDictionary(dictionary, DICTIONARY_SIZE);

  free(dictionary);
}
