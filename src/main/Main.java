package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

/**
 * @author Andre Simmons
 *
 * This Inventory Management System is an application that helps to manage/modify
 * an inventory consisting of parts and products with associated parts
 *
 *FUTURE ENHANCEMENT: Being able to have control over which parts are duplicated in a product
 *
 *
 */

public class Main extends Application{

    /**
     * Creates FXML stage and loads the first scene
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setTitle("First Screen");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * This creates the sample data and launches the app
     * @param args
     */
    public static void main(String[] args){
        int partId = Inventory.getNewPartId();
        InHouse keyboard = new InHouse(partId, "Keyboard", 30.00, 10, 1, 25, 101);
        partId = Inventory.getNewPartId();
        InHouse mouse = new InHouse(partId, "Mouse", 25.00, 20, 1, 50, 101);
        partId = Inventory.getNewPartId();
        InHouse powerCord = new InHouse(partId,"Power Cord", 5.99, 35, 1, 65, 101);
        partId = Inventory.getNewPartId();
        Outsourced monitor = new Outsourced(partId, "Monitor",249.99, 40, 10, 100, "DELL");
        partId = Inventory.getNewPartId();
        Outsourced pc = new Outsourced(partId, "PC",899.99, 60, 30, 150, "DELL");
        Inventory.addPart(keyboard);
        Inventory.addPart(mouse);
        Inventory.addPart(powerCord);
        Inventory.addPart(monitor);
        Inventory.addPart(pc);

        int productId = Inventory.getNewProductId();
        Product desktop = new Product(productId, "DELL Desktop Computer", 1210.97, 10, 5, 50);
        desktop.addAssociatedPart(keyboard);
        desktop.addAssociatedPart(mouse);
        desktop.addAssociatedPart(monitor);
        desktop.addAssociatedPart(powerCord);
        desktop.addAssociatedPart(pc);
        Inventory.addProduct(desktop);

        launch(args);
    }
}
