package fr.fms.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import fr.fms.dao.CategoryRepository;
import fr.fms.entities.Category;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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

}
