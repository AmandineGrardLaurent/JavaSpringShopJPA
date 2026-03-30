package fr.fms.controller;

import java.util.Scanner;

import fr.fms.service.CategoryService;
import fr.fms.utils.console.ConsoleColors;
import fr.fms.utils.input.InputHelper;

public class CategoryConsoleController {

    /**
     * Affiche une categorie à partir de son id.
     * 
     * @param scanner         Scanner utilisé pour la saisie
     * @param categoryService Service pour accéder aux catégories
     */
    public static void displayCategoryById(Scanner scanner, CategoryService categoryService) {
        Long categoryId = InputHelper.askLong(scanner, "Quelle catégorie souhaitez-vous consulter ? ");

        System.out.printf("Affichage de la catégorie %d : \n", categoryId);

        System.out.println(
                categoryService.getCategoryById(categoryId)
                        .map(Object::toString)
                        .orElse(ConsoleColors.RED + "Catégorie introuvable \n" + ConsoleColors.RESET));
    }

    /**
     * Supprime une catégorie à partir de son id après vérification de son
     * existence.
     * 
     * @param scanner         Scanner utilisé pour la saisie
     * @param categoryService Service pour accéder aux catégories
     */
    public static void deleteCategoryById(Scanner scanner, CategoryService categoryService) {
        Long categoryId = InputHelper.askLong(scanner,
                ConsoleColors.BLUE + "-- Quelle catégorie (id) voulez-vous supprimer ? --" + ConsoleColors.RESET);

        // Vérifie si la catégorie existe
        while (!categoryService.categoryExists(categoryId)) {
            System.out.println(ConsoleColors.RED + "Catégorie inexistante !" + ConsoleColors.RESET);
            categoryId = InputHelper.askLong(scanner,
                    ConsoleColors.BLUE + "-- Quelle catégorie (id) voulez-vous supprimer ? --" + ConsoleColors.RESET);
        }

        categoryService.deleteCategory(categoryId);
        System.out.printf(
                ConsoleColors.GREEN + "Catégorie %d supprimé avec succès \n" + ConsoleColors.RESET,
                categoryId);

    }

    /**
     * Ajoute une nouvelle catégorie via la console.
     * Vérifie que le nom de la catégorie n'existe pas déjà.
     * 
     * @param scanner         Scanner utilisé pour la saisie
     * @param categoryService Service pour gérer les catégories
     */
    public static void addCategory(Scanner scanner, CategoryService categoryService) {
        String categoryName = InputHelper.askString(scanner, "Nom de la catégorie : ");

        // Vérifie que la catégorie n'existe pas déjà
        while (categoryService.categoryExistsByName(categoryName)) {
            System.out.println(ConsoleColors.RED + "Categorie déjà existante !" + ConsoleColors.RESET);
            categoryName = InputHelper.askString(scanner, "Nom de la catégorie : ");
        }

        categoryService.createCategory(categoryName);
        System.out
                .println(ConsoleColors.GREEN + "Catégorie créée avec succès !" + ConsoleColors.RESET);
    }
}
