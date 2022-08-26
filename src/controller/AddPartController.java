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

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controls the Add Part UI
 * @author Andre Simmons
 */
public class AddPartController implements Initializable {

    /**
     * Text field for part id
     */
    @FXML
    private TextField partidtextfield;

    /**
     * Text field for part name
     */
    @FXML
    private TextField partnametextfield;

    /**
     * Text field for part inventory
     */
    @FXML
    private TextField partinventorytextfield;

    /**
     * Text field for part price
     */
    @FXML
    private TextField partpricetextfield;

    /**
     * Text field for part maximum inventory value
     */
    @FXML
    private TextField partmaxtextfield;

    /**
     * Text field for part machine id/company name
     */
    @FXML
    private TextField idornametextfield;

    /**
     * Text field for part minimum inventory value
     */
    @FXML
    private TextField partmintextfield;

    /**
     * Label for part machine id/company name
     */
    @FXML
    private Label radioLabelSwitch;

    /**
     * Toggle group for the radio buttons
     */
    @FXML
    private ToggleGroup type;

    /**
     * In-House radio button
     */
    @FXML
    private RadioButton inhouseradio;

    /**
     * Outsourced radio button
     */
    @FXML
    private RadioButton outsourcedradio;

    /**
     * Controls the save button and adds part to inventory
     * Returns to the main screen after clicking on the button
     * Displays error messages if text fields are invalid
     * @param actionEvent
     */
    public void onClickSave(ActionEvent actionEvent) {
        try {
            int id = 0;
            String name = partnametextfield.getText();
            Double price = Double.parseDouble(partpricetextfield.getText());
            int stock = Integer.parseInt(partinventorytextfield.getText());
            int min = Integer.parseInt(partmintextfield.getText());
            int max = Integer.parseInt(partmaxtextfield.getText());
            int machineId;
            String companyName;
            boolean partAddSuccessful = false;

            if (name.isEmpty()) {
                displayAlert(5);
            } else {
                if (minValidationCheck(min, max) && inventoryValidationCheck(min, max, stock)) {

                    if (inhouseradio.isSelected()) {
                        try {
                            machineId = Integer.parseInt(idornametextfield.getText());
                            InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                            newInHousePart.setId(Inventory.getNewPartId());
                            Inventory.addPart(newInHousePart);
                            partAddSuccessful = true;
                        } catch (Exception e) {
                            displayAlert(2);
                        }
                    }

                    if (outsourcedradio.isSelected()) {
                        companyName = idornametextfield.getText();
                        Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max,
                                companyName);
                        newOutsourcedPart.setId(Inventory.getNewPartId());
                        Inventory.addPart(newOutsourcedPart);
                        partAddSuccessful = true;
                    }

                    if (partAddSuccessful) {
                        mainScreen(actionEvent);
                    }
                }
            }
        } catch(Exception e) {
            displayAlert(1);
        }
    }

    /**
     * Function that controls error messages
     * @param alertType
     */
    private void displayAlert(int alertType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Part Not Added!");
                alert.setContentText("This form contains blank fields/missing values.");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Machine ID Invalid!");
                alert.setContentText("Machine ID may only contain numbers.");
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
                alert.setHeaderText("Invalid Name");
                alert.setContentText("Name field cannot be empty. Please enter a valid name.");
                alert.showAndWait();
                break;
        }
    }

    /**
     * Controls the cancel button and returns to the main screen when clicked
     * Also displays a confirmation message which must be accepted before returning to the main screen
     * @param actionEvent
     * @throws IOException
     */
    public void onClickCancel(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure that you want to void these changes and return to the main screen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            mainScreen(actionEvent);
        }

    }

    /**
     * Controls the In-House radio button
     * The Label is set to "Machine ID" when clicked
     * @param actionEvent
     */
    public void onClickInHouse(ActionEvent actionEvent) {
        radioLabelSwitch.setText("Machine ID");
    }

    /**
     * Controls the Outsourced radio button
     * The Label is set to "Company Name" when clicked
     * @param actionEvent
     */
    public void onClickOutsourced(ActionEvent actionEvent) {
        radioLabelSwitch.setText("Company Name");
    }

    /**
     * Function to validate inventory value
     * Value must be greater than/equal to the min and less than/equal to the max
     * Displays an error message if value is invalid
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
     * Function to validate minimum value
     * Value must be greater than/equal to the 0 and less than to the max
     * Displays an error message if value is invalid
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
     * Function to return to the Main Screen
     * @param actionEvent
     * @throws IOException
     */
    private void mainScreen(ActionEvent actionEvent) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This initializes the controller and sets In-House radio button to be selected on launch
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inhouseradio.setSelected(true);
    }
}
