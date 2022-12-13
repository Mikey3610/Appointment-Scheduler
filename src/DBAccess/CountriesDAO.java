package DBAccess;

import helper.JDBC;
import model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

/** This class has all of the SQL commands used by the app for accessing and making any changes to the database for countries. */
public abstract class CountriesDAO {
    public static ObservableList<Countries> getAllCountries(){
        ObservableList<Countries> clist = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from countries";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Countries C = new Countries(countryId, countryName);
                clist.add(C);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clist;
    }

    public static void checkDateConversion(){
        System.out.println("CREATE DATE TEST");
        String sql = "select Create_Date from countries";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Timestamp ts = rs.getTimestamp("Create_Date");
                System.out.println("CD: " + ts.toLocalDateTime().toString());
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    /** This method takes the data from separate tables in the MySQL database to retrieve a country's division ID.
     * @return Returns country object.
     * */
    public static Countries getCountryDivisionId(int divisionId) throws SQLException {
        String sql = "SELECT C.* FROM Countries AS C INNER JOIN First_Level_Divisions AS D ON C.COUNTRY_ID = D.COUNTRY_ID AND D.DIVISION_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int countryId = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");

            Countries countries = new Countries(countryId, countryName);
            return countries;
        }
        return null;
    }
}
