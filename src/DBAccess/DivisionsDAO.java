package DBAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;
import model.Divisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionsDAO {
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
}
