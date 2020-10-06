package models;


import java.util.concurrent.atomic.AtomicInteger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dane
 */
public class Product {
    private static final AtomicInteger COUNT = new AtomicInteger(0);
    private final ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     *
     * @param name the name of the product
     * @param price the price of the product
     * @param stock the current stock level of the product
     * @param min the minimum allowable stock level
     * @param max the maximum allowable stock level
     */
    public Product(String name, double price, int stock, int min, int max) {
        this.id = COUNT.incrementAndGet();
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        associatedParts = FXCollections.observableArrayList();
    }
    
    /**
     * Sets the Id.
     * @param id the product's ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the name.
     * @param name the product's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the price.
     * @param price the product's price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the stock amount.
     * @param stock the product's stock level
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Sets the minimum allowable stock.
     * @param min the minimum allowable stock level
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Sets the maximum allowable stock.
     * @param max the maximum allowable stock level
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     *
     * @return the ID
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     *
     * @return the stock amount
     */
    public int getStock() {
        return stock;
    }

    /**
     *
     * @return the minimum stock
     */
    public int getMin() {
        return min;
    }

    /**
     *
     * @return the maximum stock
     */
    public int getMax() {
        return max;
    }
    
    /**
     * Associate a part to the product.
     * @param part the part to associate
     */
    public void addAssociatedPart(Part part) {
        if (!associatedParts.contains(part)) {
            associatedParts.add(part);
        }
    }
    
    /**
     * Disassociate a part from the product.
     * @param part the part to disassociate
     * @return
     */
    public boolean deleteAssociatedPart(Part part) {
        return associatedParts.remove(part);
    }
    
    /**
     *
     * @return all associated parts
     */
    public ObservableList<Part> getallAssociatedParts() {
        return associatedParts;
    }
}
