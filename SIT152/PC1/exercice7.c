#include <stdio.h>

void swap(int *a, int *b) {
  int c = *a;
  *a = *b;
  *b = c;
}

void printTab(int *tab, int n) {
  printf("tab[] =");
 
 for (int i = 0; i < n; i++) {
    printf(" %d", tab[i]);
  }

 printf("\n");
}

void triBulle(int *tab, int n) {
  int echange = 0;

  do {
    echange = 0;

    for (int i = 0; i < n - 1; i++) {
      if (tab[i] > tab[i+1]) {
	swap(&tab[i], &tab[i+1]);
	echange = 1;
      }
    }
  
    n--;
  } while(echange);
}



int main() {
  int tab[] = { 1, 5, 3, 6, 9, 645, 1, 4, 2, 567, 8 };

  printTab(tab, 11);
  triBulle(tab, 11);
  printTab(tab, 11);

  return 0;
}
