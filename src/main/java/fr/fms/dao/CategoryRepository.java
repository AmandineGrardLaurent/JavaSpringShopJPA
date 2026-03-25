package fr.fms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.fms.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    public List<Category> findAllByOrderByNameAsc();

    public List<Category> findAllByOrderByNameDesc();

    public boolean existsByName(String name);
}
