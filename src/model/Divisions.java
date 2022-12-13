package model;

/** This class stores all of the Divisions objects used in the app. */
public class Divisions {
    private int divisionId;
    private String division;
    private int countryId;

    /** This is the constructor for the divisions objects.
     * @param divisionId The division ID.
     * @param countryId The country ID.
     * @param division The division.
     * */
    public Divisions (int divisionId, String division, int countryId){
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }

    /** The getter for the division ID.
     * @return Returns the division ID.
     * */
    public int getDivisionId() {
        return divisionId;
    }

    /** The setter for the division ID.
     * @param divisionId The division ID.
     * */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /** Getter for the division.
     * @return Returns the division.
     * */
    public String getDivision() {
        return division;
    }

    /** Setter for the Division.
     * @param division The division.
     * */
    public void setDivision(String division) {
        this.division = division;
    }

    /** Getter for the country ID.
     * @return Returns the country ID.
     * */
    public int getCountryId() {
        return countryId;
    }

    /** Setter for the country ID.
     * @param countryId The country ID.
     * */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /** The method that returns the Division as a String to the user in the app.
     * @return Returns the division as a String. */
    @Override
    public String toString() {
        return(getDivision());
    }
}
