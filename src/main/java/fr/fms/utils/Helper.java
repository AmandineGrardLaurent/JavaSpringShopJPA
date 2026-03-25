package fr.fms.utils;

import java.util.List;
import java.util.Scanner;

import fr.fms.entities.Article;
import fr.fms.entities.Category;
import fr.fms.service.ArticleService;
import fr.fms.service.CategoryService;

/**
 * Classe Helper pour gérer les interactions console avec l'utilisateur.
 */
public class Helper {

    /**
     * Classe interne pour gérer les couleurs d'affichage dans la console.
     */
    public class ConsoleColors {
        public static final String RESET = "\u001B[0m";
        public static final String RED = "\u001B[31m";
        public static final String GREEN = "\u001B[32m";
        public static final String YELLOW = "\u001B[33m";
        public static final String BLUE = "\u001B[34m";
    }

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
        System.out.print(message);
        Long value = scanner.nextLong();
        scanner.nextLine();
        return value;
    }

    /**
     * Affiche le menu principal et lit le choix de l'utilisateur.
     * 
     * @return le choix saisi par l'utilisateur
     */
    public static int displayChoices(Scanner scanner) {
        System.out.println("[1] Afficher tous les articles sans pagination\n"
                + "[2] Afficher tous les articles sans pagination\n"
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
        return askInt(scanner, ConsoleColors.BLUE + "-- Quel est votre choix ? --" + ConsoleColors.RESET);
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
     * Affiche un article à partir de son id.
     * 
     * @param scanner        Scanner utilisé pour la saisie
     * @param articleService Service pour accéder aux articles
     */
    public static void displayArticleById(Scanner scanner, ArticleService articleService) {
        Long articleId = askLong(scanner, "Quel article souhaitez-vous consulter ?");

        System.out.printf("Affichage de l'article %d :\n", articleId);

        System.out.println(
                articleService.getArticleById(articleId)
                        .map(Object::toString)
                        .orElse(Helper.ConsoleColors.RED + "Article introuvable \n" + Helper.ConsoleColors.RESET));
    }

    /**
     * Affiche une categorie à partir de son id.
     * 
     * @param scanner         Scanner utilisé pour la saisie
     * @param categoryService Service pour accéder aux catégories
     */
    public static void displayCategoryById(Scanner scanner, CategoryService categoryService) {
        Long categoryId = askLong(scanner, "Quelle catégorie souhaitez-vous consulter ? ");

        System.out.printf("Affichage de la catégorie %d : \n", categoryId);

        System.out.println(
                categoryService.getCategoryById(categoryId)
                        .map(Object::toString)
                        .orElse(Helper.ConsoleColors.RED + "Catégorie introuvable \n" + Helper.ConsoleColors.RESET));
    }

    /**
     * Supprime un article à partir de son id après vérification de son existence.
     * 
     * @param scanner        Scanner utilisé pour la saisie
     * @param articleService Service pour accéder aux articles
     */
    public static void deleteArticleById(Scanner scanner, ArticleService articleService) {
        Long articleId = askLong(scanner, "Quel article (id) voulez-vous supprimer ? ");

        // Vérifie si l'article existe
        while (!articleService.articleExists(articleId)) {
            System.out.println(Helper.ConsoleColors.RED + "Article inexistant !" + Helper.ConsoleColors.RESET);
            articleId = askLong(scanner, "Quel article (id) voulez-vous supprimer ? ");
        }

        articleService.deleteArticle(articleId);
        System.out.printf(
                Helper.ConsoleColors.GREEN + "Article %d supprimé avec succès \n" + Helper.ConsoleColors.RESET,
                articleId);

    }

    /**
     * Supprime une catégorie à partir de son id après vérification de son
     * existence.
     * 
     * @param scanner         Scanner utilisé pour la saisie
     * @param categoryService Service pour accéder aux catégories
     */
    public static void deleteCategoryById(Scanner scanner, CategoryService categoryService) {
        Long categoryId = askLong(scanner, "Quelle catégorie (id) voulez-vous supprimer ? ");

        // Vérifie si la catégorie existe
        while (!categoryService.categoryExists(categoryId)) {
            System.out.println(Helper.ConsoleColors.RED + "Catégorie inexistante !" + Helper.ConsoleColors.RESET);
            categoryId = askLong(scanner, "Quelle catégorie (id) voulez-vous supprimer ? ");
        }

        categoryService.deleteCategory(categoryId);
        System.out.printf(
                Helper.ConsoleColors.GREEN + "Catégorie %d supprimé avec succès \n" + Helper.ConsoleColors.RESET,
                categoryId);

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
        String brand = askString(scanner, "La marque : ");
        String description = askString(scanner, "La description : ");
        double price = askDouble(scanner, "Le prix : ");
        Long categoryId = askLong(scanner, "L'id de la catégorie : ");

        // Vérifie que la catégorie existe
        while (!categoryService.categoryExists(categoryId)) {
            System.out.println(Helper.ConsoleColors.RED + "Categorie inexistante !" + Helper.ConsoleColors.RESET);
            categoryId = askLong(scanner, "L'id de la catégorie : ");
        }

        Category category = categoryService.getCategoryById(categoryId).get();
        articleService.createArticle(brand, description, price, category);

        System.out.println(Helper.ConsoleColors.GREEN + "Article créé avec succès !" + Helper.ConsoleColors.RESET);
    }

    /**
     * Ajoute une nouvelle catégorie via la console.
     * Vérifie que le nom de la catégorie n'existe pas déjà.
     * 
     * @param scanner         Scanner utilisé pour la saisie
     * @param categoryService Service pour gérer les catégories
     */
    public static void addCategory(Scanner scanner, CategoryService categoryService) {
        String categoryName = askString(scanner, "Nom de la catégorie : ");

        // Vérifie que la catégorie n'existe pas déjà
        while (categoryService.categoryExistsByName(categoryName)) {
            System.out.println(Helper.ConsoleColors.RED + "Categorie déjà existante !" + Helper.ConsoleColors.RESET);
            categoryName = askString(scanner, "Nom de la catégorie : ");
        }

        categoryService.createCategory(categoryName);
        System.out
                .println(Helper.ConsoleColors.GREEN + "Catégorie créée avec succès !" + Helper.ConsoleColors.RESET);
    }
}
