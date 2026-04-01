package fr.fms.web;

import fr.fms.dao.CategoryRepository;
import fr.fms.entities.Category;
import fr.fms.dao.ArticleRepository;
import fr.fms.entities.Article;
import org.springframework.ui.Model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
/**
 * Controller responsible for handling all operations related to articles:
 * listing, searching, filtering, creating, updating and deleting.
 */
public class ArticleController {
    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    CategoryRepository categoryRepository;

    /**
     * Displays the list of articles with optional pagination, search and category
     * filtering.
     *
     * @param model      the Spring MVC model used to pass data to the view
     * @param page       the current page number (default is 0)
     * @param search     the search keyword used to filter articles by description
     * @param categoryId the selected category ID used for filtering (optional)
     * @return the "articles" view template
     */
    @GetMapping("/index")
    public String index(Model model,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "categoryId", required = false) Long categoryId) {
        // Page<Article> articles = articleRepository.findAll(PageRequest.of(page, 5));
        Page<Article> articles;

        if (categoryId != null) {
            articles = articleRepository
                    .findByCategoryIdAndDescriptionContainsIgnoreCase(
                            categoryId, search, PageRequest.of(page, 5));
        } else {
            articles = articleRepository
                    .findByDescriptionContainsIgnoreCase(
                            search, PageRequest.of(page, 5));
        }

        model.addAttribute("listArticle", articles.getContent());
        model.addAttribute("pages", new int[articles.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("search", search);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categories", categoryRepository.findAll());

        return "articles";
    }

    /**
     * Deletes an article by its ID and redirects back to the article list.
     *
     * @param id     the ID of the article to delete
     * @param page   the current page number (to preserve pagination)
     * @param search the current search keyword (to preserve filtering)
     * @return a redirect to the article list page
     */
    @GetMapping("/article/delete")
    public String delete(Long id, int page, String search) {
        articleRepository.deleteById(id);
        return "redirect:/index?page=" + page + "&search=" + search;
    }

    /**
     * Displays the form used to create a new article.
     *
     * @param model the Spring MVC model
     * @return the "articleAdd" view template
     */
    @GetMapping("/article/add")
    public String addForm(Model model) {
        model.addAttribute("article", new Article());
        model.addAttribute("categories", categoryRepository.findAll());
        return "articleAdd";
    }

    /**
     * Handles the submission of the article form (creation or update).
     * Performs validation and saves the article if valid.
     *
     * @param model         the Spring MVC model
     * @param article       the article object bound from the form
     * @param bindingResult contains validation errors if any
     * @return redirect to the list page if success, otherwise reload the form
     */
    @PostMapping("/article/save")
    public String save(Model model, @Valid Article article, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("article", article);
            model.addAttribute("categories", categoryRepository.findAll());
            return "articleAdd";
        }
        // Ensure the category is fully loaded from database before saving
        if (article.getCategory() != null && article.getCategory().getId() != null) {
            Category category = categoryRepository
                    .findById(article.getCategory().getId())
                    .orElse(null);
            article.setCategory(category);
        }

        articleRepository.save(article);
        return "redirect:/index";
    }

    /**
     * Displays the edit form for an existing article.
     * The form is pre-filled with the article's current data.
     *
     * @param id    the ID of the article to edit
     * @param model the Spring MVC model
     * @return the "articleAdd" view template
     */
    @GetMapping("/article/edit")
    public String edit(Long id, Model model) {
        Article article = articleRepository.findById(id).orElse(null);

        model.addAttribute("article", article);
        model.addAttribute("categories", categoryRepository.findAll());

        return "articleAdd";
    }

}