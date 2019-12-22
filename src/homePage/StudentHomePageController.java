package homePage;

import dataBase.DBConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import table.ModelBookTable;

import java.sql.*;
import java.util.Random;

public class StudentHomePageController {
    @FXML
    private Label isbn1;
    @FXML
    private Label isbn2;
    @FXML
    private Label isbn3;
    @FXML
    private Label title1;
    @FXML
    private Label title2;
    @FXML
    private Label title3;
    @FXML
    private ImageView img1;
    @FXML
    private ImageView img2;
    @FXML
    private ImageView img3;
    @FXML
    private Label description1;
    @FXML
    private Label description2;
    @FXML
    private Label description3;
    @FXML
    private Label category1;
    @FXML
    private Label category2;
    @FXML
    private Label category3;
    @FXML
    private Label available1;
    @FXML
    private Label available2;
    @FXML
    private Label available3;


    private Random rand = new Random();

    private DBConnector connector = new DBConnector().getConnector();

    private Connection connection;

//    public void initialize() {
//
//
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(
//                    "SELECT book_group.isbn, category, title, descrip, avaiable FROM book INNER JOIN book_group ON book.isbn=book_group.isbn WHERE book_id=?");
//            Statement statement = connection.createStatement();
//            ResultSet resultSet1 = statement.executeQuery("SELECT * FROM book");
//            ResultSetMetaData metaData = resultSet1.getMetaData();
//            resultSet1.last();
//            int nob = resultSet1.getRow();
//            int value = rand.nextInt(nob);
//            connection = connector.getConnection();
//            preparedStatement.setInt(1, value);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                isbn1.setText(resultSet.getString("isbn"));
//                img1.setImage(new Image("images/userPhoto/" + isbn1.getText() + ".jpg"));
//                category1.setText(resultSet.getString("category"));
//                title1.setText(resultSet.getString("title"));
//                description1.setText(resultSet.getString("descrip"));
//                available1.setText(resultSet.getString("available"));
//            }
//
//            nob = resultSet1.getRow();
//            value = rand.nextInt(nob);
//            connection = connector.getConnection();
//            preparedStatement.setInt(1, value);
//            resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                isbn2.setText(resultSet.getString("isbn"));
//                img2.setImage(new Image("images/userPhoto/" + isbn2.getText() + ".jpg"));
//                category2.setText(resultSet.getString("category"));
//                title2.setText(resultSet.getString("title"));
//                description2.setText(resultSet.getString("descrip"));
//                available2.setText(resultSet.getString("available"));
//            }
//
//            nob = resultSet1.getRow();
//            value = rand.nextInt(nob);
//            connection = connector.getConnection();
//            preparedStatement.setInt(1, value);
//            resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                isbn3.setText(resultSet.getString("isbn"));
//                img3.setImage(new Image("images/userPhoto/" + isbn3.getText() + ".jpg"));
//                category3.setText(resultSet.getString("category"));
//                title3.setText(resultSet.getString("title"));
//                description3.setText(resultSet.getString("descrip"));
//                available3.setText(resultSet.getString("available"));
//            }
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }


}
