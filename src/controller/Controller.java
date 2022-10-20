package controller;

import DBAccess.CountriesDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Countries;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public TableColumn idCol;
    public TableColumn nameCol;
    public TableView dataTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
        public void showMe(ActionEvent actionEvent){
            ObservableList<Countries> countrylist = CountriesDAO.getAllCountries();
            for(Countries C : countrylist){
                System.out.println("Country Id : " + C.getCountryId() + " Name : " + C.getCountryName());
            }
        }
    }
