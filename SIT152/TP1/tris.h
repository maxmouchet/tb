
/* Divers */
#define TRUE 1
#define FALSE 0



/* Eléments d'analyse  */
   typedef struct
   {
      long int nb_echanges;
      long int nb_comparaisons;
      long int nb_copies;
   } structSondes;



/* -------------------------------------------------------------------------- */
/* echanger                                                                   */
/* void echanger(int *t, int n1, int n2)                                      */
/*                                                                            */
/* Echanger les éléments d'indice [n1] et [n2] du tableau d'entiers [t]       */
/*                                                                            */
/* Entrées :                                                                  */
/*   - [t]  : tableau d'entiers                                               */
/*   - [n1] : indice                                                          */
/*   - [n2] : indice                                                          */
/*                                                                            */
/* Modifications :                                                            */
/*   - [t]  : tableau d'entiers                                               */
/*                                                                            */
/* -------------------------------------------------------------------------- */

   void echanger(int *t, int n1, int n2);


/* Algorithmes de tris */

/* -------------------------------------------------------------------------- */
/* tri_[algo_tri]                                                             */
/* int tri_[algo_tri] (int *t, int n, structSondes *complexite)              */
/*                                                                            */
/* Trier le tableau d'entiers [t] à [n] éléments                              */
/* [algo_tri] : bulle_naif, bulle_bool, bulle_opt, selection, insertion       */
/*                                                                            */
/* Entrées :                                                                  */
/*   - [t] : tableau d'entiers                                                */
/*   - [n] : nombre d'entiers du tableau                                      */
/*                                                                            */
/* Modifications :                                                            */
/*   - [t] : tableau d'entiers                                                */
/*                                                                            */
/* Sorties :                                                                  */
/*   - [sondes] : structure résultat des sondes sur l'algorithme              */
/*            complexite->nb_comparaisons                                     */
/*            complexite->nb_echanges                                         */
/*            complexite->nb_copies                                           */
/* -------------------------------------------------------------------------- */

   structSondes tri_bulle_naif(int *t, int n);
   structSondes tri_bulle_bool(int *t, int n);
   structSondes tri_bulle_opt(int *t, int n);

   structSondes tri_insertion(int *t, int n);
   structSondes tri_selection(int *t, int n);
	
	
/* -------------------------------------------------------------------------- */
/* tri_[algo_tri]                                                             */
/* int tri_rapide (int *t, int gauche, int droite, structSondes *complexite) */
/*                                                                            */
/* Trier la portion du tableau d'entiers [t] comprise entre les indices       */
/* [gauche] et [droite]                                                       */
/*                                                                            */
/* Entrées :                                                                  */
/*   - [t] : tableau d'entiers                                                */
/*   - [gauche] : indice gauche                                               */
/*   - [gauche] : indice droit                                                */
/*                                                                            */
/* Modifications :                                                            */
/*   - [t] : tableau d'entiers                                                */
/*                                                                            */
/* Sorties :                                                                  */
/*   - [sondes] : structure résultat des sondes sur l'algorithme              */
/*            complexite->nb_comparaisons                                     */
/*            complexite->nb_echanges                                         */
/*            complexite->nb_copies                                           */
/* -------------------------------------------------------------------------- */

   structSondes tri_rapide(int *t, int gauche, int droite);


