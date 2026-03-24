package fr.fms.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;

@Entity
public class Article implements Serializable {
    private static final long serialVersionUID=1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String description;
    private double price;

    @ManyToOne
    private Category category;

    public Article(){
    }

    public Article(Long id, String brand, String description, double price, Category category){
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

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Article{" +
               "id=" + id +
               ", brand='" + brand + '\'' +
               ", description='" + description + '\'' +
               ", price=" + price +
               ", category=" + category.getName() +
               '}';
    }
}

