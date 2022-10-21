package model;

public class Countries {
    private int countryId;
    private String countryName;

    public Countries(int countryId, String countryName){
        this.countryId = countryId;
        this.countryName = countryName;
    }
    public int getCountryId(){return countryId;}

    public String getCountryName(){return countryName;}

    @Override
    public String toString() {
        return(getCountryName());
    }
}
