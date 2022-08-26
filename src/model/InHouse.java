package model;

/**
 * Creates In-House part
 * @author Andre Simmons
 */
public class InHouse extends Part{

    /**
     * In-House part's machine id
     */
    private int machineId;

    /**
     * Construcor variable for in-house part
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId){
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * In-house part machine id get function
     * @return In-house part machine id
     */
    public int getMachineId(){return machineId;}

    /**
     * In-house part machine id set function
     * @param machineId
     */
    public void setMachineId(int machineId){this.machineId = machineId;}
}
