#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

// Functions are documented in headers
#include "words.h"
#include "murmurhash3.h"
#include "lexique_hash.h"

#define BUCKETS_N 1024
#define USE_MURMUR3 1

int hashFunction(char *word) {
  #if USE_MURMUR3 == 1
  return murmur3(word, strlen(word)) % BUCKETS_N;
  #else
  // Really really basic hashing function :p
  return (int) word[0] % BUCKETS_N;
  #endif
}

Information * findWord(HashTable hashTable, char *word) {
  int hash = hashFunction(word);
  Bucket bucket = hashTable[hash];

  if (bucket != NULL) {
    Information *nextInformation = bucket->next;
    
    while (nextInformation != NULL) {
      if (strcmp(nextInformation->word, word) == 0) {
	return nextInformation;
      }
      nextInformation = nextInformation->next;
    }
  }

  return NULL;
}

void addInformation(HashTable hashTable, Information *information) {
  int hash = hashFunction(information->word);

  // Initialize bucket if empty
  if (hashTable[hash] == NULL) {
    hashTable[hash] = calloc(1, sizeof(Information));
  }

  Bucket bucket = hashTable[hash];

  information->next = bucket->next;
  bucket->next = information;
}

void updateTable(HashTable hashTable, char *word) {
  Information *information = findWord(hashTable, word);

  if (information == NULL) {
    information = malloc(sizeof(Information));

    information->word = strcpy(malloc(sizeof(*word)), word);
    information->occurences = 1;
    information->next = NULL;

    addInformation(hashTable, information);
  } else {
    information->occurences++;
  }
}

HashTable buildTable(FILE *file) {
  char word[1024];

  HashTable hashTable = calloc(BUCKETS_N, sizeof(Bucket));

  while (nextWord(file, word) != 0) {
    updateTable(hashTable, word);
  }

  return hashTable;
}

void printTable(HashTable hashTable) {
  for (int i = 0; i < BUCKETS_N; i++) {
    Bucket bucket = hashTable[i];
    
    if (bucket != NULL) {
      Information *nextInformation = bucket->next;

      while (nextInformation != NULL) {
	printf("%s: %d\n", nextInformation->word, nextInformation->occurences);
	nextInformation = nextInformation->next;
      }
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

  HashTable hashTable;

  hashTable = buildTable(textFile);
  printTable(hashTable);
}
