package fr.fms.utils;

import java.util.List;
import java.util.Scanner;

import fr.fms.entities.Article;
import fr.fms.entities.Category;
import fr.fms.service.ArticleService;
import fr.fms.service.CategoryService;

public class Helper {

    public class ConsoleColors {
        public static final String RESET = "\u001B[0m";
        public static final String RED = "\u001B[31m";
        public static final String GREEN = "\u001B[32m";
        public static final String YELLOW = "\u001B[33m";
        public static final String BLUE = "\u001B[34m";
    }

    public static int askInt(Scanner scanner, String message) {
        int number;
        while (true) {
            System.out.println(message);
            if (!scanner.hasNextInt()) {
                System.out.println(ConsoleColors.RED + "Saisissez un entier." + ConsoleColors.RESET);
                scanner.next();
                continue;
            }
            number = scanner.nextInt();
            scanner.nextLine();
            break;
        }
        return number;
    }

    public static String askString(Scanner scanner, String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public static double askDouble(Scanner scanner, String message) {
        System.out.print(message);
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }

    public static Long askLong(Scanner scanner, String message) {
        System.out.print(message);
        Long value = scanner.nextLong();
        scanner.nextLine();
        return value;
    }

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

        // Ask the user for a numeric choice
        return askInt(scanner, ConsoleColors.BLUE + "-- Quel est votre choix ? --" + ConsoleColors.RESET);
    }

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

    public static void displayArticleById(Scanner scanner, ArticleService articleService) {
        Long articleId = Long.valueOf(askInt(scanner, "Quel article souhaitez-vous consulter ?"));

        System.out.printf("Affichage de l'article %d :\n", articleId);

        System.out.println(
                articleService.getArticleById(articleId)
                        .map(Object::toString)
                        .orElse(Helper.ConsoleColors.RED + "Article introuvable \n" + Helper.ConsoleColors.RESET));
    }

    public static void deleteArticleById(Scanner scanner, ArticleService articleService) {
        Long articleId = askLong(scanner, "Quel article (id) voulez-vous supprimer ? ");

        while (!articleService.articleExists(articleId)) {
            System.out.println(Helper.ConsoleColors.RED + "Article inexistant !" + Helper.ConsoleColors.RESET);
            articleId = askLong(scanner, "Quel article (id) voulez-vous supprimer ? ");
        }

        articleService.deleteArticle(articleId);
        System.out.printf(
                Helper.ConsoleColors.GREEN + "Article %d supprimé avec succès \n" + Helper.ConsoleColors.RESET,
                articleId);

    }

    public static void addArticle(Scanner scanner, ArticleService articleService, CategoryService categoryService) {
        String brand = askString(scanner, "La marque : ");
        String description = askString(scanner, "La description : ");
        double price = askDouble(scanner, "Le prix : ");
        Long categoryId = askLong(scanner, "L'id de la catégorie : ");

        while (!categoryService.categoryExists(categoryId)) {
            System.out.println(Helper.ConsoleColors.RED + "Categorie inexistante !" + Helper.ConsoleColors.RESET);
            categoryId = askLong(scanner, "L'id de la catégorie : ");
        }

        Category category = categoryService.getCategoryById(categoryId).get();
        articleService.createArticle(brand, description, price, category);

        System.out.println(Helper.ConsoleColors.GREEN + "Article créé avec succès !" + Helper.ConsoleColors.RESET);
    }

    public static void addCategory(Scanner scanner, CategoryService categoryService) {
        String categoryName = askString(scanner, "Nom de la catégorie : ");

        while (categoryService.categoryExistsByName(categoryName)) {
            System.out.println(Helper.ConsoleColors.RED + "Categorie déjà existante !" + Helper.ConsoleColors.RESET);
            categoryName = askString(scanner, "Nom de la catégorie : ");
        }

        categoryService.createCategory(categoryName);
        System.out
                .println(Helper.ConsoleColors.GREEN + "Catégorie créée avec succès !" + Helper.ConsoleColors.RESET);
    }
}
