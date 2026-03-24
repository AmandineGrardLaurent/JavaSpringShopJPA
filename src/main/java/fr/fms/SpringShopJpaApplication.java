package fr.fms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import fr.fms.dao.CategoryRepository;
import fr.fms.dao.ArticleRepository;
import fr.fms.entities.Category;
import fr.fms.entities.Article;

import java.util.Optional;


@SpringBootApplication
public class SpringShopJpaApplication implements CommandLineRunner{
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ArticleRepository articleRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringShopJpaApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		// Category smartphone = categoryRepository.save(new Category("Smartphone"));
		// Category tablet = categoryRepository.save(new Category("Tablet"));
		// Category laptop = categoryRepository.save(new Category("Laptop"));

		// articleRepository.save(new Article(null,"Samsung", "S10", 500, smartphone));
		// articleRepository.save(new Article(null,"Samsung", "S9", 350, smartphone));

		// articleRepository.save(new Article(null,"Asus", "R510", 600, laptop));

		// articleRepository.save(new Article(null,"Ipad", "Apple", 350, tablet));

		System.out.println("Affichage des articles de la catégorie 1 : ");
		for(Article article : articleRepository.findByCategoryId(1L)){
			System.out.println(article);
		}

		System.out.println("Affiche d'un article 1 : ");
		System.out.println(articleRepository.findById(1L));

		System.out.println("Affichage de tous les articles : ");
		for(Article article : articleRepository.findAll()){
			System.out.println(article);
		}

		System.out.println("Affichage de tous les articles de la marque Samsung et serie S: ");
		for(Article article : articleRepository.searchArticles("Sam", "S")){
			System.out.println(article);
		}

		// System.out.println("Suppression de l'id 3");
		// articleRepository.deleteById(3L);

		System.out.println("Affichage de tous les articles : ");
		for(Article article : articleRepository.findAll()){
			System.out.println(article);
		}

		System.out.println("Modification du prix de l'article 1  : 350 -> 300");
		Optional<Article> optional = articleRepository.findById(1L);
		if(optional.isPresent()){
			Article article = optional.get();
			article.setPrice(300);
			articleRepository.save(article);
		}

		System.out.println("Modification de la description de l'article 1  : S10 -> S11");
		Optional<Article> optional1 = articleRepository.findById(1L);
		if(optional.isPresent()){
			Article article = optional1.get();
			article.setDescription("S11");
			articleRepository.save(article);
		}

		System.out.println("Affiche d'un article 2 : ");
		System.out.println(articleRepository.findById(1L));


		System.out.println("Affichage de toutes les catégories par ordre croissant : ");
		for(Category category : categoryRepository.findAllByOrderByNameAsc()){
			System.out.println(category);
		}

		System.out.println("Affichage de toutes les catégories par ordre décroissant : ");
		for(Category category : categoryRepository.findAllByOrderByNameDesc()){
			System.out.println(category);
		}

		// System.out.println("Suppression des articles moins de 301€");
		// articleRepository.deleteByPriceLessThan(301);

	}
}

