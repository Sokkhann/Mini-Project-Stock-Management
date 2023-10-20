package co.istad;

import org.nocrala.tools.texttablefmt.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// our class product
public class Product {

    //product's entity
    private Integer id;
    private String name;
    private Double price;
    private Integer quantity;
    private LocalDate importDate;

    // create default constructor
    public Product () {

    }

    // create all arg constructor
    public Product(Integer id, String name, Double price, Integer quantity, LocalDate importDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.importDate = importDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getImportDate() {
        return importDate;
    }

    public void setImportDate(LocalDate importDate) {
        this.importDate = importDate;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + price + " " + quantity + " " + importDate;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
