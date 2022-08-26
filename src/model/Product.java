package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Creates a product that contains associated parts
 *
 * @author Andre Simmons
 */

public class Product {
    /**
     * Product's ID
     */
    private int id;
    /**
     * Product's name
     */
    private String name;
    /**
     * Product's price
     */
    private double price;
    /**
     * Product's inventory value
     */
    private int stock;
    /**
     * Product's minimum inventory value
     */
    private int min;
    /**
     * Product's maximum inventory value
     */
    private int max;

    /**
     * List of associated parts for the product
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Product Constructor variable
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */

    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * id get function
     * @return product id
     */
    public int getId(){return id;}

    /**
     * id set function
     * @param id
     */
    public void setId(int id){this.id = id;}

    /**
     * name get function
     * @return product name
     */
    public String getName(){return name;}

    /**
     * name set function
     * @param name
     */
    public void setName(String name){this.name = name;}

    /**
     * price get function
     * @return product price
     */
    public double getPrice(){return price;}

    /**
     * price set function
     * @param price
     */
    public void setPrice(double price){this.price = price;}

    /**
     * stock get function
     * @return product stock/inventory value
     */
    public int getStock(){return stock;}

    /**
     * stock set function
     * @param stock
     */
    public void setStock(int stock){this.stock = stock;}

    /**
     * minimum inventory value get function
     * @return product minimum inventory value
     */
    public int getMin(){return min;}

    /**
     * minimum inventory value set function
     * @param min
     */
    public void setMin(int min){this.min = min;}

    /**
     * maximum inventory value get function
     * @return product maximum  inventory value
     */
    public int getMax(){return max;}

    /**
     * maximum inventory value set function
     * @param max
     */
    public void setMax(int max){this.max = max;}

    /**
     * This function adds a part to the associated parts list
     * @param part
     */
    public void addAssociatedPart(Part part){associatedParts.add(part);}

    /**
     * This function deletes a part from the associated parts list
     * @param selectedAssociatedPart
     * @return
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if(associatedParts.contains(selectedAssociatedPart)){
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else
            return false;
    }

    /**
     * This function gets the list of all associated parts
     * @return associated parts list
     */
    public ObservableList<Part> getAllAssociatedParts(){return associatedParts;}
}
