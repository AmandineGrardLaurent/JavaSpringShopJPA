package fr.fms.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.fms.entities.Cart;
import fr.fms.entities.CartArticle;

public interface CartArticleRepository extends JpaRepository<CartArticle, Long> {
    public List<CartArticle> findByCartId(Long cartId);

    public Optional<CartArticle> findByCartIdAndArticleId(Long cartId, Long articleId);
}
