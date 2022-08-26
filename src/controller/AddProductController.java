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
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *Controls the Add Product Scene
 * @author Andre Simmons
 */
public class AddProductController implements Initializable {

    /**
     * Table view for associated parts
     */
    @FXML
    private TableView<Part> aTableView;

    /**
     * Column for associated part id
     */
    @FXML
    private TableColumn<Part, Integer> aPartIdColumn;

    /**
     * Column for associated part name
     */
    @FXML
    private TableColumn<Part, String> aPartNameColumn;

    /**
     * Column for associated part inventory value
     */
    @FXML
    private TableColumn<Part, Integer> aInvLevelColumn;

    /**
     * Column for associated part price
     */
    @FXML
    private TableColumn<Part, Double> aPriceColumn;

    /**
     * Table view for all parts
     */
    @FXML
    private TableView<Part> tableView;

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
    private TableColumn<Part, Integer> invLevelColumn;

    /**
     * Column for part price
     */
    @FXML
    private TableColumn<Part, Double> priceColumn;

    /**
     * Text field to search for a part
     */
    @FXML
    private TextField partSearchTextField;

    /**
     * Text field for product id
     */
    @FXML
    private TextField productIdTextField;

    /**
     * Text field for product name
     */
    @FXML
    private TextField productNameTextField;

    /**
     * Text field for product inventory value
     */
    @FXML
    private TextField productInventoryTextField;

    /**
     * Text field for product price
     */
    @FXML
    private TextField productPriceTexField;

    /**
     * Text field for product maximum inventory value
     */
    @FXML
    private TextField productMaxTextField;

    /**
     * Text field for product minimum inventory value
     */
    @FXML
    private TextField productMinTextField;

    /**
     * List containing associated parts of a product
     */
    private ObservableList<Part> aParts = FXCollections.observableArrayList();


    /**
     * Function to display alert messages to the user
     * @param alertType
     */
    private void displayAlert(int alertType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Product Not Added!");
                alert.setContentText("This form contains blank fields/missing values.");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Information");
                alert.setHeaderText("Part Not Found");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Min Value Invalid");
                alert.setContentText("Min must be a number greater than 0 and less than Max.");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Error");
                alert.setHeaderText("Inventory Value Invalid");
                alert.setContentText("Inventory must be a number equal to or between Min and Max.");
                alert.showAndWait();
                break;
            case 5:
                alert.setTitle("Error");
                alert.setHeaderText("No Part Selected");
                alert.showAndWait();
                break;
            case 6:
                alert.setTitle("Error");
                alert.setHeaderText("Name Invalid");
                alert.setContentText("Name Text Field is Empty");
                alert.showAndWait();
                break;
        }
    }

    /**
     * Controls the add button
     * Adds the selected part from the all parts table to the associated parts table
     * Displays an alert message if no part is selected
     * @param actionEvent
     */
    public void onClickAdd(ActionEvent actionEvent) {
        Part selectedPart = tableView.getSelectionModel().getSelectedItem();
        if(selectedPart == null){
            displayAlert(5);
        }
        else{
            aParts.add(selectedPart);
            aTableView.setItems(aParts);
        }
    }

    /**
     * Controls the remove button
     * Removes part from associated parts table
     * Displays a confirmation message before removing the part
     * @param actionEvent
     */
    public void onClickRemove(ActionEvent actionEvent) {
        Part selectedPart = aTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(5);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure that you want to remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                aParts.remove(selectedPart);
                aTableView.setItems(aParts);
            }
        }
    }

    /**
     * Controls the save button
     * Adds new product to the inventory and launches the main screen
     * Alert messages are displayed if text field is invalid
     * @param actionEvent
     */
    public void onClickSave(ActionEvent actionEvent) {
        try {
            int id = 0;
            String name = productNameTextField.getText();
            Double price = Double.parseDouble(productPriceTexField.getText());
            int stock = Integer.parseInt(productInventoryTextField.getText());
            int min = Integer.parseInt(productMinTextField.getText());
            int max = Integer.parseInt(productMaxTextField.getText());

            if (name.isEmpty()) {
                displayAlert(6);
            }
            else {
                if (minValidationCheck(min, max) && inventoryValidationCheck(min, max, stock)) {
                    Product newProduct = new Product(id, name, price, stock, min, max);
                    for (Part part : aParts) {
                        newProduct.addAssociatedPart(part);
                    }
                    newProduct.setId(Inventory.getNewProductId());
                    Inventory.addProduct(newProduct);
                    mainScreen(actionEvent);
                }
            }
        } catch (Exception e){
            displayAlert(1);
        }
    }

    /**
     * Controls the cancel button
     * Displays a confirmation message and launches main screen if confirmed
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
     * Controls search button
     * Searches for a part by id or name
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

        tableView.setItems(partsFound);

        if (partsFound.size() == 0) {
            displayAlert(1);
        }
    }

    /**
     * Function to return the user to the main screen
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
     * Funcion that checks if the minimum inventory value is valid
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
     * Function that checks if inventory value is valid
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
     * Initializes this controller and populates the data into the tables
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        invLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableView.setItems(Inventory.getAllParts());

        aPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        aPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        aInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        aPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
