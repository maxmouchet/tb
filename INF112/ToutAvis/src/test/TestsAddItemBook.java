package test;

import avis.SocialNetwork;
import exception.BadEntry;
import exception.ItemBookAlreadyExists;
import exception.MemberAlreadyExists;
import exception.NotMember;

public class TestsAddItemBook {

    public static int addItemBookOKTest(SocialNetwork sn, String pseudo, String pwd, String profil, String idTest) {
        int nbLivres = sn.nbBooks();

        try {
            sn.addItemBook(pseudo, pwd, "Hackers & Painters", "Essais", "Paul Graham", 200);

            if (sn.nbBooks() != nbLivres + 1) {
                System.out.println("Test " + idTest + " : le nombre de livres n'a pas été correctement incrémenté");
                return 1;
            } else {
                return 0;
            }

        } catch (Exception e) {
            System.out.println("Test " + idTest + " : exception non prévue. " + e);
            e.printStackTrace();
            return 1;
        }
    }

    public static int addItemBookAlreadyExistTest(SocialNetwork sn, String pseudo, String pwd, String profil, String idTest, String messErreur) {
        int nbLivres = sn.nbBooks();

        try {
            sn.addItemBook(pseudo, pwd, "From Zero To One", "Essais", "Peter Thiel", 200);
            sn.addItemBook(pseudo, pwd, "From Zero To One", "Essais", "Peter Thiel", 200);

            System.out.println("Test " + idTest + " : " + messErreur);
            return 1;
        } catch (ItemBookAlreadyExists e) {
            if (sn.nbBooks() != nbLivres + 1) {
                System.out.println("Test " + idTest + " : l'exception ItemBookAlreadyExists a bien été levé, mais le nombre de livres a été incrémenté");
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Test " + idTest + " : exception non prévue. " + e);
            e.printStackTrace();
            return 1;
        }
    }

    public static int addItemBookAlreadyExistDifferentCaseTest(SocialNetwork sn, String pseudo, String pwd, String profil, String idTest, String messErreur) {
        int nbLivres = sn.nbBooks();

        try {
            sn.addItemBook(pseudo, pwd, "The Singularity is Near", "Essais", "Raymond Kurzweil", 600);
            sn.addItemBook(pseudo, pwd, "tHE Singularity is Near", "Essais", "rAYMOND Kurzweil", 600);

            System.out.println("Test " + idTest + " : " + messErreur);
            return 1;
        } catch (ItemBookAlreadyExists e) {
            if (sn.nbBooks() != nbLivres + 1) {
                System.out.println("Test " + idTest + " : l'exception ItemBookAlreadyExists a bien été levé, mais le nombre de livres a été incrémenté");
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Test " + idTest + " : exception non prévue. " + e);
            e.printStackTrace();
            return 1;
        }
    }

    public static int addItemBookAlreadyExistWithWhitespacesTest(SocialNetwork sn, String pseudo, String pwd, String profil, String idTest, String messErreur) {
        int nbLivres = sn.nbBooks();

        try {
            sn.addItemBook(pseudo, pwd, "The Art of War", "Essais", "Sun Tzu", 100);
            sn.addItemBook(pseudo, pwd, "  The Art of War  ", "Essais", "  Sun T", 100);

            System.out.println("Test " + idTest + " : " + messErreur);
            return 1;
        } catch (ItemBookAlreadyExists e) {
            if (sn.nbBooks() != nbLivres + 1) {
                System.out.println("Test " + idTest + " : l'exception ItemBookAlreadyExists a bien été levé, mais le nombre de livres a été incrémenté");
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Test " + idTest + " : exception non prévue. " + e);
            e.printStackTrace();
            return 1;
        }
    }

    public static int addItemBookSameAuthorDifferentTitleTest(SocialNetwork sn, String pseudo, String pwd, String profil, String idTest) {
        int nbLivres = sn.nbBooks();

        try {
            sn.addItemBook(pseudo, pwd, "1Q84", "Roman", "Haruki Murakami", 1000);
            sn.addItemBook(pseudo, pwd, "Kafka on the shore", "Roman", "Haruki Murakami", 500);

            if (sn.nbBooks() != nbLivres + 2) {
                System.out.println("Test " + idTest + " : le nombre de livres n'a pas été correctement incrémenté");
                return 1;
            } else {
                return 0;
            }

        } catch (Exception e) {
            System.out.println("Test " + idTest + " : exception non prévue. " + e);
            e.printStackTrace();
            return 1;
        }
    }

    public static int addItemBookNullTitleTest(SocialNetwork sn, String pseudo, String pwd, String profil, String idTest, String messErreur) {
        int nbLivres = sn.nbBooks();

        try {
            sn.addItemBook(pseudo, pwd, null, "Roman", "Haruki Murakami", 1000);

            System.out.println("Test " + idTest + " : " + messErreur);
            return 1;
        } catch (BadEntry e) {
            if (sn.nbBooks() != nbLivres + 1) {
                System.out.println("Test " + idTest + " : l'exception BadEntry a bien été levé, mais le nombre de livres a été incrémenté");
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Test " + idTest + " : exception non prévue. " + e);
            e.printStackTrace();
            return 1;
        }
    }

    public static int addItemBookEmptyTitleTest(SocialNetwork sn, String pseudo, String pwd, String profil, String idTest, String messErreur) {
        int nbLivres = sn.nbBooks();

        try {
            sn.addItemBook(pseudo, pwd, "    ", "Roman", "Haruki Murakami", 1000);

            System.out.println("Test " + idTest + " : " + messErreur);
            return 1;
        } catch (BadEntry e) {
            if (sn.nbBooks() != nbLivres + 1) {
                System.out.println("Test " + idTest + " : l'exception BadEntry a bien été levé, mais le nombre de livres a été incrémenté");
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Test " + idTest + " : exception non prévue. " + e);
            e.printStackTrace();
            return 1;
        }
    }

    public static int addItemBookNullGenderTest(SocialNetwork sn, String pseudo, String pwd, String profil, String idTest, String messErreur) {
        int nbLivres = sn.nbBooks();

        try {
            sn.addItemBook(pseudo, pwd, "Underground", null, "Haruki Murakami", 1000);

            System.out.println("Test " + idTest + " : " + messErreur);
            return 1;
        } catch (BadEntry e) {
            if (sn.nbBooks() != nbLivres + 1) {
                System.out.println("Test " + idTest + " : l'exception BadEntry a bien été levé, mais le nombre de livres a été incrémenté");
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Test " + idTest + " : exception non prévue. " + e);
            e.printStackTrace();
            return 1;
        }
    }

    public static int addItemBookNullAuthorTest(SocialNetwork sn, String pseudo, String pwd, String profil, String idTest, String messErreur) {
        int nbLivres = sn.nbBooks();

        try {
            sn.addItemBook(pseudo, pwd, "La Fin des temps", "Roman", null, 1000);

            System.out.println("Test " + idTest + " : " + messErreur);
            return 1;
        } catch (BadEntry e) {
            if (sn.nbBooks() != nbLivres + 1) {
                System.out.println("Test " + idTest + " : l'exception BadEntry a bien été levé, mais le nombre de livres a été incrémenté");
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Test " + idTest + " : exception non prévue. " + e);
            e.printStackTrace();
            return 1;
        }
    }

    public static int addItemBookNegativePageNumberTest(SocialNetwork sn, String pseudo, String pwd, String profil, String idTest, String messErreur) {
        int nbLivres = sn.nbBooks();

        try {
            sn.addItemBook(pseudo, pwd, "Une brève histoire du temps", "Science", "Stephen Hawking", -10);

            System.out.println("Test " + idTest + " : " + messErreur);
            return 1;
        } catch (BadEntry e) {
            if (sn.nbBooks() != nbLivres + 1) {
                System.out.println("Test " + idTest + " : l'exception BadEntry a bien été levé, mais le nombre de livres a été incrémenté");
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Test " + idTest + " : exception non prévue. " + e);
            e.printStackTrace();
            return 1;
        }
    }

    public static int addItemBookNotMemberTest(SocialNetwork sn, String profil, String idTest, String messErreur) {
        int nbLivres = sn.nbBooks();

        try {
            sn.addItemBook("BillGate$$38", "Micro$$oft", "The Intelligent Investor", "Business", "Benjamin Graham", 640);

            System.out.println("Test " + idTest + " : " + messErreur);
            return 1;
        } catch (NotMember e) {
            if (sn.nbBooks() != nbLivres + 1) {
                System.out.println("Test " + idTest + " : l'exception NotMember a bien été levé, mais le nombre de livres a été incrémenté");
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Test " + idTest + " : exception non prévue. " + e);
            e.printStackTrace();
            return 1;
        }
    }

    public static int addItemBookWrongPasswordTest(SocialNetwork sn, String pseudo, String profil, String idTest, String messErreur) {
        int nbLivres = sn.nbBooks();

        try {
            sn.addItemBook(pseudo, "Ju5nPa5lo2015", "Hamlet", "Theatre", "William Shakespeare", 100);

            System.out.println("Test " + idTest + " : " + messErreur);
            return 1;
        } catch (NotMember e) {
            if (sn.nbBooks() != nbLivres + 1) {
                System.out.println("Test " + idTest + " : l'exception BadEntry a bien été levé, mais le nombre de livres a été incrémenté");
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Test " + idTest + " : exception non prévue. " + e);
            e.printStackTrace();
            return 1;
        }
    }

    public static void main(String[] args) throws BadEntry, MemberAlreadyExists {
        int nbTests = 0;
        int nbErreurs = 0;

        System.out.println("# Ajout de livres au réseau social");
        SocialNetwork sn = new SocialNetwork();

        String pseudo = "ToTo";
        String pwd = "Pa$$w0rd";
        String profil = "tAtA";

        System.out.println("Ajout d'un membre pour le test: " + pseudo);
        sn.addMember(pseudo, pwd, profil);

        // tests de add
        int nbFilms = sn.nbFilms();

        // <=> fiche numéro 3
        // tentative d'ajout de livres avec entrées "correctes"

        nbTests++;
        nbErreurs += addItemBookOKTest(sn, pseudo, pwd, profil, "1.1");
        nbTests++;
        nbErreurs += addItemBookAlreadyExistTest(sn, pseudo, pwd, profil, "1.2", "L'ajout d'un livre dont le titre existe déjà est autorisé");
        nbTests++;
        nbErreurs += addItemBookAlreadyExistDifferentCaseTest(sn, pseudo, pwd, profil, "1.3", "L'ajout d'un livre dont le titre existe déjà (casse différente) est autorisé");
        nbTests++;
        nbErreurs += addItemBookAlreadyExistWithWhitespacesTest(sn, pseudo, pwd, profil, "1.4", "L'ajout d'un livre dont le titre existe déjà (avec des espaces en début et fin) est autorisé");
        nbTests++;
        nbErreurs += addItemBookSameAuthorDifferentTitleTest(sn, pseudo, pwd, profil, "1.5");

        // <=> fiche numéro 4
        // tentative d'ajout de livres avec entrées "incorrectes"

        nbTests++;
        nbErreurs += addItemBookNullTitleTest(sn, pseudo, pwd, profil, "2.1", "L'ajout d'un livre avec un titre null est autorisé");
        nbTests++;
        nbErreurs += addItemBookEmptyTitleTest(sn, pseudo, pwd, profil, "2.2", "L'ajout d'un livre avec un titre composé uniquement d'espaces est autorisé");
        nbTests++;
        nbErreurs += addItemBookNullGenderTest(sn, pseudo, pwd, profil, "2.3", "L'ajout d'un livre avec un genre null est autorisé");
        nbTests++;
        nbErreurs += addItemBookNullAuthorTest(sn, pseudo, pwd, profil, "2.4", "L'ajout d'un livre avec un auteur null est autorisé");
        nbTests++;
        nbErreurs += addItemBookNegativePageNumberTest(sn, pseudo, pwd, profil, "2.5", "L'ajout d'un livre avec un nombre de pages negatif est autorisé");
        nbTests++;
        nbErreurs += addItemBookNotMemberTest(sn, profil, "2.6", "L'ajout d'un livre pour un membre inexistant est autorisé");
        nbTests++;
        nbErreurs += addItemBookWrongPasswordTest(sn, pseudo, profil, "2.7", "L'ajout d'un livre avec un mauvais mot de passe est autorisé");

        nbTests++;
        if (nbFilms != sn.nbFilms()) {
            System.out.println("Erreur  :  le nombre de films après utilisation de addItemBook a été modifié");
            nbErreurs++;
        }

        // ce n'est pas du test, mais cela peut "rassurer"...
        System.out.println(sn);

        // bilan du test de addItemBook
        System.out.println("TestsAddItemBook :   " + nbErreurs + " erreur(s) / " + nbTests + " tests effectués");
    }
}
