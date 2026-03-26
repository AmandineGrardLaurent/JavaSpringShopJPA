package fr.fms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import fr.fms.dao.ArticleRepository;
import fr.fms.dao.CategoryRepository;
import fr.fms.entities.Article;
import fr.fms.entities.Category;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;

    public CategoryService(CategoryRepository categoryRepository, ArticleRepository articleRepository) {
        this.categoryRepository = categoryRepository;
        this.articleRepository = articleRepository;
    }

    public List<Category> getAllCategoryByOrderByNameAsc() {
        return categoryRepository.findAllByOrderByNameAsc();
    }

    public List<Category> getAllCategoryByOrderByNameDesc() {
        return categoryRepository.findAllByOrderByNameDesc();
    }

    public Optional<Category> getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    public boolean categoryExists(Long categoryId) {
        return categoryRepository.existsById(categoryId);
    }

    public boolean categoryExistsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    @Transactional
    public Category createCategory(String name) {
        Category category = new Category(name);
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        articleRepository.removeCategoryFromArticles(categoryId);
        categoryRepository.deleteById(categoryId);
    }
}
