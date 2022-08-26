package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controls the Main Screen
 * @author Andre Simmons
 */
public class MainScreenController implements Initializable {
    /**
     * The user selected part that needs to be modified
     */
    private static Part selectedPartModify;
    /**
     * The user selected product that needs to be modified
     */
    private static Product selectedProductModify;

    /**
     * Table view for the products
     */
    @FXML
    private TableView<Product> productTableView;

    /**
     * Text field for searching for parts
     */
    @FXML
    private TextField partSearchTextField;

    /**
     * Table view for the parts
     */
    @FXML
    private TableView<Part> partTableView;

    /**
     * Column for part id
     */
    @FXML
    private TableColumn<Part, Integer> partIdColumn;

    /**
     * Column for part name
     */
    @FXML
    private TableColumn<Part, String> partNameColumn;

    /**
     * Column for part inventory value
     */
    @FXML
    private TableColumn<Part, Integer> partInvColumn;

    /**
     * Column for part price
     */
    @FXML
    private TableColumn<Part, Double> partPriceColumn;

    /**
     * Text field for searching for products
     */
    @FXML
    private TextField productSearchTextField;

    /**
     * Column for product id
     */
    @FXML
    private TableColumn<Product, Integer> productIdColumn;

    /**
     * Column for product name
     */
    @FXML
    private TableColumn<Product, String> productNameColumn;
    /**
     * Column for product inventory level
     */
    @FXML
    private TableColumn<Product, Integer> productInvColumn;

    /**
     * Column for product price
     */
    @FXML
    private TableColumn<Product, Double> productPriceColumn;

    /**
     * Function to get the user selected part
     * @return user selected part
     */
    public static Part getSelectedPart(){
        return selectedPartModify;
    }

    /**
     * Function to get the user selected product
     * @return user selected product
     */
    public static Product getSelectedProduct(){
        return selectedProductModify;
    }

    /**
     * Controls the exit button
     * Exits the application
     * @param actionEvent
     */
    public void onClickExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Controls the product add button
     * Launches the Add Product Screen
     * @param actionEvent
     * @throws IOException
     */
    public void onClickProductAdd(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/AddProduct.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Controls the product modify button
     * Launches the Modify Product Screen
     * @param actionEvent
     * @throws IOException
     */
    public void onClickProductModify(ActionEvent actionEvent) throws IOException {
        selectedProductModify = productTableView.getSelectionModel().getSelectedItem();
        if (selectedProductModify == null) {
            displayAlert(4);
        } else {
            Parent parent = FXMLLoader.load(getClass().getResource("../view/ModifyProduct.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Controls the product delete button
     * Displays an error message if there was no product selected
     * Displays a confirmation before deleting the product
     * Prevents the user from deleting a product that has one or more associated parts
     * @param actionEvent
     */
    public void onClickProductDelete(ActionEvent actionEvent) {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            displayAlert(4);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure that you want to delete the selected product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                ObservableList<Part> assocParts = selectedProduct.getAllAssociatedParts();
                if (assocParts.size() >= 1) {
                    displayAlert(5);
                } else {
                    Inventory.deleteProduct(selectedProduct);
                }
            }
        }
    }

    /**
     * Controls the part add button
     * Launches the Add Part Screen
     * @param actionEvent
     * @throws IOException
     */
    public void onClickPartAdd(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/AddPart.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Controls the part modify button
     * Launches the modify part screen
     * @param actionEvent
     * @throws IOException
     */
    public void onClickPartModify(ActionEvent actionEvent) throws IOException {
        selectedPartModify = partTableView.getSelectionModel().getSelectedItem();
        if (selectedPartModify == null) {
            displayAlert(3);
        } else {
            Parent parent = FXMLLoader.load(getClass().getResource("../view/ModifyPart.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Controls part delete button
     * Displays an error message if no part was selected
     * Displays a confirmation message before deleting the part
     * @param actionEvent
     */
    public void onClickPartDelete(ActionEvent actionEvent) {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            displayAlert(3);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure that you want to delete the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            }
        }
    }

    /**
     * Function that displays different alert messages to the user
     * @param alertType
     */
    private void displayAlert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alert.setTitle("Information");
                alert.setHeaderText("Part Not Found! Please enter a valid part id or name");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Information");
                alert.setHeaderText("Product Not Found! Please enter a valid part id or name");
                alert.showAndWait();
                break;
            case 3:
                alertError.setTitle("Error");
                alertError.setHeaderText("No Part Selected");
                alertError.showAndWait();
                break;
            case 4:
                alertError.setTitle("Error");
                alertError.setHeaderText("No Product Selected");
                alertError.showAndWait();
                break;
            case 5:
                alertError.setTitle("Error");
                alertError.setHeaderText("Parts Associated With Product");
                alertError.setContentText("All parts must be removed from product before deletion.");
                alertError.showAndWait();
                break;
        }
    }

    /**
     * Controls the part search button
     * Searches for part by id or name
     * Displays an error message if the part is not found
     * @param actionEvent
     */
    public void partSearchAction(ActionEvent actionEvent) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchString = partSearchTextField.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(searchString) ||
                    part.getName().contains(searchString)) {
                partsFound.add(part);
            }
        }
        partTableView.setItems(partsFound);
        if (partsFound.size() == 0) {
            displayAlert(1);
        }
    }

    /**
     * Controls the product search button
     * Searches for product by id or name
     * Displays an error message if the product is not found
     * @param actionEvent
     */
    public void productSearchAction(ActionEvent actionEvent) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> productsFound = FXCollections.observableArrayList();
        String searchString = productSearchTextField.getText();
        for (Product product : allProducts) {
            if (String.valueOf(product.getId()).contains(searchString) ||
                    product.getName().contains(searchString)) {
                productsFound.add(product);
            }
        }
        productTableView.setItems(productsFound);
        if (productsFound.size() == 0) {
            displayAlert(2);
        }
    }

    /**
     * Initializes this controller and adds data to the tables
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTableView.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Populate products table view
        productTableView.setItems(Inventory.getAllProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        System.out.println("I am initialized!");
    }
}
