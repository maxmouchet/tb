CC=gcc
CFLAGS=-Weverything -Wno-padded

all: lexique_tableau lexique_liste

lexique_tableau: words.o lexique_tableau.o
	$(CC) -o lexique_tableau words.o lexique_tableau.o

lexique_tableau.o: lexique_tableau.c
	$(CC) -o lexique_tableau.o -c lexique_tableau.c $(CFLAGS)

lexique_liste: words.o lexique_liste.o
	$(CC) -o lexique_liste words.o lexique_liste.o

lexique_list.o: lexique_liste.c
	$(CC) -o lexique_liste.o -c lexique_liste.c $(CFLAGS)

words.o: words.c
	$(CC) -o words.o -c words.c -Weverything

.PHONY: clean mrproper

clean:
	rm -f *.o

mrproper: clean
	rm -f lexique_tableau lexique_liste 
