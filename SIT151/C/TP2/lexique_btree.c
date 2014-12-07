#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

// Functions are documented in headers
#include "words.h"
#include "lexique_btree.h"

Information * findWord(Node node, char *word, int *found) {
  Node nextNode;

  int rank = strcmp(node->word, word);

  if (rank < 0) {
    // Word may be on the left
    nextNode = node->left;
  } else if (rank > 0) {
    // Word may be on the right
    nextNode = node->right;
  } else {
    // Word is on this node
    *found = 1;
    return node;
  }

  // Check if we are on a leaf
  if (nextNode == NULL) {
    // Word not found
    *found = 0;
    return node;
  } else {
    // Continue search
    return findWord(nextNode, word, found);
  }
}

void insertInformation(Node node, Information *information) {
  int rank = strcmp(node->word, information->word);
  
  if (rank < 0) {
    node->left = information;
  } else if (rank > 0) {
    node->right = information;
  }
}

void updateTree(Node node, char *word) {
  int found;
  Information *position = findWord(node, word, &found);

  if (found) {
    position->occurences++;
  } else {
    Information *information = malloc(sizeof(Information));

    information->word = strdup(word);
    information->occurences = 1;
    information->left = NULL;
    information->right = NULL;

    insertInformation(position, information);
  }
}

Node buildTree(FILE *file) {
  char word[1024];
  
  Node tree = malloc(sizeof(Information));
  tree->word = strdup("\0");
  tree->left = NULL;
  tree->right = NULL;

  while (nextWord(file, word) != 0) {
    updateTree(tree,  word);
  }

  return tree;
}

void printTree(Node node) {
  if (node == NULL) {
    return;
  }

  printf("%s: %d\n", node->word, node->occurences);
  printTree(node->left);
  printTree(node->right);
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

  Node tree = buildTree(textFile);
  printTree(tree);

  free(tree);
}
