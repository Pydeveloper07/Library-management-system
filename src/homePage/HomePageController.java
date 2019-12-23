package homePage;

import dataBase.DBConnector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    @FXML
    Text totalBooks;
    @FXML
    Text totalIssued;
    @FXML
    Text totalLost;

    private DBConnector connector = new DBConnector().getConnector();
    PreparedStatement preparedStatement;
    Connection connection;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        totalBooks.setText(""+getNumberOfBooks());
        totalIssued.setText(""+getNumberOfordered());
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

    private int getNumberOfordered(){
        int numberOfLostBooks = 2;
        try{
            connection = connector.getConnection();
            PreparedStatement p = connection.prepareStatement("SELECT * FROM ordered_books");
            ResultSet resultSet = p.executeQuery();
            resultSet.last();
            numberOfLostBooks = resultSet.getRow();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return numberOfLostBooks;
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
