#ifndef LEXIQUE_LISTE_H
#define LEXIQUE_LISTE_H

#include <stdio.h>

typedef struct {
  char *word;
  int occurences;
  void *next;
} Information;

typedef Information *Dictionary;

// Find a word in a dictionary
// Returns a pointer to the information
Information * findWord(Dictionary dictionary, char *word);

// Append an information to a dictionary
// This is much faster than prepending because the probability of
// finding a duplicate word is decreasing when moving towards the
// end of the text
void appendInformation(Dictionary dictionary, Information *information);

// Prepend an information to a dictionary
void prependInformation(Dictionary dictionary, Information *information);

// Add a new information to a dictionary if the word was not present
// or increment occurences if the word was already present
void updateDictionary(Dictionary dictionary, char *word);

// Build a dictionary (word:count) from a file
// Returns a pointer to the dictionary
Dictionary buildDictionary(FILE *file);

// Pretty-print the dictionary
void printDictionary(Dictionary dictionary);

#endif
