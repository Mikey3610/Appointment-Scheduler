package model;

/** This class stores all of the countries objects used in the app. */
public class Countries {
    private int countryId;
    private String countryName;

    /** The constructor of the countries stored in the database.
     * @param countryId The country ID.
     * @param countryName The country name.
     * */
    public Countries(int countryId, String countryName){
        this.countryId = countryId;
        this.countryName = countryName;
    }

    /** Getter for the country ID.
     * @return Returns the country ID.
     * */
    public int getCountryId(){return countryId;}

    /** Getter for the country name.
     * @return Returns the country's name. */
    public String getCountryName(){return countryName;}

    /** This method returns the country's name as a string to be used in the app.
     * @return Returns the country name as a String.
     * */
    @Override
    public String toString() {
        return(getCountryName());
    }
}
