#include <stdlib.h>
#include <stdio.h>
#include <math.h>

typedef int *Polynome;

float evalPoly(Polynome p, int n, float x) {
  float result = 0;
  
  for (int i = 0; i < n; i++) {
    result += p[i] * pow(x, i);
  }

  return result;
}

int main() {
  Polynome p = calloc(3, sizeof(int));
  
  // p = 2 + 3x + 5x^2
  p[0] = 2;
  p[1] = 3;
  p[2] = 5;

  float result = evalPoly(p, 3, 4.5);

  printf("p(x) = %d + %dx + %dx^2\np(4.5) = %g\n", p[0], p[1], p[2], result);

  return 0;
}
