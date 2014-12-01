#ifndef LEXIQUE_TABLEAU_H
#define LEXIQUE_TABLEAU_H

#include <stdio.h>

typedef struct {
  char *word;
  int occurences;
} Information;

typedef Information *Dictionary;

// Find a word in a dictionary
// Returns the index if found, -1 else
int findWord(Dictionary dictionary, int length, char *word);

// Add a new information to a dictionary if the word was not present
// or increment occurences if the word was already present
// Returns new word count
int updateDictionary(Dictionary dictionary, int length, char *word);

// Build a dictionary (word:count) from a file
// Returns dictionary word count
int buildDictionary(Dictionary dictionary, int length, FILE *file);

// Pretty-print the dictionary
void printDictionary(Dictionary dictionary, int length);

#endif
