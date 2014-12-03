#include <stdio.h>
#include <stdlib.h>

void printTab(int *tab, int n) {
  printf("tab[] =");
 
 for (int i = 0; i < n; i++) {
    printf(" %d", tab[i]);
  }

 printf("\n");
}

void triCasier(int *tab, int n) {
  int min = tab[0];
  int max = min;
  
  for (int i = 0; i < n; i++) {
    if (tab[i] > max)
      max = tab[i];
    if (tab[i] < min)
      min = tab[i];
  } 

  int length = max - min + 1;
  int *temp = calloc(length, sizeof(int));

  for (int i = 0; i < n; i++) {
    temp[tab[i]-min] = temp[tab[i]-min] + 1;
  }

  int j = 0;
  for (int i = 0; i < length; i++) {
    while (temp[i] > 0) {  
      tab[j] = min + i;
      temp[i]--;
      j++;
    }
  }
}

int main() {
  int tab[] = { 1, 5, 3, 6, 9, 645, 1, 4, 2, 567, 8 };

  printTab(tab, 11);
  triCasier(tab, 11);
  printTab(tab, 11);

  return 0;
}
