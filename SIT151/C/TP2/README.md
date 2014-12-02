## SIT 151 / C / TP2

**Objectif:** réaliser un lexique contenant le nombre d'occurences de chaque mot à partir d'un fichier.  
**Implémentations:** tableau, liste chaînée, hashtable, b-tree  

### Comparaison algorithmes

#### Résultats théoriques

#### Résultats pratiques

Script usage: `Usage: ./benchmark.sh ALGORITHM FILES...`  
Configuration: `MBA 2013, Core i5@1.3GHz, 8GB DDR3, SSD`  
Memory usage (total heap allocations) measured with Intruments on OSX.  

File     | Words  | lexique_tableau (s) | lexique_tableau (KB) | lexique_liste (s) | lexique_liste (KB)
---------|--------|---------------------|----------------------|-------------------|-------------------
fic0.txt | 69     | 0.00                | 262.72               | 0.00              | 8.19
fic1.txt | 585    | 0.00                | 265.77               | 0.00              | 17.33
fic2.txt | 1363   | 0.00                | 268.23               | 0.00              | 24.73
fic3.txt | 3664   | 0.00                | 273.97               | 0.01              | 41.94
fic4.txt | 8028   | 0.01                | 279.19               | 0.03              | 57.59
fic5.txt | 14978  | 0.04                | 286.34               | 0.09              | 78.88
fic6.txt | 22148  | 0.07                | 301.31               | 0.17              | 123.94
fic7.txt | 51333  | 0.24                | 334.17               | 0.71              | 222.59
fic8.txt | 124062 | 0.87                | 396.62               | 3.31              | 409.58
fic9.txt | 239289 | 2.20                | 456.94               | 10.76             | 590.70

##### Comparaisons allocations sur fic9.txt

**lexique_tableau**  
![lexique_tableau heap allocations](https://dl.dropboxusercontent.com/u/1765758/Screenshots%20GitHub/heap_lexique_tableau.png)

**lexique_liste**
![lexique_tableau heap allocations](https://dl.dropboxusercontent.com/u/1765758/Screenshots%20GitHub/heap_lexique_liste2.png)
