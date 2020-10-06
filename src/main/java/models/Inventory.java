package models;


import models.Product;
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
public class Inventory {
    private final ObservableList<Part> allParts;
    private final ObservableList<Product> allProducts;
    
    /**
     * Create an instance of the Inventory class
     */
    public Inventory() {
        this.allParts = FXCollections.observableArrayList();
        this.allProducts = FXCollections.observableArrayList();
    }
    
    /**
     * Adds a new part to the current inventory
     * @param newPart the part to add to inventory
     */
    public void addPart(Part newPart) {
        if (!this.allParts.contains(newPart)) {
            this.allParts.add(newPart);
        }
    }
    
    /**
     * Adds a new product to the current inventory
     * @param newProduct the product to add to inventory
     */
    public void addProduct(Product newProduct) {
        if (!this.allProducts.contains(newProduct)) {
            this.allProducts.add(newProduct);
        }
    }
    
    /**
     *
     * @param partId the ID of the part to lookup
     * @return the found part
     */
    public Part lookupPart(int partId) {
        for (Part part : this.allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }
    
    /**
     *
     * @param partName the name of the part to lookup
     * @return the found part
     */
    public ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();
        
        for (Part part : this.allParts) {
            if (part.getName().contains(partName)) {
                matchingParts.add(part);
            }
        }
        return matchingParts;
    }
    
    /**
     *
     * @param productId the ID of the product to lookup
     * @return the found product
     */
    public Product lookupProduct(int productId) {
        for (Product product : this.allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }
    
    /**
     *
     * @param productName the name of the product to lookup
     * @return the found product
     */
    public ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> matchingProducts = FXCollections.observableArrayList();
        
        for (Product product : this.allProducts) {
            if (product.getName().contains(productName)) {
                matchingProducts.add(product);
            }
        }
        return matchingProducts;
    }
    
    /**
     *
     * @param index
     * @param selectedPart
     */
    public void updatePart(int index, Part selectedPart) {
        this.allParts.set(index, selectedPart);
    }
    
    /**
     *
     * @param index the index of the product to update in allProducts
     * @param selectedProduct the product to update
     */
    public void updateProduct(int index, Product selectedProduct) {
        this.allProducts.set(index, selectedProduct);
    }
    
    /**
     *
     * @param selectedPart the part to delete
     * @return if the part was found and deleted
     */
    public boolean deletePart(Part selectedPart) {
        return this.allParts.remove(selectedPart);
    }
    
    /**
     *
     * @param selectedProduct the product to delete
     * @return if the product was found and deleted
     */
    public boolean deleteProduct(Product selectedProduct) {
        return this.allProducts.remove(selectedProduct);
    }
    
    /**
     *
     * @return all parts in inventory
     */
    public ObservableList<Part> getAllParts() {
        return this.allParts;
    }
    
    /**
     *
     * @return all products in inventory
     */
    public ObservableList<Product> getAllProducts() {
        return this.allProducts;
    }
}
