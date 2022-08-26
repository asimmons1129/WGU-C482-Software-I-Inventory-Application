package model;

/**
 *Creates individual parts
 * Based on UML Provided by WGU
 * @author Andre Simmons
 */

public class Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor variable for each part
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */

    public Part(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * part id get function
     * @return part id
     */
    public int getId(){return id;}

    /**
     * part id set function
     * @param id
     */
    public void setId(int id){this.id = id;}

    /**
     * part name get function
     * @return part name
     */
    public String getName(){return name;}

    /**
     * part name set function
     * @param name
     */
    public void setName(String name){this.name = name;}

    /**
     * part price get function
     * @return part price
     */
    public double getPrice(){return price;}

    /**
     * part price set function
     * @param price
     */
    public void setPrice(double price){this.price = price;}

    /**
     * part inventory value get function
     * @return part inventory value
     */
    public int getStock(){return stock;}

    /**
     * part inventory set function
     * @param stock
     */
    public void setStock(int stock){this.stock = stock;}

    /**
     * part minimum inventory value get function
     * @return part minimum inventory value
     */
    public int getMin(){return min;}

    /**
     * part minimum inventory value set function
     * @param min
     */
    public void setMin(int min){this.min = min;}

    /**
     * part maximum inventory value get function
     * @return part minimum inventory value
     */
    public int getMax(){return max;}

    /**
     * part maximum inventory value set function
     * @param max
     */
    public void setMax(int max){this.max = max;}



}
