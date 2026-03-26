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
					ArticleConsoleController.updateArticle(scanner, articleService, categoryService);
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
					Helper.displayItems(categoryService.getAllCategoryByOrderByNameAsc(),
							"Liste de tous les catégories : ",
							ConsoleColors.RED + "Aucune catégorie trouvée" + ConsoleColors.GREEN);
					break;
				case 13:
					System.out.println("Bonne journée et à bientôt.");
					exit = true;
					break;
				default:
					break;
			}
		}

		// -------------------------- EXO 1 --------------------------

		// // Category smartphone = categoryRepository.save(new Category("Smartphone"));
		// // Category tablet = categoryRepository.save(new Category("Tablet"));
		// // Category laptop = categoryRepository.save(new Category("Laptop"));

		// // articleRepository.save(new Article(null,"Samsung", "S10", 500,
		// smartphone));
		// // articleRepository.save(new Article(null,"Samsung", "S9", 350,
		// smartphone));

		// // articleRepository.save(new Article(null,"Asus", "R510", 600, laptop));

		// // articleRepository.save(new Article(null,"Ipad", "Apple", 350, tablet));

		// System.out.println("Affichage des articles de la catégorie 1 : ");
		// for(Article article : articleRepository.findByCategoryId(1L)){
		// System.out.println(article);
		// }

		// System.out.println("Affiche d'un article 1 : ");
		// System.out.println(articleRepository.findById(1L));

		// System.out.println("Affichage de tous les articles : ");
		// for(Article article : articleRepository.findAll()){
		// System.out.println(article);
		// }

		// System.out.println("Affichage de tous les articles de la marque Samsung et
		// serie S: ");
		// for(Article article : articleRepository.searchArticles("Sam", "S")){
		// System.out.println(article);
		// }

		// // System.out.println("Suppression de l'id 3");
		// // articleRepository.deleteById(3L);

		// System.out.println("Affichage de tous les articles : ");
		// for(Article article : articleRepository.findAll()){
		// System.out.println(article);
		// }

		// System.out.println("Modification du prix de l'article 1 : 350 -> 300");
		// Optional<Article> optional = articleRepository.findById(1L);
		// if(optional.isPresent()){
		// Article article = optional.get();
		// article.setPrice(300);
		// articleRepository.save(article);
		// }

		// System.out.println("Modification de la description de l'article 1 : S10 ->
		// S11");
		// Optional<Article> optional1 = articleRepository.findById(1L);
		// if(optional.isPresent()){
		// Article article = optional1.get();
		// article.setDescription("S11");
		// articleRepository.save(article);
		// }

		// System.out.println("Affiche d'un article 2 : ");
		// System.out.println(articleRepository.findById(1L));

		// System.out.println("Affichage de toutes les catégories par ordre croissant :
		// ");
		// for(Category category : categoryRepository.findAllByOrderByNameAsc()){
		// System.out.println(category);
		// }

		// System.out.println("Affichage de toutes les catégories par ordre décroissant
		// : ");
		// for(Category category : categoryRepository.findAllByOrderByNameDesc()){
		// System.out.println(category);
		// }

		// // System.out.println("Suppression des articles moins de 301€");
		// // articleRepository.deleteByPriceLessThan(301);
	}
}
