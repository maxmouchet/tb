#include <stdlib.h>
#include <stdio.h>

typedef int *Polynome;

Polynome addPoly(Polynome p, Polynome q, int n) {
  Polynome r = calloc(n, sizeof(int));

  for (int i = 0; i < n; i++) {
    r[i] = p[i] + q[i];
  }

  return r;
}

int main() {
  Polynome p = calloc(3, sizeof(int));
  Polynome q = calloc(3, sizeof(int));
  
  // p = 2 + 3x + 5x^2
  p[0] = 2;
  p[1] = 3;
  p[2] = 5;

  // q = 1 + 2x + 4x^2
  q[0] = 1;
  q[1] = 2;
  q[2] = 4;

  Polynome r = addPoly(p, q, 3);

  printf("%d + %dx + %dx^2 + %d + %dx + %dx^2 = %d + %dx + %dx^2\n", p[0], p[1], p[2], q[0], q[1], q[2], r[0], r[1], r[2]);

  return 0;
}
