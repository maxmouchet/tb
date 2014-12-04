#include "tris.h"

void echanger(int *t, int n1, int n2) {
  int c = t[n1];
  t[n1] = t[n2];
  t[n2] = c;
}

structSondes tri_bulle_naif(int *t, int n) {
  structSondes sonde = { 0, 0, 0 };

  for (int i = n; i >= 0; i--) {
    for (int j = 0; j < n - 1; j++) {
      
      sonde.nb_comparaisons++;
      if (t[j] > t[j+1]) {

	sonde.nb_echanges++;
	echanger(t, j, j+1);
      }
    }
  }

  return sonde;
}

structSondes tri_bulle_bool(int *t, int n) {
  structSondes sonde = { 0, 0, 0 };
  int echange = 0;

  do {
    echange = 0;

    for (int i = 0; i < n - 1; i++) {
      
      sonde.nb_comparaisons++;
      if (t[i] > t[i+1]) {

	sonde.nb_echanges++;
	echanger(t, i, i+1);
	echange = 1;
      }
    }
  } while(echange);

  return sonde;
}

structSondes tri_bulle_opt(int *t, int n) {
  structSondes sonde = { 0, 0, 0 };
  int echange = 0;

  do {
    echange = 0;

    for (int i = 0; i < n -1; i++) {
      
      sonde.nb_comparaisons++;
      if (t[i] > t[i+1]) {

	sonde.nb_echanges++;
	echanger(t, i, i+1);
	echange = 1;
      }
    }
  
    n--;
  } while(echange);

  return sonde;
}

structSondes tri_insertion(int *t, int n) {
  structSondes sonde = { 0, 0, 0 };
  int j, v;

  for (int i = 1; i < n; i++) {
    sonde.nb_copies++;
    v = t[i];

    sonde.nb_copies++;
    j = i;
    
    sonde.nb_comparaisons++;
    while (j > 0 && t[j-1] > v) {
      sonde.nb_copies++;
      t[j] = t[j-1];
      j--;
    }

    sonde.nb_copies++;
    t[j] = v;
  }

  return sonde;
}

structSondes tri_selection(int *t, int n) {
  structSondes sonde = { 0, 0, 0 };
  int min;
 
  for (int i = 0; i < n-1; i++) {
    sonde.nb_copies++;
    min = i;

    for (int j = i+1; j < n; j++) {

      sonde.nb_comparaisons++;
      if (t[j] < t[min]) {
	sonde.nb_copies++;
	min = j;
      }

    }

    sonde.nb_echanges++;
    echanger(t, i, min);
  }

  return sonde;
}

int partitionner(int *t, int gauche, int droite, structSondes *sonde) {
  sonde->nb_copies++;
  int j = gauche;
  
  sonde->nb_copies++;
  int pivot = t[gauche];

  for (int i = gauche + 1; i <= droite; i++) {

    sonde->nb_comparaisons++;
    if (t[i] < pivot) {
      j++;
      
      sonde->nb_echanges++;
      echanger(t, i, j);
    }
  }

  sonde->nb_echanges++;
  echanger(t, gauche, j);

  return j;
}

structSondes tri_rapide(int *t, int gauche, int droite) {
  structSondes sonde = { 0, 0, 0 };

  sonde.nb_comparaisons++;
  if (gauche < droite) {
    int pivot = partitionner(t, gauche, droite, &sonde);
    tri_rapide(t, gauche, pivot - 1);
    tri_rapide(t, pivot + 1, droite);
  }

  return sonde;
}
