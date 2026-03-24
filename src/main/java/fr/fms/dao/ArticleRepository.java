package fr.fms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;

import fr.fms.entities.Article;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Long>{
    public List<Article> findByBrand(String brand);
    public List<Article> findByBrandContains(String brand);
    public List<Article> findByBrandAndPrice(String brand, double price);
    public List<Article> findByCategoryId(Long categoryId);

    @Query("SELECT a FROM Article a WHERE a.brand LIKE %:x% AND a.description LIKE %:y%")
    public List<Article> searchArticles(@Param("x") String brand, @Param("y") String description);

    public void deleteByPriceLessThan(double price);

}