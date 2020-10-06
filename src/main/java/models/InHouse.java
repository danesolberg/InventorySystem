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
public class InHouse extends Part {
    private int machineId;
    
    /**
     * Creates an instance of an InHouse Part.
     * @param name the name of the part
     * @param price the price of the part
     * @param stock the current stock level
     * @param min the minimum allowable stock level
     * @param max the maximum allowable stock level
     * @param machineId the ID of the machine that produced the part
     */
    public InHouse(String name, double price, int stock, int min, int max, int machineId) {
        super(name, price, stock, min, max);
        this.machineId = machineId;
    }
    
    /**
     *
     * @return the ID of the machine that produced the part
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Sets the ID of the machine that produced the part.
     * @param machineId the ID of the machine that produced the part
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
