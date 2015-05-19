package test;

import avis.SocialNetwork;

/**
 * @author B. Prou
 * @version V0.6
 * @date mars 2011
 */

class TestsInitialisation {


    public static void main(String[] args) {
        System.out.println("\n# Tests d'initialisation du réseau social");

        try {

            // un réseau social créé ne doit avoir ni membres ni items
            SocialNetwork sn = new SocialNetwork();
            if (sn.nbMembers() != 0) {
                System.out.println("Erreur 0.1 :  le nombre de membres après création du réseau est non nul");
                System.exit(1);
            }
            if (sn.nbBooks() != 0) {
                System.out.println("Erreur 0.2 : le nombre de livres après création du réseau est non nul");
                System.exit(1);
            }
            if (sn.nbFilms() != 0) {
                System.out.println("Erreur 0.3 : le nombre de films après création du réseau est non nul");
                System.exit(1);
            }
        } catch (Exception e) {
            System.out.println("Exception non prévue : " + e);
            e.printStackTrace();
            System.exit(1);
        }
    }


}
