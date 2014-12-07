#ifndef LEXIQUE_BTREE_H
#define LEXIQUE_BTREE_H

#include <stdio.h>

typedef struct {
  char *word;
  int occurences;
  void *left;
  void *right;
} Information;

typedef Information *Node;

// Find a word in a tree
// Returns a pointer to the information
// or to the place the information should be inserted
// result indicates if the word was found or not
Information * findWord(Node node, char *word, int *result);

// Insert an information to a tree
void insertInformation(Node node, Information *information);

// Add a new information to a tree if the word was not present
// or increment occurences if the word was already present
void updateTree(Node node, char *word);

// Build a tree of information (word:count) from a file
// Returns a pointer to the tree
Node buildTree(FILE *file);

// Pretty-print the tree
void printTree(Node node);

#endif
