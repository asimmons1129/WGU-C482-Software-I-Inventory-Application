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
 * Controls the Modify Product Screen
 * @author Andre Simmons
 */
public class ModifyProductController implements Initializable {

    /**
     * Column for associated part price
     */
    @FXML
    private TableColumn<Part, Double> aPartPriceColumn;

    /**
     * Column for associated part inventory value
     */
    @FXML
    private TableColumn<Part, Integer> aPartInvColumn;

    /**
     * Column for associated part name
     */
    @FXML
    private TableColumn<Part, String> aPartNameColumn;

    /**
     * Column for associated part id
     */
    @FXML
    private TableColumn<Part, Integer> aPartIDColumn;

    /**
     * Table view for associated part
     */
    @FXML
    private TableView<Part> aPartTableView;

    /**
     * Column for part price
     */
    @FXML
    private TableColumn<Part, Double> partPriceColumn;

    /**
     * Column for part inventory value
     */
    @FXML
    private TableColumn<Part, Integer> partInvColumn;

    /**
     * Column for part name
     */
    @FXML
    private TableColumn<Part, String> partNameColumn;

    /**
     * Column for part id
     */
    @FXML
    private TableColumn<Part, Integer> partIdColumn;

    /**
     * Text field to search for part by id/name
     */
    @FXML
    private TextField partSearchTextField;

    /**
     * Text field for product minimum inventory value
     */
    @FXML
    private TextField productMinTextField;

    /**
     * Text field for product maximum inventory value
     */
    @FXML
    private TextField productMaxTextField;

    /**
     * Text field for product price
     */
    @FXML
    private TextField productPriceTextField;

    /**
     * Text field for product inventory value
     */
    @FXML
    private TextField productInvTextField;

    /**
     * Text field for product name
     */
    @FXML
    private TextField productNameTextField;

    /**
     * Text field for product id
     */
    @FXML
    private TextField productIdTextField;

    /**
     * Table view for all parts
     */
    @FXML
    private TableView<Part> partTableView;

    /**
     * Product selected by the user
     */
    Product userSelectedProduct;

    /**
     * List of all associated parts
     */
    private ObservableList<Part> aParts = FXCollections.observableArrayList();


    /**
     * Controls the add button
     * Adds the selected part to the associated parts table
     * @param actionEvent
     */
    public void onClickAdd(ActionEvent actionEvent) {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(5);
        } else {
            aParts.add(selectedPart);
            aPartTableView.setItems(aParts);
        }
    }

    /**
     * Controls the remove associated parts button
     * Removes part from the associated parts table
     * Displays an error message if no part was selected
     * Displays a confirmation message before removing the part
     * @param actionEvent
     */
    public void onClickRemove(ActionEvent actionEvent) {
        Part selectedPart = aPartTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(5);
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sur that you want to remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                aParts.remove(selectedPart);
                aPartTableView.setItems(aParts);
            }
        }
    }

    /**
     * Controls the save button
     * Updates product in the inventory and launches the main screen
     * Alert messages are displayed if text field is invalid
     * @param actionEvent
     */
    public void onClickSave(ActionEvent actionEvent) {
        try {
            int id = userSelectedProduct.getId();
            String name = productNameTextField.getText();
            Double price = Double.parseDouble(productPriceTextField.getText());
            int stock = Integer.parseInt(productInvTextField.getText());
            int min = Integer.parseInt(productMinTextField.getText());
            int max = Integer.parseInt(productMaxTextField.getText());
            if (name.isEmpty()) {
                displayAlert(6);
            } else {
                if (minValidationCheck(min, max) && inventoryValidationCheck(min, max, stock)) {
                    Product newProduct = new Product(id, name, price, stock, min, max);
                    for (Part part : aParts) {
                        newProduct.addAssociatedPart(part);
                    }
                    Inventory.addProduct(newProduct);
                    Inventory.deleteProduct(userSelectedProduct);
                    mainScreen(actionEvent);
                }
            }
        } catch (Exception e){
            displayAlert(1);
        }
    }

    /**
     * Controls cancel button
     * Displays a confirmation message before returning the user to the main screen
     * @param actionEvent
     * @throws IOException
     */
    public void onClickCancel(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure that you want to cancel these changes and return to the main screen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            mainScreen(actionEvent);
        }
    }

    /**
     * Controls the search button
     * Searches for a part by name or id
     * @param actionEvent
     */
    public void searchAction(ActionEvent actionEvent) {
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
     * Function to return to the main screen
     * @param event
     * @throws IOException
     */
    private void mainScreen(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Function that checks the validity of the minimum inventory value
     * @param min
     * @param max
     * @return
     */
    private boolean minValidationCheck(int min, int max) {
        boolean validMin = true;
        if (min <= 0 || min >= max) {
            validMin = false;
            displayAlert(3);
        }
        return validMin;
    }

    /**
     * Function that checks the validity of the inventory value
     * @param min
     * @param max
     * @param stock
     * @return
     */
    private boolean inventoryValidationCheck(int min, int max, int stock) {
        boolean validInv = true;
        if (stock < min || stock > max) {
            validInv = false;
            displayAlert(4);
        }
        return validInv;
    }

    /**
     * Displays different alert messages to the user
     * @param alertType
     */
    private void displayAlert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Product Could Not Be Modified");
                alert.setContentText("Field contains blank fields or invalid values.");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Part Not Found");
                alert.setContentText("Please enter a valid part name or id!");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Min Value Not Valid");
                alert.setContentText("Min must be a number greater than 0 and less than Max.");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Error");
                alert.setHeaderText("Inventory Value Not Valid");
                alert.setContentText("Inventory value must be a number equal to or between Min and Max");
                alert.showAndWait();
                break;
            case 5:
                alert.setTitle("Error");
                alert.setHeaderText("No Part Selected");
                alert.showAndWait();
                break;
            case 6:
                alert.setTitle("Error");
                alert.setHeaderText("Name Not Valid");
                alert.setContentText("Name Text Field Cannot Be Empty.");
                alert.showAndWait();
                break;
        }
    }

    /**
     * Initializes this controller and adds data associated with selected part to appropriate text fields
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userSelectedProduct = MainScreenController.getSelectedProduct();
        aParts = userSelectedProduct.getAllAssociatedParts();

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTableView.setItems(Inventory.getAllParts());

        aPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        aPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        aPartInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        aPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        aPartTableView.setItems(aParts);

        productIdTextField.setText(String.valueOf(userSelectedProduct.getId()));
        productNameTextField.setText(userSelectedProduct.getName());
        productInvTextField.setText(String.valueOf(userSelectedProduct.getStock()));
        productPriceTextField.setText(String.valueOf(userSelectedProduct.getPrice()));
        productMaxTextField.setText(String.valueOf(userSelectedProduct.getMax()));
        productMinTextField.setText(String.valueOf(userSelectedProduct.getMin()));

    }
}
