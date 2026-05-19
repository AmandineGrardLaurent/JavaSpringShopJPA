package fr.fms.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import fr.fms.dao.ArticleRepository;
import fr.fms.entities.Article;
import fr.fms.entities.Category;

public class ArticleServiceTest {
    public static Article article;

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

        // Given
        when(articleRepository.findById(10L))
                .thenReturn(Optional.of(article));

        // When
        Optional<Article> result = articleService.getArticleById(10L);

        // Then
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

        when(articleRepository.findById(99L))
                .thenReturn(Optional.empty());

        Optional<Article> result = articleService.getArticleById(99L);

        assertFalse(result.isPresent());

        verify(articleRepository).findById(99L);
    }

}
