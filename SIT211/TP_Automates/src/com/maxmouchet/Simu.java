package com.maxmouchet;

import java.util.Scanner;

public class Simu {

    public static void main(String[] args) {
        AEF aef = new AEF2tt();

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Veuillez entrer votre chaine de test");
            String input = sc.next();

            if (aef.accepte(input)) {
                System.out.println("La chaine " + input + " est acceptee");
            } else {
                System.out.println("La chaine " + input + " n'est pas acceptee");
            }
        }
    }
}
