package fr.fms.controller;

import fr.fms.dao.CartArticleRepository;
import fr.fms.dao.CartRepository;
import fr.fms.dao.UserRepository;

import fr.fms.entities.CartArticle;
import fr.fms.entities.User;
import fr.fms.entities.Cart;
import org.springframework.ui.Model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartArticleRepository cartArticleRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/cart")
    public String cart(Model model) {
        // user id hardcoded before implementing authentification
        Long userId = 2L;

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();

                    User user = userRepository.findById(userId).orElse(null);
                    newCart.setUser(user);

                    return cartRepository.save(newCart);
                });
        List<CartArticle> cartArticlesList = cartArticleRepository.findByCartId(cart.getId());

        model.addAttribute("cartArticleList", cartArticlesList);
        return "cart";
    }

}
