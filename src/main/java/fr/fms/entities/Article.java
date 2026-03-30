package fr.fms.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.DecimalMin;

@Entity
public class Article implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La marque est obligatoire")
    @Size(min = 5, max = 50, message = "La marque doit comporter entre 5 et 50 caractères")
    private String brand;

    @NotNull(message = "La description est obligatoire")
    @Size(min = 5, max = 75, message = "La description doit comporter entre 5 et 75 caractères")
    private String description;

    @NotNull(message = "Le prix est obligatoire")
    @DecimalMin(value = "0.01", message = "Le prix doit être un nombre positif")
    private double price;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;

    public Article() {
    }

    public Article(Long id, String brand, String description, double price, Category category) {
        this.id = id;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return String.format(
                "| %-5s | %-15s | %-25s | %9.2f $ | %-20s |",
                id,
                brand,
                description,
                price,
                (category != null ? category.getName() : "Aucune"));
    }
}
