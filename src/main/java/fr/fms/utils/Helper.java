package fr.fms.utils;

import java.util.Scanner;

public class Helper {

    public static int askInt(Scanner scanner, String message) {
        int number;
        while (true) {
            System.out.println(message);
            if (!scanner.hasNextInt()) {
                System.out.println("Saisissez un entier.");
                scanner.next();
                continue;
            }
            number = scanner.nextInt();
            break;
        }
        return number;
    }

    public static int displayChoices(Scanner scanner) {
        System.out.println("[1] Afficher tous les articles sans pagination\n"
                + "[2] Afficher tous les articles sans pagination\n"
                + "**********************************"
                + "\n[3] Ajouter un article\n"
                + "[4] Afficher un article\n"
                + "[5] Supprimer un article\n"
                + "[6] Modifier un article\n"
                + "**********************************"
                + "\n[7] Ajouter une catégorie\n"
                + "[8] Afficher une catégorie\n"
                + "[9] Supprimer une catégorie\n"
                + "[10] Mettre à jour une catégorie\n"
                + "[11] Afficher tous les articles d'une catégorie\n"
                + "**********************************"
                + "\n[12] Quitter\n");

        // Ask the user for a numeric choice
        return askInt(scanner, "Quel est votre choix ? ");
    }
}
