package DBAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;
import model.Divisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class has all of the SQL commands used by the app for accessing and making any changes to the database for divisions (region). */
public abstract class DivisionsDAO {
    /** This method selects and displays the division from the corresponding country selected by the user from the database.
     * @param countryId The country ID.
     * @return Returns the division to the corresponding country.
     * */
    public static ObservableList<Divisions> getDivisionByCountry(int countryId){
        ObservableList<Divisions> divisions = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from first_level_divisions WHERE Country_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, countryId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int divisionId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                Divisions D = new Divisions(divisionId, division, countryId);
                divisions.add(D);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return divisions;
    }

    /** This method retrieves all of the divisions in the database.
     * @return Returns all of the divisions.
     * */
    public static ObservableList<Divisions> getAllDivisions(){
        ObservableList<Divisions> divisions = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from first_level_divisions";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int divisionId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                Divisions D = new Divisions(divisionId, division, countryId);
                divisions.add(D);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return divisions;
    }

    /** This method selects the division by the selected Division ID.
     * @param divisionId The division ID.
     * @return Returns the Division.
     * */
    public static Divisions getDivision(int divisionId){

        try{
            String sql = "SELECT * from first_level_divisions WHERE Division_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, divisionId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int countryId = rs.getInt("Country_ID");
                String division = rs.getString("Division");
                Divisions D = new Divisions(divisionId, division, countryId);
                return D;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
