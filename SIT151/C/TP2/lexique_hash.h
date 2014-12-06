#ifndef LEXIQUE_HASH_H
#define LEXIQUE_HASH_H

#include <stdio.h>

typedef struct {
  char *word;
  int occurences;
  void *next;
} Information;

// Represents a bucket in the hash table
typedef Information *Bucket;

// Associate an integer to a bucket
typedef Bucket *HashTable;

// Word -> Hash
int hashFunction(char *word);

// Find a word in a hash table
// Returns a pointer to the information
Information * findWord(HashTable hashTable, char *word);

// Add an information to the hash table
void addInformation(HashTable hashTable, Information *information);

// Create an information if the word is not present
// or increment occurences count
void updateTable(HashTable hashTable, char *word);

// Build a hashtable (word:count) from a file
// Returns a pointer to the table
HashTable buildTable(FILE *file);

// Pretty-print the hash table
void printTable(HashTable hashTable);

#endif
