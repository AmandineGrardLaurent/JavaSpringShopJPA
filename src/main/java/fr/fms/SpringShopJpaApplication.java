package fr.fms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;

import fr.fms.entities.Category;
import fr.fms.service.ArticleService;
import fr.fms.service.CategoryService;
import fr.fms.entities.Article;


@SpringBootApplication
public class SpringShopJpaApplication implements CommandLineRunner{
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ArticleService articleService;

	public static void main(String[] args) {
		SpringApplication.run(SpringShopJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Category smartphone = categoryRepository.save(new Category("Smartphone"));
		// Category tablet = categoryRepository.save(new Category("Tablet"));
		// Category laptop = categoryRepository.save(new Category("Laptop"));

		// articleRepository.save(new Article(null,"Samsung", "S10", 500, smartphone));
		// articleRepository.save(new Article(null,"Samsung", "S9", 350, smartphone));

		// articleRepository.save(new Article(null,"Asus", "R510", 600, laptop));

		// articleRepository.save(new Article(null,"Ipad", "Apple", 350, tablet));

		System.out.println("Affichage des articles de la catégorie 1 : ");
		for(Article article : articleService.getArticleByCategoryId(1L)){
			System.out.println(article);
		}

		System.out.println("Affiche d'un article 1 : ");
		System.out.println(articleService.getArticleById(1L));

		System.out.println("Affichage de tous les articles : ");
		for(Article article : articleService.getAllArticles()){
			System.out.println(article);
		}

		System.out.println("Affichage de tous les articles de la marque Samsung et serie S: ");
		for(Article article : articleService.getArticleByBrandContainingAndDescriptionContaining("Sam", "S")){
			System.out.println(article);
		}

		// System.out.println("Suppression de l'id 3");
		// articleService.deleteArticle(3L);

		System.out.println("Modification du prix de l'article 1  : 350 -> 300");
		articleService.updatePrice(1L, 300);

		System.out.println("Modification de la description de l'article 1  : S10 -> S11");
		articleService.updateDescription(1L, "S11");


		System.out.println("Affichage de toutes les catégories par ordre croissant : ");
		for(Category category : categoryService.getAllCategoryByOrderByNameAsc()){
			System.out.println(category);
		}

		System.out.println("Affichage de toutes les catégories par ordre décroissant : ");
		for(Category category : categoryService.getAllCategoryByOrderByNameDesc()){
			System.out.println(category);
		}

		// System.out.println("Suppression des articles moins de 301€");
		// articleService.deleteArticleByPriceLessThan(301);

	}
}

