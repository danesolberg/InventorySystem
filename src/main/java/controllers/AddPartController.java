package controllers;

/*
 * To change license header, choose License Headers in Project Properties.
 * To change template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import app.App;
import models.InHouse;
import models.Outsourced;
import models.Part;
import models.Validation;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dane
 */
public class AddPartController implements Initializable {
    @FXML private TextField partIdTextField;
    @FXML private TextField partNameTextField;
    @FXML private TextField partInventoryTextField;
    @FXML private TextField partPriceTextField;
    @FXML private TextField partMaxTextField;
    @FXML private TextField partMinTextField;
    @FXML private TextField dynamicIdTextField;
    @FXML private RadioButton inHouseRadioButton;
    @FXML private RadioButton outsourcedRadioButton;
    @FXML private Label dynamicIdLabel;
    
    private ToggleGroup partTypeToggleGroup;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partTypeToggleGroup = new ToggleGroup();
        inHouseRadioButton.setToggleGroup(partTypeToggleGroup);
        outsourcedRadioButton.setToggleGroup(partTypeToggleGroup);
        inHouseRadioButton.setSelected(true);
    }    
    
    /**
     * Change JavaFX scene back to the Home screen.
     * @param event
     * @throws IOException
     */
    @FXML
    private void switchToHome(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/Views/Home.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    /**
     * Updates the current scene to reflect the part type selected by the user.
     */
    @FXML
    private void selectPartType() {
        if (partTypeToggleGroup.getSelectedToggle().equals(inHouseRadioButton)) {
            dynamicIdLabel.setText("Machine ID");
        } else {
            dynamicIdLabel.setText("Company Name");
        }
    }
    
    /**
     * Analyzes the text inputs for the current scene and returns a Validation
     * object containing input validity and any error messages.
     * @return
     */
    private Validation validateInputs() {    
        if (partNameTextField.getText().trim().isEmpty()
                || partInventoryTextField.getText().trim().isEmpty()
                || partPriceTextField.getText().trim().isEmpty()
                || partMaxTextField.getText().trim().isEmpty()
                || partMinTextField.getText().trim().isEmpty()
                || dynamicIdTextField.getText().trim().isEmpty()) {
            return new Validation(false, "Please complete empty fields.");
        } else {
            String name = partNameTextField.getText().trim();
            int stock = Integer.valueOf(partInventoryTextField.getText().trim());
            double price = Double.valueOf(partPriceTextField.getText().trim());
            int max = Integer.valueOf(partMaxTextField.getText().trim());
            int min = Integer.valueOf(partMinTextField.getText().trim());
            
            if (max < min) {
                return new Validation(false, "Minimum inventory must be less than maximum inventory.");
            } else if (stock > max || stock < min) {
                return new Validation(false, "Current inventory must be between max and min.");
            }
            
            if (partTypeToggleGroup.getSelectedToggle().equals(inHouseRadioButton)) {
                	    try {
                        Integer.parseInt(dynamicIdTextField.getText().trim());
                    } catch (NumberFormatException nfe) {
                        return new Validation(false, "Please provide a valid Machine ID.");
                    }
            }
        }
        return new Validation(true);
    }
    
    /**
     * Adds the pertinent Part object to the Inventory object assigned to
     * App.inventory.
     * @param event
     * @throws IOException
     */
    @FXML
    private void save(ActionEvent event) throws IOException {
        Validation validation = validateInputs();
        if (validation.getValidity() == true) {
            String name = partNameTextField.getText();
            int stock = Integer.valueOf(partInventoryTextField.getText().trim());
            double price = Double.valueOf(partPriceTextField.getText().trim());
            int max = Integer.valueOf(partMaxTextField.getText().trim());
            int min = Integer.valueOf(partMinTextField.getText().trim());
            
            Part newPart;
            if (partTypeToggleGroup.getSelectedToggle().equals(inHouseRadioButton)) {
                newPart = new InHouse(name, price, stock, min, max, Integer.valueOf(dynamicIdTextField.getText().trim()));
            } else {
                newPart = new Outsourced(name, price, stock, min, max, dynamicIdTextField.getText().trim());
            }
            
            App.inventory.addPart(newPart);
            switchToHome(event);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, validation.getErrors());
            alert.showAndWait();
        }             
    }
}
