package fr.fms.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.fms.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    public Optional<Cart> findByUserId(Long userId);
}
