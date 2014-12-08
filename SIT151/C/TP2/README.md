## SIT 151 / C / TP2

[*Maxime Mouchet*](https://github.com/maxmouchet), [*Yann Feunteun*](https://github.com/yafeunteun)  
& *Julian Guttierez* (pour l'ex. 6)

**Objectif:** réaliser un lexique contenant le nombre d'occurrences de chaque mot à partir d'un fichier.  
**Implémentations:** tableau, liste chaînée, hashtable, binary tree  

[Comparaison des algorithmes](#comparaison-des-algorithmes)  
[Comparaison des fonctions de hachage](#comparaison-des-fonctions-de-hachage)  
[Analyse du lexique par liste chaînée](#analyse-du-lexique-par-liste-chaînée)

### Comparaison des algorithmes

#### Résultats théoriques

Structure de données | Recherche                          |
---------------------|------------------------------------|
Tableau              |Θ(n)                                |
Liste chaînée        |Θ(n)                                |
Hashtable            |dépendant de la fonction de hashage |
Arbre binaire        |Θ(log(n))                           |

#### Résultats pratiques

Script usage: `Usage: ./benchmark.sh ALGORITHM FILES...`  
Configuration: `MBA 2013, Core i5@1.3GHz, 8GB DDR3, SSD`  
Compilation: `clang -O3`   

##### Mesures

File      | Words  | lexique_tableau (s) | lexique_liste (ajout) (s)     | lexique_hash (s)  | lexique_btree (s)
----------|--------|---------------------|-------------------------------|-------------------|-------------------
fic0.txt  | 69     | 0.00                | 0.00                          | 0.00              | 0.00
fic1.txt  | 585    | 0.00                | 0.00                          | 0.00              | 0.00
fic2.txt  | 1363   | 0.00                | 0.00                          | 0.00              | 0.00
fic3.txt  | 3664   | 0.00                | 0.00                          | 0.00              | 0.00
fic4.txt  | 8028   | 0.01                | 0.01                          | 0.00              | 0.00
fic5.txt  | 14978  | 0.02                | 0.02                          | 0.00              | 0.01
fic6.txt  | 22148  | 0.04                | 0.05                          | 0.01              | 0.01
fic7.txt  | 51333  | 0.17                | 0.19                          | 0.03              | 0.03
fic8.txt  | 124062 | 0.63                | 0.65                          | 0.08              | 0.07
fic9.txt  | 239289 | 1.54                | 1.58                          | 0.14              | 0.17
pg135.txt | 568531 | 5.83                | 6.07                          | 0.40              | 0.34

##### Tendances
![running time](https://dl.dropboxusercontent.com/u/1765758/Screenshots%20GitHub/sit151_tp2_running_time2.png)  
**Temps d'éxecution en fonction du nombre de mots.**  

**TODO:** Memory usage

#### Interprétation

### Comparaison des fonctions de hachage
On montre ici l'impact de la fonction de hachage sur la performance du programme. La première fonction proposée (ASCII code first char) présente une mauvaise distribution des valeurs, qui impacte directement le temps de recherche. En effet elle renvoie le code ASCII du premier caractère du mot mais ceux-ci ne sont pas distribués de manière égale dans la langue francaise [[1](1)].  
Au contraire de la fonction MurmurHash3 [[2](2)] qui distribue mieux les valeurs au-travers de calculs bit à bit plus complexes.

![MurmurHash3 vs ASCII](https://dl.dropboxusercontent.com/u/1765758/Screenshots%20GitHub/sit151_tp2_murmur_vs_ascii1.png)  
**Temps d'éxecution en fonction du nombre de mots et de la fonction de hachage.**  

Il est possible de choisir la fonction de hachage à utiliser avec la constante `USE_MURMUR3`:

```c
int hashFunction(char *word) {
  #if USE_MURMUR3 == 1
    return murmur3(word, strlen(word)) % BUCKETS_N;
  #else
    // Really really basic hashing function
    return (int) word[0] % BUCKETS_N;
  #endif
}
```

### Analyse du lexique par liste chaînée
Il est possible d'implémenter le lexique par liste chaînée de deux manières:
- En ajoutant les nouvelles informations à la fin de la liste.
- En insérant les nouvelles informations au début de la liste.

Intuitivement l'insertion au début peut paraitre plus efficace car il n'est pas nécessaire de rechercher la fin de la liste, cependant ce n'est pas adapté à la création d'un lexique. En effet la probabilité de rencontrer de nouveaux mots en double diminue avec la progression dans le texte. Or si l'on insère les nouveaux mots au début, les mots rencontrés au début du texte vont se retrouver à la fin de la liste et l'espace de recherche va augmenter significativement.

![words distribution](https://dl.dropboxusercontent.com/u/1765758/Screenshots%20GitHub/words_distribution_append.png)  
**Distribution des mots en double par rapport à leur position lors de la première rencontre.**

![append vs prepend](https://dl.dropboxusercontent.com/u/1765758/Screenshots%20GitHub/append_vs_prepend.png)  
**Temps d'éxecution en fonction du nombre de mots et du type de liste chaînée.**

### Références
<a name="1"></a> [1] http://en.wikipedia.org/wiki/MurmurHash  
<a name="2"></a> [2] http://fr.wikipedia.org/wiki/Fréquence_d'apparition_des_lettres_en_français
