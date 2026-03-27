package fr.fms.controller;

import java.util.Collection;
import java.util.Optional;
import java.util.Scanner;
import java.util.List;

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
     */
    public static void displayArticleById(Scanner scanner, ArticleService articleService) {
        Long articleId = InputHelper.askLong(scanner,
                ConsoleColors.BLUE + "-- Quel article souhaitez-vous consulter ? --" + ConsoleColors.RESET);

        System.out.printf(ConsoleColors.GREEN + "\nAffichage de l'article %d :\n" + ConsoleColors.RESET, articleId);

        System.out.println(
                articleService.getArticleById(articleId)
                        .map(Object::toString)
                        .orElse(ConsoleColors.RED + "Article introuvable \n" + ConsoleColors.RESET));

        System.out.println("\n");
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
            System.out.printf(ConsoleColors.GREEN + "-- Page n°%d --" + ConsoleColors.RESET, (page + 1));

            for (Article article : articlePage.getContent()) {
                System.out.println(article);
            }

            System.out.println("");
            Helper.displayPagination(articlePage);

            System.out
                    .println("[1] page précédent"
                            + "\n[2] page suivante"
                            + "\n[3] pagination"
                            + "\n[4] quitter \n");

            int choice = InputHelper.askInt(scanner,
                    ConsoleColors.BLUE + "-- Faîtes votre choix : --" + ConsoleColors.RESET);

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
                    size = InputHelper.askInt(scanner,
                            ConsoleColors.BLUE + "-- Combien d'articles par page ? --" + ConsoleColors.RESET);
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

    /**
     * Affiche les articles d'une catégorie
     */
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
                ConsoleColors.GREEN + "Article %d supprimé avec succès" + ConsoleColors.RESET,
                articleId);

    }

    /**
     * Ajoute un nouvel article via la console.
     * Vérifie que la catégorie existe avant de créer l'article.
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

    /**
     * Modifie un article via la console
     */
    public static void updateArticle(Scanner scanner, ArticleService articleService,
            CategoryService categoryService) {

        Long articleId = InputHelper.askLong(scanner,
                ConsoleColors.BLUE + "-- Quel article (id) voulez-vous modifier ? -- \n" + ConsoleColors.RESET);

        Optional<Article> optionalArticle = articleService.getArticleById(articleId);

        if (!optionalArticle.isPresent()) {
            System.out.println(ConsoleColors.RED + "Article introuvable !\n" + ConsoleColors.RESET);
            return;
        }

        Article article = optionalArticle.get();

        String brandInput = InputHelper.askString(scanner,
                ConsoleColors.YELLOW + "Nouvelle marque (vide pour ignorer) : " + ConsoleColors.RESET);
        if (!brandInput.isEmpty())
            article.setBrand(brandInput);

        String descriptionInput = InputHelper.askString(scanner,
                ConsoleColors.YELLOW + "Nouvelle description (vide pour ignorer) : " + ConsoleColors.RESET);
        if (!descriptionInput.isEmpty())
            article.setDescription(descriptionInput);

        String priceInput = InputHelper.askString(scanner,
                ConsoleColors.YELLOW + "Nouveau prix (vide pour ignorer) : " + ConsoleColors.RESET);
        if (!priceInput.isEmpty()) {
            try {
                double price = Double.parseDouble(priceInput);
                article.setPrice(price);
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.RED + "Prix invalide !\n" + ConsoleColors.RESET);
            }
        }

        String categoryInput = InputHelper.askString(scanner,
                ConsoleColors.YELLOW + "Nouvelle catégorie (id, vide pour ignorer) : " + ConsoleColors.RESET);
        if (!categoryInput.isEmpty()) {
            try {
                Long categoryId = Long.parseLong(categoryInput);

                while (!categoryService.categoryExists(categoryId)) {
                    System.out.println(ConsoleColors.RED + "Catégorie inexistante !" + ConsoleColors.RESET);
                    categoryId = InputHelper.askLong(scanner, "Nouvelle catégorie (id, vide pour ignorer) : ");

                }
                Category category = categoryService.getCategoryById(categoryId).get();
                article.setCategory(category);

            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.RED + "Id de la catégorie invalide !\n" + ConsoleColors.RESET);
            }
        }
        articleService.saveArticle(article);

        System.out.println(ConsoleColors.GREEN + "\nArticle mis à jour ! \n" + ConsoleColors.RESET);
    }
}
