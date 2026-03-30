package fr.fms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    @Query("UPDATE Article a SET a.category = null WHERE a.category.id = :id")
    void removeCategoryFromArticles(@Param("id") Long categoryId);

    Page<Article> findAllByOrderByBrandAsc(Pageable pageable);

    Page<Article> findByDescriptionContainsIgnoreCase(String description, Pageable pageable);

}