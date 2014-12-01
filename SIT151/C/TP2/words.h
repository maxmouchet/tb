#ifndef WORDS_H // Si la constante n'a pas été définie le fichier n'a jamais été inclus
#define WORDS_H

#include <stdio.h>

// Read the next word from file
// Returns 1 if a word was read, 0 else
int nextWord(FILE *file, char *dst);

#endif
