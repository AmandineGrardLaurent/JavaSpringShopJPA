package fr.fms.utils.input;

import java.util.Scanner;

import fr.fms.utils.console.ConsoleColors;

public class InputHelper {
    /**
     * Récupère l'entier saisit par l'utilisateur, avec validation.
     * 
     * @return la saisie de l'utilisateur
     */
    public static int askInt(Scanner scanner, String message) {
        int number;
        while (true) {
            System.out.println(message);
            if (!scanner.hasNextInt()) {
                System.out.println(ConsoleColors.RED + "Saisissez un entier." + ConsoleColors.RESET);
                // on vide le buffer
                scanner.next();
                continue;
            }
            number = scanner.nextInt();
            // on vide le saut de ligne restant
            scanner.nextLine();
            break;
        }
        return number;
    }

    /**
     * Récupère la chaîne de caractère saisi par l'utilisateur.
     * 
     * @return la saisie de l'utilisateur
     */
    public static String askString(Scanner scanner, String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    /**
     * Récupère le nombre décimal saisi par l'utilisateur.
     * 
     * @return la saisie de l'utilisateur
     */
    public static double askDouble(Scanner scanner, String message) {
        System.out.print(message);
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }

    /**
     * Récupère le nombre entier (sur 64bits) saisi par l'utilisateur.
     * 
     * @return la saisie de l'utilisateur
     */
    public static Long askLong(Scanner scanner, String message) {
        Long number;

        while (true) {
            System.out.println(message);
            if (!scanner.hasNextLong()) {
                System.out.println(ConsoleColors.RED + "Saisissez un entier." + ConsoleColors.RESET);
                // on vide le buffer
                scanner.next();
                continue;
            }
            number = scanner.nextLong();
            // on vide le saut de ligne restant
            scanner.nextLine();
            break;
        }
        return number;

    }
}
