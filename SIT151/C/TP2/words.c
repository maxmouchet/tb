#include <ctype.h>
#include "words.h"

int nextWord(FILE *file, char *dst) {
  int i;
  char c;

  while (!(isalpha(c = (char)fgetc(file)))) {
    if  (c == EOF) {
      dst[0] = '\0';
      return 0;
    }
  }

  dst[0] = (char)(c & 0xDF);
  i = 1;

  while (isalpha(c = (char)fgetc(file))) {
    dst[i] = (char)(c & 0xDF);
    i++;
  }

  dst[i] = '\0';
  return 1;
}
