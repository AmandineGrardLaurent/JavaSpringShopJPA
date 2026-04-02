package fr.fms.controller;

import fr.fms.dao.ArticleRepository;
import fr.fms.dao.CartArticleRepository;
import fr.fms.dao.CartRepository;
import fr.fms.dao.UserRepository;

import fr.fms.entities.CartArticle;
import fr.fms.entities.User;
import fr.fms.entities.Article;
import fr.fms.entities.Cart;
import org.springframework.ui.Model;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller responsible for managing the shopping cart.
 *
 * Handles operations such as displaying the cart, adding articles,
 * updating quantities, and removing items.
 *
 * Note: User authentication is not yet implemented, so a default
 * user (id = 1) is used.
 */
@Controller
public class CartController {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartArticleRepository cartArticleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;

    /**
     * Displays the current user's cart.
     * 
     * If the cart does not exist for the user, it will be created automatically.
     * Calculates the total price of all items in the cart.
     */
    @GetMapping("/cart")
    public String cart(Model model) {
        // user id hardcoded before implementing authentification
        Long userId = 1L;

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    User user = userRepository.findById(userId).orElse(null);
                    newCart.setUser(user);

                    return cartRepository.save(newCart);
                });
        List<CartArticle> cartArticlesList = cartArticleRepository.findByCartId(cart.getId());

        double totalPrice = cartArticlesList.stream()
                .mapToDouble(cartArticle -> cartArticle.getQuantity() * cartArticle.getArticle().getPrice())
                .sum();

        model.addAttribute("cartArticleList", cartArticlesList);
        model.addAttribute("totalPrice", totalPrice);
        return "cart";
    }

    /**
     * Adds an article to the cart.
     *
     * If the article already exists in the cart, its quantity is incremented.
     * Otherwise, a new CartArticle entry is created.
     */
    @PostMapping("/cart/save")
    public String save(Long articleId, int quantity) {

        Long userId = 1L;

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    User user = userRepository.findById(userId).orElse(null);
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article non trouvé"));

        Optional<CartArticle> optionalCartArticle = cartArticleRepository.findByCartIdAndArticleId(cart.getId(),
                articleId);

        CartArticle cartArticle;

        if (optionalCartArticle.isPresent()) {
            cartArticle = optionalCartArticle.get();
            cartArticle.setQuantity(cartArticle.getQuantity() + quantity);
        } else {
            cartArticle = new CartArticle();
            cartArticle.setCart(cart);
            cartArticle.setArticle(article);
            cartArticle.setQuantity(quantity);
        }

        cartArticleRepository.save(cartArticle);

        return "redirect:/cart";
    }

    /**
     * Updates the quantity of an article in the cart.
     *
     * If quantity is set to 0 or less, the item is removed from the cart.
     */
    @PostMapping("/cart/edit")
    public String edit(Long cartArticleId, int quantity) {

        CartArticle cartArticle = cartArticleRepository.findById(cartArticleId)
                .orElseThrow(() -> new RuntimeException("Article du panier non trouvé"));

        if (quantity <= 0) {
            cartArticleRepository.delete(cartArticle);
        } else {
            cartArticle.setQuantity(quantity);
            cartArticleRepository.save(cartArticle);
        }

        return "redirect:/cart";
    }

    /**
     * Removes an article from the cart.
     */
    @GetMapping("/cart/delete")
    public String delete(Long id) {
        cartArticleRepository.deleteById(id);
        return "redirect:/cart";
    }

}
