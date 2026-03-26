package fr.fms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import fr.fms.entities.Category;
import fr.fms.service.ArticleService;
import fr.fms.service.CategoryService;
import fr.fms.utils.Helper;
import fr.fms.utils.console.ConsoleColors;
import fr.fms.controller.ArticleConsoleController;
import fr.fms.controller.CategoryConsoleController;
import fr.fms.entities.Article;

@SpringBootApplication
public class SpringShopJpaApplication implements CommandLineRunner {
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ArticleService articleService;

	public static void main(String[] args) {
		SpringApplication.run(SpringShopJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Scanner scanner = new Scanner(System.in);
		boolean exit = false;

		System.out
				.println(ConsoleColors.GREEN + "-- Bienvenue dans notre application de gestion d'articles -- \n"
						+ ConsoleColors.RESET);

		while (!exit) {
			int userChoice = Helper.displayChoices(scanner);
			switch (userChoice) {
				case 1:
					Helper.displayItems(articleService.getAllArticles(), "Liste de tous les articles : ",
							ConsoleColors.RED + "Aucun article trouvé" + ConsoleColors.GREEN);
					break;
				case 2:
					ArticleConsoleController.displayArticlesByPage(scanner, articleService);
					break;
				case 3:
					ArticleConsoleController.addArticle(scanner, articleService, categoryService);
					break;
				case 4:
					ArticleConsoleController.displayArticleById(scanner, articleService);
					break;
				case 5:
					ArticleConsoleController.deleteArticleById(scanner, articleService);
					break;
				case 6:
					break;
				case 7:
					CategoryConsoleController.addCategory(scanner, categoryService);
					break;
				case 8:
					CategoryConsoleController.displayCategoryById(scanner, categoryService);
					break;
				case 9:
					CategoryConsoleController.deleteCategoryById(scanner, categoryService);
					break;
				case 10:
					break;
				case 11:
					ArticleConsoleController.displayArticlesByCategoryId(scanner, articleService, categoryService);
					break;
				case 12:
					System.out.println("Bonne journée et à bientôt.");
					exit = true;
					break;
				default:
					break;
			}
		}

	}
}
