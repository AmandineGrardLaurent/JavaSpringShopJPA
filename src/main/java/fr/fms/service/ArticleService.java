package fr.fms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.fms.dao.ArticleRepository;
import fr.fms.entities.Article;
import fr.fms.entities.Category;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    public List<Article> getArticleByCategoryId(Long id) {
        return articleRepository.findByCategoryId(id);
    }

    public List<Article> getArticleByBrandContainingAndDescriptionContaining(String brand, String description) {
        return articleRepository.findByBrandContainingAndDescriptionContaining(brand, description);
    }

    @Transactional
    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    @Transactional
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    @Transactional
    public void deleteArticleByPriceLessThan(double price) {
        articleRepository.deleteByPriceLessThan(price);
    }

    @Transactional
    public void updatePrice(Long id, double price) {
        articleRepository.findById(id).ifPresent(article -> {
            article.setPrice(price);
            articleRepository.save(article);
        });
    }

    @Transactional
    public void updateDescription(Long id, String description) {
        articleRepository.findById(id).ifPresent(article -> {
            article.setDescription(description);
            articleRepository.save(article);
        });
    }

    @Transactional
    public Article createArticle(String brand, String description, double price, Category category) {
        Article article = new Article(null, brand, description, price, category);
        return articleRepository.save(article);
    }

}