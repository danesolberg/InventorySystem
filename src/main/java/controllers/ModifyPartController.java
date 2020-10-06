package controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import app.App;
import models.InHouse;
import models.Outsourced;
import models.Part;
import models.Validation;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dane
 */
public class ModifyPartController implements Initializable {
    private Part partToModify;
    @FXML private TextField partIdTextField;
    @FXML private TextField partNameTextField;
    @FXML private TextField partInventoryTextField;
    @FXML private TextField partPriceTextField;
    @FXML private TextField partMaxTextField;
    @FXML private TextField partMinTextField;
    @FXML private TextField dynamicIdTextField;
    @FXML private RadioButton inHouseRadioButton;
    @FXML private RadioButton outsourcedRadioButton;
    private ToggleGroup partTypeToggleGroup;
    @FXML private Label dynamicIdLabel;
    
    
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
     * Receives Part data from an external screen for further modification.
     * @param part
     */
    public void receivePart(Part part) {
        partToModify = part;
        
        if (part instanceof InHouse) {
            inHouseRadioButton.setSelected(true);
            dynamicIdTextField.setText(Integer.toString(((InHouse) part).getMachineId()));
        } else if (part instanceof Outsourced) {
            outsourcedRadioButton.setSelected(true);
            dynamicIdTextField.setText(((Outsourced) part).getCompanyName());
        }
        
        selectPartType();
        
        partIdTextField.setText(Integer.toString(part.getId()));
        partNameTextField.setText(part.getName());
        partInventoryTextField.setText(Integer.toString(part.getStock()));
        partPriceTextField.setText(Double.toString(part.getPrice()));
        partMaxTextField.setText(Integer.toString(part.getMax()));
        partMinTextField.setText(Integer.toString(part.getMin()));
    }
    
    /**
     * Analyzes the text inputs for the current scene and returns a <Validation>
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
     * Adds the pertinent <Part> object to the <Inventory> object assigned to
     * <App.inventory>.
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
            
            App.inventory.getAllParts().set(App.inventory.getAllParts().indexOf(partToModify), newPart);
            switchToHome(event);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, validation.getErrors());
            alert.showAndWait();
        }             
    }
}
