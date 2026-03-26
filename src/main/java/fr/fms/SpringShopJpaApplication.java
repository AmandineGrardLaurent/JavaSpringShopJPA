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
					ArticleConsoleController.displayArticlesByCategoryId(scanner, categoryService);
					break;
				case 12:
					System.out.println("Bonne journée et à bientôt.");
					exit = true;
					break;
				default:
					break;
			}
		}

		// System.out.println("Affichage des articles de la catégorie 1 : ");
		// for(Article article : articleService.getArticleByCategoryId(1L)){
		// System.out.println(article);
		// }

		// System.out.println("Affichage de tous les articles de la marque Samsung et
		// serie S: ");
		// for(Article article :
		// articleService.getArticleByBrandContainingAndDescriptionContaining("Sam",
		// "S")){
		// System.out.println(article);
		// }

		// System.out.println("Modification du prix de l'article 1 : 350 -> 300");
		// articleService.updatePrice(1L, 300);

		// System.out.println("Modification de la description de l'article 1 : S10 ->
		// S11");
		// articleService.updateDescription(1L, "S11");

		// System.out.println("Affichage de toutes les catégories par ordre croissant :
		// ");
		// for(Category category : categoryService.getAllCategoryByOrderByNameAsc()){
		// System.out.println(category);
		// }

		// System.out.println("Affichage de toutes les catégories par ordre décroissant
		// : ");
		// for(Category category : categoryService.getAllCategoryByOrderByNameDesc()){
		// System.out.println(category);
		// }

		// // System.out.println("Suppression des articles moins de 301€");
		// // articleService.deleteArticleByPriceLessThan(301);

	}
}
