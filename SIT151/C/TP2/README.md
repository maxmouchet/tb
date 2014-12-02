## SIT 151 / C / TP2

**Objectif:** réaliser un lexique contenant le nombre d'occurences de chaque mot à partir d'un fichier.  
**Implémentations:** tableau, liste chaînée, hashtable, b-tree  

### Comparaison algorithmes

#### Résultats théoriques

#### Résultats pratiques

Script usage: `Usage: ./benchmark.sh ALGORITHM FILES...`  
Configuration: `MBA 2013, Core i5@1.3GHz, 8GB DDR3, SSD`  

File     | Words  | lexique_tableau (s) | lexique_liste (s)
---------|--------|---------------------|------------------
fic0.txt | 69     | 0.00                | 0.00
fic1.txt | 585    | 0.00                | 0.00
fic2.txt | 1363   | 0.00                | 0.00
fic3.txt | 3664   | 0.00                | 0.01
fic4.txt | 8028   | 0.01                | 0.03
fic5.txt | 14978  | 0.04                | 0.09
fic6.txt | 22148  | 0.07                | 0.17
fic7.txt | 51333  | 0.24                | 0.71
fic8.txt | 124062 | 0.87                | 3.31
fic9.txt | 239289 | 2.20                | 10.76
