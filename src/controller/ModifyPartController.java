package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controls the Modify Part Screen
 * @author Andre Simmons
 */
public class ModifyPartController implements Initializable {

    /**
     * Label for machine id/company name
     */
    @FXML
    private Label idOrNameLabel;

    /**
     * Text field for part minimum inventory value
     */
    @FXML
    private TextField partMinTextField;

    /**
     * Text field for machine id/company name
     */
    @FXML
    private TextField idOrNameTextField;

    /**
     * Text field for part maximum inventory value
     */
    @FXML
    private TextField partMaxTextField;

    /**
     * Text field for part price
     */
    @FXML
    private TextField partPriceTextField;

    /**
     * Text field for part inventory value
     */
    @FXML
    private TextField partInventoryTextField;

    /**
     * Text field for part name
     */
    @FXML
    private TextField partNameTextField;

    /**
     * Text field for part id
     */
    @FXML
    private TextField partIdTextField;

    /**
     * Outsourced radio button
     */
    @FXML
    private RadioButton outsourcedRadio;

    /**
     * Toggle group for radio buttons
     */
    @FXML
    private ToggleGroup type;

    /**
     * In-House radio button
     */
    @FXML
    private RadioButton inHouseRadio;

    /**
     * Part that user selected
     */
    private Part userSelectedPart;

    /**
     * Controls the save button
     * Saves and replaces updated part in the inventory
     * Launches the main screen
     * Checks validation of all text fields through error messages
     * @param actionEvent
     */
    public void onClickSave(ActionEvent actionEvent) {
        try {
            int id = userSelectedPart.getId();
            String name = partNameTextField.getText();
            Double price = Double.parseDouble(partPriceTextField.getText());
            int stock = Integer.parseInt(partInventoryTextField.getText());
            int min = Integer.parseInt(partMinTextField.getText());
            int max = Integer.parseInt(partMaxTextField.getText());
            int machineId;
            String companyName;
            boolean partAdded = false;

            if (minValidationCheck(min, max) && inventoryValid(min, max, stock)) {
                if (inHouseRadio.isSelected()) {
                    try {
                        machineId = Integer.parseInt(idOrNameTextField.getText());
                        InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                        Inventory.addPart(newInHousePart);
                        partAdded = true;
                    } catch (Exception e) {
                        displayAlert(2);
                    }
                }
                if (outsourcedRadio.isSelected()) {
                    companyName = idOrNameTextField.getText();
                    Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max,
                            companyName);
                    Inventory.addPart(newOutsourcedPart);
                    partAdded = true;
                }
                if (partAdded) {
                    Inventory.deletePart(userSelectedPart);
                    mainScreen(actionEvent);
                }
            }
        } catch(Exception e) {
            displayAlert(1);
        }
    }

    /**
     * Controls the cancel button
     * Cancels any unsaved changes to the part
     * Displays a confirmation message before returning the user to the main screen
     * @param actionEvent
     * @throws IOException
     */
    public void onClickCancel(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure that you want cancel these changes and return to the main screen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            mainScreen(actionEvent);
        }
    }

    /**
     * Controls the Outsourced radio button
     * Label is set to "Company Name" when selected
     * @param actionEvent
     */
    public void onClickOutsourced(ActionEvent actionEvent) {
        idOrNameLabel.setText("Company Name");
    }

    /**
     * Controls the In-House radio button
     * Label is set to "Machine ID" when selected
     * @param actionEvent
     */
    public void onClickInHouse(ActionEvent actionEvent) {
        idOrNameLabel.setText("Machine ID");
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
    private boolean inventoryValid(int min, int max, int stock) {
        boolean validInv = true;
        if (stock < min || stock > max) {
            validInv = false;
            displayAlert(4);
        }
        return validInv;
    }

    /**
     * Function that displays different alert messages to the user
     * @param alertType
     */
    private void displayAlert(int alertType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Part Couldn't Be Modified");
                alert.setContentText("Field contains blank fields or invalid values.");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Machine ID Not Valid");
                alert.setContentText("The Machine ID may only contain numbers.");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Min Value Not Valid");
                alert.setContentText("The Min valuemust be a number greater than 0 and less than Max.");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Error");
                alert.setHeaderText("Inventory Value Not Valid");
                alert.setContentText("Inventory must be a number equal to or between Min and Max");
                alert.showAndWait();
                break;
        }
    }

    /**
     * Initializes this controller and displays all information about selected part in the appropriate text fields
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userSelectedPart = MainScreenController.getSelectedPart();

        if (userSelectedPart instanceof InHouse) {
            inHouseRadio.setSelected(true);
            idOrNameLabel.setText("Machine ID");
            idOrNameTextField.setText(String.valueOf(((InHouse) userSelectedPart).getMachineId()));
        }

        if (userSelectedPart instanceof Outsourced){
            outsourcedRadio.setSelected(true);
            idOrNameLabel.setText("Company Name");
            idOrNameTextField.setText(((Outsourced) userSelectedPart).getCompanyName());
        }

        partIdTextField.setText(String.valueOf(userSelectedPart.getId()));
        partNameTextField.setText(userSelectedPart.getName());
        partInventoryTextField.setText(String.valueOf(userSelectedPart.getStock()));
        partPriceTextField.setText(String.valueOf(userSelectedPart.getPrice()));
        partMaxTextField.setText(String.valueOf(userSelectedPart.getMax()));
        partMinTextField.setText(String.valueOf(userSelectedPart.getMin()));

    }
}
