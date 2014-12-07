CC=gcc
CFLAGS=-O3 -Weverything -Wno-padded

all: lexique_tableau lexique_liste lexique_hash lexique_btree

lexique_tableau: words.o lexique_tableau.o
	$(CC) -o lexique_tableau words.o lexique_tableau.o

lexique_tableau.o: lexique_tableau.c
	$(CC) -o lexique_tableau.o -c lexique_tableau.c $(CFLAGS)

lexique_liste: words.o lexique_liste.o
	$(CC) -o lexique_liste words.o lexique_liste.o

lexique_liste.o: lexique_liste.c
	$(CC) -o lexique_liste.o -c lexique_liste.c $(CFLAGS)

lexique_hash: words.o murmurhash3.o lexique_hash.o
	$(CC) -o lexique_hash words.o murmurhash3.o lexique_hash.o

lexique_hash.o: lexique_hash.c
	$(CC) -o lexique_hash.o -c lexique_hash.c $(CFLAGS)

lexique_btree: words.o lexique_btree.o
	$(CC) -o lexique_liste words.o lexique_liste.o

lexique_btree.o: lexique_btree.c
	$(CC) -o lexique_btree.o -c lexique_btree.c $(CFLAGS)

murmurhash3.o: murmurhash3.c
	$(CC) -o murmurhash3.o -c murmurhash3.c $(CFLAGS)

words.o: words.c
	$(CC) -o words.o -c words.c $(CFLAGS)

.PHONY: clean mrproper

clean:
	rm -f *.o

mrproper: clean
	rm -f lexique_tableau lexique_liste lexique_hash lexique_btree
