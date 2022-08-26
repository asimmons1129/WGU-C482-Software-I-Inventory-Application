package model;

/**
 * Creates outsourced part
 *
 * @author Andre Simmons
 */
public class Outsourced extends Part{

    /**
     * Outsourced part's company name
     */
    private String companyName;

    /**
     * Constructor variable for outsourced part
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Outsourced part company name get function
     * @return Outsourced part company name
     */
    public String getCompanyName(){return companyName;}

    /**
     * Outsourced part company name set function
     * @param companyName
     */
    public void setCompanyName(String companyName){this.companyName = companyName;}
}
