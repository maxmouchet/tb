## SIT 151 / C / TP2

[*Yann Feunteun*](https://github.com/yafeunteun), [*Maxime Mouchet*](https://github.com/maxmouchet)

**Objectif:** réaliser un lexique contenant le nombre d'occurences de chaque mot à partir d'un fichier.  
**Implémentations:** tableau, liste chaînée, hashtable, b-tree  

[Comparaison des algorithmes](#comparaison-des-algorithmes)  
[Comparaison des fonctions de hachage](#comparaison-des-fonctions-de-hachage)  
[Analyse du lexique par liste chaînée](#analyse-du-lexique-par-liste-chaînée)

### Comparaison des algorithmes

#### Résultats pratiques

Script usage: `Usage: ./benchmark.sh ALGORITHM FILES...`  
Configuration: `MBA 2013, Core i5@1.3GHz, 8GB DDR3, SSD`  
Compilation: `clang -O3`   

##### Mesures

File      | Words  | lexique_tableau (s) | lexique_liste (s)    | lexique_hash (s)  | lexique_btree (s)
----------|--------|---------------------|----------------------|-------------------|-------------------
fic0.txt  | 69     | 0.00                | 0.00                 | 0.00              | 0.00
fic1.txt  | 585    | 0.00                | 0.00                 | 0.00              | 0.00
fic2.txt  | 1363   | 0.00                | 0.00                 | 0.00              | 0.00
fic3.txt  | 3664   | 0.00                | 0.00                 | 0.00              | 0.00
fic4.txt  | 8028   | 0.01                | 0.01                 | 0.00              | 0.00
fic5.txt  | 14978  | 0.02                | 0.02                 | 0.00              | 0.01
fic6.txt  | 22148  | 0.04                | 0.05                 | 0.01              | 0.01
fic7.txt  | 51333  | 0.17                | 0.19                 | 0.03              | 0.03
fic8.txt  | 124062 | 0.63                | 0.65                 | 0.08              | 0.07
fic9.txt  | 239289 | 1.54                | 1.58                 | 0.14              | 0.17
pg135.txt | 568531 | 5.83                | 6.07                 | 0.40              | 0.34

##### Tendances
![running time](https://dl.dropboxusercontent.com/u/1765758/Screenshots%20GitHub/sit151_tp2_running_time2.png)  
**TODO:** Memory usage

### Comparaison des fonctions de hachage
![MurmurHash3 vs ASCII](https://dl.dropboxusercontent.com/u/1765758/Screenshots%20GitHub/sit151_tp2_murmur_vs_ascii1.png)

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
![append vs prepend](https://dl.dropboxusercontent.com/u/1765758/Screenshots%20GitHub/append_vs_prepend.png)
![words distribution](https://dl.dropboxusercontent.com/u/1765758/Screenshots%20GitHub/words_distribution_append.png)
