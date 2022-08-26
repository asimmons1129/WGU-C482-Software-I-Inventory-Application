package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Creates an inventory of products and parts
 *
 * @author Andre Simmons
 */
public class Inventory {

    /**
     * Part ID
     */
    private static int partId = 0;

    /**
     * Product Id
     */
    private static int productId = 0;

    /**
     * A list of all parts in the inventory
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * A list of all products in the inventory
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * all parts get function
     * @return all inventory parts
     */
    public static ObservableList<Part> getAllParts(){return allParts;}

    /**
     * all products get function
     * @return all inventory products
     */
    public static ObservableList<Product> getAllProducts(){return allProducts;}

    /**
     * function to add part to inventory
     * @param newPart
     */
    public static void addPart(Part newPart){allParts.add(newPart);}

    /**
     * function to add product to inventory
     * @param newProduct
     */
    public static void addProduct(Product newProduct){allProducts.add(newProduct);}

    /**
     * new part get function
     * @return new part
     */
    public static int getNewPartId(){return ++partId;}

    /**
     * new product get function
     * @return new product
     */
    public static int getNewProductId(){return ++productId;}

    /**
     * function to search for a part
     * @param partId
     * @return found part
     */
    public static Part lookupPart(int partId){
        Part partFound = null;
        for(Part part : allParts){
            if(part.getId() == partId){
                partFound = part;
            }
        }
        return partFound;
    }

    /**
     * function to search the list of parts by name
     * @param partName
     * @return found part
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        for(Part part : allParts){
            if(part.getName().equals(partName)){
                partsFound.add(part);
            }
        }
        return partsFound;
    }

    /**
     * function to search the list of products by id
     * @param productId
     * @return found product
     */
    public static Product lookupProduct(int productId){
        Product productFound = null;
        for(Product product: allProducts){
            if(product.getId() == productId) {
                productFound = product;
            }
        }
        return productFound;
    }

    /**
     * function to search list of products by name
     * @param productName
     * @return found product
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> productsFound = FXCollections.observableArrayList();
        for(Product product : allProducts){
            if(product.getName().equals(productName)){
                productsFound.add(product);
            }
        }
        return productsFound;
    }

    /**
     * function to update/replace part in the all parts list
     * @param index
     * @param selectedPart
     */
    public static void updatePart (int index, Part selectedPart){allParts.set(index, selectedPart);}

    /**
     * function to update/replace product in the all products list
     * @param index
     * @param selectedProduct
     */
    public static void updateProduct(int index, Product selectedProduct){allProducts.set(index, selectedProduct);}

    /**
     * function to delete part
     * @param selectedPart
     * @return boolean result of part removal
     */
    public static boolean deletePart(Part selectedPart){
        if(allParts.contains(selectedPart)){
            allParts.remove(selectedPart);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * function to delete a product
     * @param selectedProduct
     * @return boolean result of product removal
     */
    public static boolean deleteProduct(Product selectedProduct){
        if(allProducts.contains(selectedProduct)){
            allProducts.remove(selectedProduct);
            return true;
        }
        else{
            return false;
        }
    }
}
