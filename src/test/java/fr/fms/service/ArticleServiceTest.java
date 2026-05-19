package fr.fms.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Mock
    ArticleRepository articleRepository;

    @InjectMocks
    ArticleService articleService;

    @BeforeEach
    void setup() {
        // Initiate mocks and inject them into the service
        MockitoAnnotations.openMocks(this);

        Category category = new Category();
        category.setName("Smartphone");

        article = new Article(
                10L,
                "Iphone",
                "15",
                200,
                category);
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
        verify(articleRepository).findById(99L);
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

}
