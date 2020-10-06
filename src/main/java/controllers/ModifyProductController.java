package controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import app.App;
import models.Product;
import models.Part;
import models.Validation;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dane
 */
public class ModifyProductController implements Initializable {
    private Product productToModify;
    @FXML private TextField productIdTextField;
    @FXML private TextField productNameTextField;
    @FXML private TextField productInventoryTextField;
    @FXML private TextField productPriceTextField;
    @FXML private TextField productMaxTextField;
    @FXML private TextField productMinTextField;
    @FXML private TableView<Part> allPartsTableView, associatedPartsTableView;
    @FXML private TableColumn<Part, Integer> allPartsIdColumn, assPartsIdColumn;
    @FXML private TableColumn<Part, String> allPartsNameColumn, assPartsNameColumn;
    @FXML private TableColumn<Part, Integer> allPartsInventoryColumn, assPartsInventoryColumn;
    @FXML private TableColumn<Part, Double> allPartsPriceColumn, assPartsPriceColumn;
    @FXML private TextField partSearch;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        allPartsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        assPartsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        assPartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        assPartsInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        allPartsTableView.setItems(App.inventory.getAllParts());
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
     * Sets the parts in allPartsTableView equal to parts matching search
     * filter in partSearch.
     */
    @FXML
    private void filterParts() {
        ObservableList<Part> allParts, filteredParts;
        String search = partSearch.getText();
        
        allParts = App.inventory.getAllParts();
        filteredParts = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (Integer.toString(part.getId()).equals(search)
                    || part.getName().contains(search)) {
                filteredParts.add(part);
            }
        }
        allPartsTableView.setItems(filteredParts);
    }
    
    /**
     * Adds a part to the associatedParts attribute of the pertinent Product
     * object.
     */
    @FXML
    private void addAssociatedPart() {
        Part selectedPart = allPartsTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            productToModify.addAssociatedPart(selectedPart);
        }
    }
    
    /**
     * Removes a part from the associatedParts attribute of the pertinent Product
     * object.
     */
    @FXML
    private void removeAssociatedPart() {
        Part selectedPart = associatedPartsTableView.getSelectionModel().getSelectedItem();
        
        if (selectedPart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will permanently disassociate the selected part. Do you wish to continue?");
            alert.setTitle("Remove Associated Part");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                productToModify.deleteAssociatedPart(selectedPart);
            }
        }
    }
    
    /**
     * Receives Product data from an external screen for further modification.
     * @param product
     */
    public void receiveProduct(Product product) {
        productToModify = product;

        productIdTextField.setText(Integer.toString(product.getId()));
        productNameTextField.setText(product.getName());
        productInventoryTextField.setText(Integer.toString(product.getStock()));
        productPriceTextField.setText(Double.toString(product.getPrice()));
        productMaxTextField.setText(Integer.toString(product.getMax()));
        productMinTextField.setText(Integer.toString(product.getMin()));
        
        associatedPartsTableView.setItems(product.getallAssociatedParts());
    }
    
    /**
     * Analyzes the text inputs for the current scene and returns a Validation
     * object containing input validity and any error messages.
     * @return
     */
    private Validation validateInputs() {    
        if (productNameTextField.getText().trim().isEmpty()
                || productInventoryTextField.getText().trim().isEmpty()
                || productPriceTextField.getText().trim().isEmpty()
                || productMaxTextField.getText().trim().isEmpty()
                || productMinTextField.getText().trim().isEmpty()) {
            return new Validation(false, "Please complete empty fields.");
        } else {
            String name = productNameTextField.getText().trim();
            int stock = Integer.valueOf(productInventoryTextField.getText().trim());
            double price = Double.valueOf(productPriceTextField.getText().trim());
            int max = Integer.valueOf(productMaxTextField.getText().trim());
            int min = Integer.valueOf(productMinTextField.getText().trim());
            
            if (max < min) {
                return new Validation(false, "Minimum inventory must be less than maximum inventory.");
            } else if (stock > max || stock < min) {
                return new Validation(false, "Current inventory must be between max and min.");
            }
        }
        return new Validation(true);
    }
    
    /**
     * Adds the pertinent Product object to the Inventory object assigned to
     * App.inventory.
     * @param event
     * @throws IOException
     */
    @FXML
    private void save(ActionEvent event) throws IOException {
        Validation validation = validateInputs();
        if (validation.getValidity() == true) {
            String name = productNameTextField.getText();
            int stock = Integer.valueOf(productInventoryTextField.getText().trim());
            double price = Double.valueOf(productPriceTextField.getText().trim());
            int max = Integer.valueOf(productMaxTextField.getText().trim());
            int min = Integer.valueOf(productMinTextField.getText().trim());
            
            Product newProduct = new Product(name, price, stock, min, max);
            associatedPartsTableView.getItems().forEach((part) -> {
                newProduct.addAssociatedPart(part);
            });
            
            App.inventory.getAllProducts().set(App.inventory.getAllProducts().indexOf(productToModify), newProduct);
            switchToHome(event);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, validation.getErrors());
            alert.showAndWait();
        }             
    }
}
