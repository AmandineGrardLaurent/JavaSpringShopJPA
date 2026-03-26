package fr.fms.controller;

import java.util.Collection;
import java.util.Scanner;

import org.springframework.data.domain.Page;

import fr.fms.entities.Article;
import fr.fms.entities.Category;
import fr.fms.service.ArticleService;
import fr.fms.service.CategoryService;
import fr.fms.utils.Helper;
import fr.fms.utils.console.ConsoleColors;
import fr.fms.utils.input.InputHelper;

public class ArticleConsoleController {

    /**
     * Affiche un article à partir de son id.
     * 
     * @param scanner        Scanner utilisé pour la saisie
     * @param articleService Service pour accéder aux articles
     */
    public static void displayArticleById(Scanner scanner, ArticleService articleService) {
        Long articleId = InputHelper.askLong(scanner, "Quel article souhaitez-vous consulter ?");

        System.out.printf("Affichage de l'article %d :\n", articleId);

        System.out.println(
                articleService.getArticleById(articleId)
                        .map(Object::toString)
                        .orElse(ConsoleColors.RED + "Article introuvable \n" + ConsoleColors.RESET));
    }

    /**
     * Permet d'afficher les articles avec un système de pagination interactif.
     * L'utilisateur peut naviguer entre les pages, changer le nombre d'éléments
     * par page ou quitter l'affichage.
     *
     * Fonctionnalités :
     * - Navigation page précédente / suivante
     * - Modification du nombre d'articles par page
     * - Affichage de la pagination
     */
    public static void displayArticlesByPage(Scanner scanner, ArticleService articleService) {
        int page = 0;
        int size = 5;
        boolean exit = false;
        Page<Article> articlePage;

        while (!exit) {
            articlePage = articleService.getArticlesByPage(page, size);
            System.out.printf(ConsoleColors.GREEN + "\n Page n°%d \n" + ConsoleColors.RESET, (page + 1));

            for (Article article : articlePage.getContent()) {
                System.out.println(article);
            }

            Helper.displayPagination(articlePage);

            System.out
                    .println(ConsoleColors.BLUE + "\n [1] page précédent"
                            + "\n [2] page suivante"
                            + "\n [3] pagination"
                            + "\n [4] quitter \n" + ConsoleColors.RESET);
            int choice = InputHelper.askInt(scanner, "Faîtes votre choix : ");

            switch (choice) {
                case 1:
                    if (page > 0) {
                        page--;
                    } else {
                        System.out
                                .println(ConsoleColors.RED + "Vous êtes déjà à la première page" + ConsoleColors.RESET);
                    }
                    break;
                case 2:
                    if (page < articlePage.getTotalPages() - 1) {
                        page++;
                    } else {
                        System.out
                                .println(ConsoleColors.RED + "Vous êtes déjà à la dernière page" + ConsoleColors.RESET);
                    }
                    break;
                case 3:
                    size = InputHelper.askInt(scanner, "Combien d'articles par page ? ");
                    page = 0;
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println(ConsoleColors.RED + "Choix invalide" + ConsoleColors.RESET);
            }

        }

    }

    public static void displayArticlesByCategoryId(Scanner scanner, ArticleService articleService,
            CategoryService categoryService) {
        Long categoryId = InputHelper.askLong(scanner, "Quelle catégorie souhaitez-vous consulter ? ");
        Collection<Article> articles = articleService.getArticlesByCategoryId(categoryId);

        if (!categoryService.categoryExists(categoryId)) {
            System.out.println(ConsoleColors.RED + "Cette catégorie n'existe pas." + ConsoleColors.RESET);
        } else if (articles.isEmpty()) {
            System.out.println(ConsoleColors.RED + "Aucun article dans cette catégorie." + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.BLUE + "Articles de la catégorie : \n" + ConsoleColors.RESET);
            for (Article article : articles) {
                System.out.println(article);
            }
        }
    }

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
