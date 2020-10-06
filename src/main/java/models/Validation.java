/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author dane
 */
public class Validation {
    private boolean validInputs;
    private String errorMessage;
    
    /**
     * Create an instance of the Validation class.
     * @param valid whether or not the inputs are valid
     */
    public Validation(boolean valid) {
        validInputs = valid;
        errorMessage = "";
    }
    
    /**
     * Create an instance of the Validation class.
     * @param valid whether or not the inputs are valid
     * @param errorMessage a message describing the invalid inputs
     */
    public Validation(boolean valid, String errorMessage) {
        validInputs = valid;
        this.errorMessage = errorMessage;
    }
    
    /**
     *
     * @return whether inputs are valid or not
     */
    public boolean getValidity() {
        return validInputs;
    }
    
    /**
     *
     * @return any error message
     */
    public String getErrors() {
        return errorMessage;
    }
}
