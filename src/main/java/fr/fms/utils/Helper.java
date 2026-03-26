package fr.fms.utils;

import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import fr.fms.entities.Article;
import fr.fms.entities.Category;
import fr.fms.service.ArticleService;
import fr.fms.service.CategoryService;

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
        System.out.println("[1] Afficher tous les articles sans pagination\n"
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
                + "********************************** \n"
                + "[12] Quitter\n");

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
            System.out.println(ConsoleColors.BLUE + title + ConsoleColors.RESET + "\n");
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

            displayPagination(articlePage);

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
        Long categoryId = InputHelper.askLong(scanner, "Quelle catégorie (id) voulez-vous supprimer ? ");

        // Vérifie si la catégorie existe
        while (!categoryService.categoryExists(categoryId)) {
            System.out.println(ConsoleColors.RED + "Catégorie inexistante !" + ConsoleColors.RESET);
            categoryId = InputHelper.askLong(scanner, "Quelle catégorie (id) voulez-vous supprimer ? ");
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

    public static void displayArticlesByCategoryId(Scanner scanner, CategoryService categoryService) {
        Long categoryId = InputHelper.askLong(scanner, "Quelle catégorie souhaitez-vous consulter ? ");
        Collection<Article> articles = categoryService.getArticlesByCategoryId(categoryId);
        System.out.println(articles);
        if (articles.isEmpty()) {
            System.out.println(ConsoleColors.RED + "Aucun article dans cette catégorie." + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.BLUE + "Articles de la catégorie : \n" + ConsoleColors.RESET);
            for (Article article : articles) {
                System.out.println(article);
            }
        }
    }
}
