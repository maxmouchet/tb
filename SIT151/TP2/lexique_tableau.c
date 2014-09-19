#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

typedef struct {
  char *word;
  int occurences;
} Information;

typedef Information *Dictionary;

// Read the next word from file
// Returns 1 if a word was read, 0 else
int nextWord(FILE *file, char *dst) {
  int i;
  char c;

  while (!(isalpha(c = fgetc(file)))) {
    if  (c == EOF) {
      dst[0] = '\0';
      return 0;
    }
  }

  dst[0] = c & 0xDF;
  i = 1;

  while (isalpha(c = fgetc(file))) {
    dst[i] = c & 0xDF;
    i++;
  }

  dst[i] = '\0';
  return 1;
}

// Find a word in a dictionary
// Returns the index if found, -1 else
int findWord(Dictionary dictionary, int length, char *word) {

  for (int i = 0; i < length; i++) {
    if (strcmp(dictionary[i].word, word) == 0) {
      return i;
    }
  }

  return -1;
}

// Add a new information to a dictionary if the word was not present
// or increment occurences if the word was already present
// Returns new word count
int updateDictionary(Dictionary dictionary, int length, char *word) {
  int index;

  if ((index = findWord(dictionary, length, word)) == -1) {
    Information information;

    information.word = strcpy(malloc(sizeof(word)), word);
    information.occurences = 1;
    
    dictionary[length] = information;
    length++;
  } else {
    dictionary[index].occurences++;
  }

  return length;
}

// Build a dictionary (word:count) from a file
// Returns dictionary word count
int buildDictionary(Dictionary dictionary, int length, FILE *file) {
  char word[1024];

  while (nextWord(file, word) != 0) {
    length = updateDictionary(dictionary, length, word);
  }

  return length;
}

// Pretty-print the dictionary
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
  
  Dictionary dictionary = calloc(1000, sizeof(Information));
  int length = 0;

  length = buildDictionary(dictionary, length, textFile);
  printDictionary(dictionary, 1000);
}
