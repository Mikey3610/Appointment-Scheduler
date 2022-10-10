package model;

public class Countries {
    private int countryId;
    private String countryName;

    public Countries(int id, String name){
        this.countryId = id;
        this.countryName = name;
    }
    public int getId(){return countryId;}

    public String getName(){return countryName;}
}
