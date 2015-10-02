package com.maxmouchet;

public class AEF2 extends AEF {

    @Override
    public boolean accepte(String entree) {
        State etat = State.STATE0;
        int index = 0;
        char carlu;

        while (index != entree.length()) {
            carlu = entree.charAt(index++);

            if ((etat == State.STATE0) && (carlu == 'a')) {
                etat = State.STATE1;
                continue;
            }

            if ((etat == State.STATE1) && (carlu == 'b')) {
                etat = State.STATE2;
                continue;
            }

            if ((etat == State.STATE2) && (carlu == 'a' || carlu == 'b')) {
                etat = State.STATE3;
                continue;
            }

            if ((etat == State.STATE3) && (carlu == 'a')) {
                etat = State.STATE3;
                continue;
            }

            if ((etat == State.STATE3) && (carlu == 'b')) {
                etat = State.STATE4;
                continue;
            }

            if ((etat == State.STATE4) && (carlu == 'b')) {
                etat = State.STATE4;
                continue;
            }

            if ((etat == State.STATE4) && (carlu == 'a')) {
                etat = State.STATE5;
                continue;
            }

            if ((etat == State.STATE5) && (carlu == 'a')) {
                etat = State.STATE3;
                continue;
            }

            if ((etat == State.STATE5) && (carlu == 'b')) {
                etat = State.STATE4;
                continue;
            }

            return false;
        }

        return etat == State.STATE5;
    }

    enum State {
        STATE0,
        STATE1,
        STATE2,
        STATE3,
        STATE4,
        STATE5
    }
}
