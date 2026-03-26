package fr.fms.controller;

import java.util.Scanner;

import fr.fms.entities.Category;
import fr.fms.service.ArticleService;
import fr.fms.service.CategoryService;
import fr.fms.utils.console.ConsoleColors;
import fr.fms.utils.input.InputHelper;

public class ArticleConsoleController {

    /**
     * Supprime un article à partir de son id après vérification de son existence.
     * 
     * @param scanner        Scanner utilisé pour la saisie
     * @param articleService Service pour accéder aux articles
     */
    public static void deleteArticleById(Scanner scanner, ArticleService articleService) {
        Long articleId = InputHelper.askLong(scanner, "Quel article (id) voulez-vous supprimer ? ");

        // Vérifie si l'article existe
        while (!articleService.articleExists(articleId)) {
            System.out.println(ConsoleColors.RED + "Article inexistant !" + ConsoleColors.RESET);
            articleId = InputHelper.askLong(scanner, "Quel article (id) voulez-vous supprimer ? ");
        }

        articleService.deleteArticle(articleId);
        System.out.printf(
                ConsoleColors.GREEN + "Article %d supprimé avec succès \n" + ConsoleColors.RESET,
                articleId);

    }

    /**
     * Ajoute un nouvel article via la console.
     * Vérifie que la catégorie existe avant de créer l'article.
     * 
     * @param scanner         Scanner utilisé pour la saisie
     * @param articleService  Service pour gérer les articles
     * @param categoryService Service pour gérer les catégories
     */
    public static void addArticle(Scanner scanner, ArticleService articleService, CategoryService categoryService) {
        String brand = InputHelper.askString(scanner, "La marque : ");
        String description = InputHelper.askString(scanner, "La description : ");
        double price = InputHelper.askDouble(scanner, "Le prix : ");
        Long categoryId = InputHelper.askLong(scanner, "L'id de la catégorie : ");

        // Vérifie que la catégorie existe
        while (!categoryService.categoryExists(categoryId)) {
            System.out.println(ConsoleColors.RED + "Categorie inexistante !" + ConsoleColors.RESET);
            categoryId = InputHelper.askLong(scanner, "L'id de la catégorie : ");
        }

        Category category = categoryService.getCategoryById(categoryId).get();
        articleService.createArticle(brand, description, price, category);

        System.out.println(ConsoleColors.GREEN + "Article créé avec succès !" + ConsoleColors.RESET);
    }
}
