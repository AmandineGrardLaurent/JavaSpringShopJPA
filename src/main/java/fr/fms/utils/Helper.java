package fr.fms.utils;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Scanner;

import fr.fms.utils.console.ConsoleColors;
import fr.fms.utils.input.InputHelper;

/**
 * Classe Helper pour gérer les interactions console avec l'utilisateur.
 */
public class Helper {

    /**
     * Affiche le menu principal et lit le choix de l'utilisateur.
     * 
     * @return le choix saisi par l'utilisateur
     */
    public static int displayChoices(Scanner scanner) {
        System.out.println("\n[1] Afficher tous les articles sans pagination\n"
                + "[2] Afficher tous les articles avec pagination\n"
                + "********************************** \n"
                + "[3] Ajouter un article\n"
                + "[4] Afficher un article\n"
                + "[5] Supprimer un article\n"
                + "[6] Modifier un article\n"
                + "********************************** \n"
                + "[7] Ajouter une catégorie\n"
                + "[8] Afficher une catégorie\n"
                + "[9] Supprimer une catégorie\n"
                + "[10] Mettre à jour une catégorie\n"
                + "[11] Afficher tous les articles d'une catégorie\n"
                + "[12] Afficher toutes les catégories\n"
                + "********************************** \n"
                + "[13] Quitter\n");

        // Demande le choix à l'utilisateur
        return InputHelper.askInt(scanner, ConsoleColors.BLUE + "-- Quel est votre choix ? --" + ConsoleColors.RESET);
    }

    /**
     * Affiche une liste d'objets de manière générique avec un titre.
     * 
     * @param items        Liste d'objets à afficher
     * @param title        Titre de l'affichage
     * @param errorMessage Message d'erreur si la liste est vide
     */
    public static <T> void displayItems(List<T> items, String title, String errorMessage) {
        if (items.isEmpty()) {
            System.out.println(ConsoleColors.RED + errorMessage + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.GREEN + title + ConsoleColors.RESET + "\n");
            for (T item : items) {
                System.out.println(item);
            }
            System.out.println("\n");
        }
    }

    /**
     * Affiche la pagination des pages disponibles en mettant en évidence
     * la page courante entre crochets.
     *
     * Exemple d'affichage :
     * Pages : 1 2 [3] 4 5
     *
     * @param page objet Page contenant les informations de pagination
     */
    public static void displayPagination(Page<?> page) {
        int totalPages = page.getTotalPages();
        int currentPage = page.getNumber();

        System.out.print(ConsoleColors.GREEN + "Pages : ");

        for (int i = 0; i < totalPages; i++) {
            if (i == currentPage) {
                System.out.print("[" + (i + 1) + "] ");
            } else {
                System.out.print((i + 1) + " ");
            }
        }

        System.out.println("\n" + ConsoleColors.RESET);
    }

}
