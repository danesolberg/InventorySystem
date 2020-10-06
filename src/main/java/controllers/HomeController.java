package controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import app.App;
import models.InHouse;
import models.Part;
import models.Product;
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
import javafx.scene.control.Button;
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
public class HomeController implements Initializable {
    @FXML private Button exitButton;
    @FXML private TableView<Part> partsTableView;
    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInventoryColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;
    @FXML private TextField partSearch;
    @FXML private TableView<Product> productsTableView;
    @FXML private TableColumn<Product, Integer> productIdColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Integer> productInventoryColumn;
    @FXML private TableColumn<Product, Double> productPriceColumn;
    @FXML private TextField productSearch;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        partsTableView.setItems(App.inventory.getAllParts());
        
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        productsTableView.setItems(App.inventory.getAllProducts());
    }
    
    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void switchToAddPart(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/Views/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void switchToModifyPart(ActionEvent event) throws IOException {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        
        if (selectedPart != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Views/ModifyPart.fxml"));
            Parent modifyPartParent = loader.load();
            ModifyPartController controller = loader.getController();
            controller.receivePart(selectedPart);
            Scene modifyPartScene = new Scene(modifyPartParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(modifyPartScene);
            window.show();
        }
    }
    
    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void switchToAddProduct(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/Views/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void switchToModifyProduct(ActionEvent event) throws IOException {
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        
        if (selectedProduct != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Views/ModifyProduct.fxml"));
            Parent modifyProductParent = loader.load();
            ModifyProductController controller = loader.getController();
            controller.receiveProduct(selectedProduct);
            Scene modifyProductScene = new Scene(modifyProductParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(modifyProductScene);
            window.show();
        }
    }
    
    /**
     *
     */
    @FXML
    private void exitButton() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    
    /**
     *
     */
    @FXML
    private void deletePart() {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will permanently delete the selected part. Do you wish to continue?");
            alert.setTitle("Delete Part");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                App.inventory.getAllParts().remove(selectedPart);
            }
        }
    }
    
    /**
     *
     */
    @FXML
    private void deleteProduct() {
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            if (selectedProduct.getallAssociatedParts().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will permanently delete the selected product. Do you wish to continue?");
                alert.setTitle("Delete Product");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK) {
                    App.inventory.getAllProducts().remove(selectedProduct);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Can not delete a product that has associated parts.");
                alert.showAndWait();
            }
        }
    }
    
    /**
     *
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
        partsTableView.setItems(filteredParts);
    }
    
    /**
     *
     */
    @FXML
    private void filterProducts() {
        ObservableList<Product> allProducts, filteredProducts;
        String search = productSearch.getText();
        
        allProducts = App.inventory.getAllProducts();
        filteredProducts = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (Integer.toString(product.getId()).equals(search)
                    || product.getName().contains(search)) {
                filteredProducts.add(product);
            }
        }
        productsTableView.setItems(filteredProducts);
    }
}
