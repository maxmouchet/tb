package com.maxmouchet;

public class AEF1 extends AEF {

    @Override
    public boolean accepte(String entree) {
        int etat = 1;
        int index = 0;
        char carlu;

        while (index != entree.length()) {
            carlu = entree.charAt(index++);

            if ((etat == 1) && (carlu == 'a')) {
                etat = 2;
            } else if ((etat == 2) && (carlu == 'b')) {
                etat = 1;
            } else {
                return false;
            }
        }

        return etat == 2;
    }

}
