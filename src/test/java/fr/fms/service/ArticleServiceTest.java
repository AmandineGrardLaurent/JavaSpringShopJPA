package fr.fms.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import fr.fms.dao.ArticleRepository;
import fr.fms.entities.Article;
import fr.fms.entities.Category;

public class ArticleServiceTest {
    public Article article;
    public Category category;

    @Mock
    ArticleRepository articleRepository;

    @InjectMocks
    ArticleService articleService;

    @BeforeEach
    void setup() {
        // Initiate mocks and inject them into the service
        MockitoAnnotations.openMocks(this);

        category = new Category();
        category.setName("Smartphone");
        category.setId(1L);

        article = new Article(
                10L,
                "Iphone",
                "15",
                200,
                category);
    }

    @Test
    void should_find_all_articles() {

    }

    @Test
    void should_find_article_by_id() {
        // Test to verify that the service can find an article by its ID

        System.out.println("Test: should_find_article_by_id");

        // GIVEN
        when(articleRepository.findById(10L))
                .thenReturn(Optional.of(article));

        // WHEN
        Optional<Article> result = articleService.getArticleById(10L);

        // THEN
        assertTrue(result.isPresent());

        assertEquals("Iphone", result.get().getBrand());
        assertEquals("15", result.get().getDescription());
        assertEquals(200, result.get().getPrice());
        assertEquals("Smartphone", result.get().getCategory().getName());

        verify(articleRepository).findById(10L);
    }

    @Test
    void should_find_articles_by_category_id() {
        // Test to verify that the service can find articles by their category ID

        System.out.println("Test: should_find_articles_by_category_id");

        List<Article> articleList = new ArrayList<>();

        Article article2 = new Article(
                11L,
                "Samsung",
                "Galaxy S24",
                300,
                category);

        articleList.add(article);
        articleList.add(article2);

        // GIVEN
        when(articleRepository.findByCategoryId(category.getId()))
                .thenReturn(articleList);

        // WHEN
        List<Article> result = articleService.getArticlesByCategoryId(category.getId());

        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());

        Article articleOne = result.get(0);
        Article articleTwo = result.get(1);

        assertEquals("Iphone", articleOne.getBrand());
        assertEquals("15", articleOne.getDescription());
        assertEquals(200, articleOne.getPrice());
        assertEquals("Smartphone", articleOne.getCategory().getName());

        assertEquals("Samsung", articleTwo.getBrand());
        assertEquals("Galaxy S24", articleTwo.getDescription());
        assertEquals(300, articleTwo.getPrice());
        assertEquals("Smartphone", articleTwo.getCategory().getName());

        verify(articleRepository, times(1)).findByCategoryId(category.getId());
    }

    @Test
    void should_return_empty_when_article_not_found() {
        // Test to verify that the service return empty when the article is not found by
        // its ID

        System.out.println("Test: should_return_empty_when_article_not_found");

        // GIVEN
        when(articleRepository.findById(99L))
                .thenReturn(Optional.empty());

        // WHEN
        Optional<Article> result = articleService.getArticleById(99L);

        // THEN
        assertFalse(result.isPresent());
        verify(articleRepository, times(1)).findById(99L);
    }

    @Test
    void should_find_articles_by_brand_and_description() {
        // Test to verify that the service can find an article by its brand and its
        // description

        System.out.println("Test: should_find_articles_by_brand_and_description");

        List<Article> articleList = new ArrayList<>();
        articleList.add(article);

        // GIVEN
        when(articleRepository.findByBrandContainingAndDescriptionContaining("Iph", "5")).thenReturn(articleList);

        // WHEN
        List<Article> result = articleService.getArticleByBrandContainingAndDescriptionContaining("Iph", "5");

        Article articleOne = result.get(0);

        // THEN
        assertNotNull(result);
        assertEquals(1, result.size());

        assertEquals("Iphone", articleOne.getBrand());
        assertEquals("15", articleOne.getDescription());
        assertEquals(200, articleOne.getPrice());
        assertEquals("Smartphone", articleOne.getCategory().getName());

        verify(articleRepository, times(1)).findByBrandContainingAndDescriptionContaining("Iph", "5");
    }

    @Test
    void should_create_article() {
        // Test to verify that the service correctly creates a new article

        System.out.println("Test: should_create_article");

        Category category = new Category();
        category.setName("Smartphone");

        // GIVEN

        // any(Article.class) is used to match any Article object passed to the save
        // method.
        // Mock the repository save method to return the same Article instance
        // that is passed as argument.
        when(articleRepository.save(any(Article.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN

        // Call the service method to create a new Article
        Article result = articleService.createArticle("Iphone", "15", 200, category);

        // THEN

        // Capture the Article object passed to the repository save method
        ArgumentCaptor<Article> captor = ArgumentCaptor.forClass(Article.class);
        verify(articleRepository, times(1)).save(captor.capture());

        Article saved = captor.getValue();

        // Verify that the saved Article is correct
        assertNotNull(saved);
        assertEquals("Iphone", saved.getBrand());
        assertEquals("15", saved.getDescription());
        assertEquals(200, saved.getPrice());
        assertEquals("Smartphone", saved.getCategory().getName());

        // Verify that the service returns the same instance as the saved Article
        assertEquals(saved, result);
    }

    @Test
    void should_delete_article_by_id() {
        // Test to verify that the service correctly deletes an article by its ID

        System.out.println("Test: should_delete_article_by_id");

        // GIVEN
        Long articleId = 10L;

        // WHEN
        articleService.deleteArticle(articleId);

        // THEN
        verify(articleRepository, times(1)).deleteById(articleId);
    }

    @Test
    void should_delete_articles_below_price() {
        // Test to verify that the service correctly deletes articles with a price less
        // than a specified value

        System.out.println("Test: should_delete_articles_below_price");

        // GIVEN
        double articlePrice = 200;

        // WHEN
        articleService.deleteArticleByPriceLessThan(articlePrice);

        // THEN
        verify(articleRepository, times(1)).deleteByPriceLessThan(articlePrice);
    }

}
