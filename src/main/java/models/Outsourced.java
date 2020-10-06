package models;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dane
 */
public class Outsourced extends Part {
    private String companyName;
    
    /**
     * Creates an instance of an Outsourced Part.
     * @param name the name of the part
     * @param price the price of the part
     * @param stock the stock level of the part
     * @param min minimum stock level
     * @param max maximum stock level
     * @param companyName the name of the manufacturing company
     */
    public Outsourced(String name, double price, int stock, int min, int max, String companyName) {
        super(name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     *
     * @return the name of the company that produced the outsourced part.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the company name.
     * @param companyName the name of the manufacturing company
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    
}
