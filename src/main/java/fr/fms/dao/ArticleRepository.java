package fr.fms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;

import fr.fms.entities.Article;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    public List<Article> findByBrand(String brand);

    public List<Article> findByBrandContains(String brand);

    public List<Article> findByBrandAndPrice(String brand, double price);

    public List<Article> findByCategoryId(Long categoryId);

    public List<Article> findByBrandContainingAndDescriptionContaining(String brand, String description);

    public void deleteByPriceLessThan(double price);

    @Modifying
    @Query("UPDATE article a SET a.category = null WHERE a.category.id = :id")
    void removeCategoryFromArticles(@Param("id") Long categoryId);

}