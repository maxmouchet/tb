#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int isValid(char ISBN[]) {
  int weight = 10;
  int sum    = 0;

  int valid  = 0; // 0 if invalid, 1 if valid

  int length = strlen(ISBN);
  if (length == 13) {
    
    for (int i = 0; i < length; i++) {
      if ((ISBN[i] != '-') && (ISBN[i] != ' ') && (ISBN[i] != 'X')) {
	int digit = ISBN[i] - '0'; // ASCII to integer
	sum += digit * weight--;
      } else if (ISBN[i] == 'X') {
	sum += 10;
      }
    }

    if (sum % 11 == 0) {
      valid = 1;
    }

  }

  return valid;
}

int main(int argc, char *argv[]) {
  if (argc != 2) {
    printf("Usage: %s ISBN\n", argv[0]);
    return 1;
  }

  char *ISBN = argv[1];

  if(isValid(ISBN)) {
    printf("%s is a valid ISBN code\n", ISBN);
  } else {
    printf("%s is not a valid ISBN code\n", ISBN);
  }
  
  return 0;
}
