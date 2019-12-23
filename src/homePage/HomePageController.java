package homePage;

import dataBase.DBConnector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    @FXML
    Label totalBooks;
    @FXML
    Label totalIssued;
    @FXML
    Label totalLost;

    private DBConnector connector = new DBConnector().getConnector();
    PreparedStatement preparedStatement;
    Connection connection;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        totalBooks.setText("asd"+getNumberOfBooks());
        totalBooks.setText(""+getNumberOfBooks());
        totalLost.setText(""+getNumberOfLostBooks());
    }

    private int getNumberOfBooks(){
        int numberOfBooks = 0;
        try{
            numberOfBooks = 0;
            connection = connector.getConnection();
            PreparedStatement p = connection.prepareStatement("SELECT quantity FROM book_group");
            ResultSet resultSet = p.executeQuery();
            while(resultSet.next()){
                numberOfBooks += resultSet.getInt("quantity");
            }

        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return numberOfBooks;
    }
    private int getNumberOfLostBooks(){
        int numberOfLostBooks = 0;
        try{
            connection = connector.getConnection();
            PreparedStatement p = connection.prepareStatement("SELECT * FROM lost_books");
            ResultSet resultSet = p.executeQuery();
            resultSet.last();
            numberOfLostBooks = resultSet.getRow();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return numberOfLostBooks;
    }
}
