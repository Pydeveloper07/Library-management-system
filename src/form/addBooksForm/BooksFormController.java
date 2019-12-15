package form.addBooksForm;

import dataBase.DBConnector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BooksFormController implements Initializable {

    @FXML
    TextField isbn;
    @FXML
    Button getData;
    @FXML
    TextField title;
    @FXML
    TextArea description;
    @FXML
    ImageView photo;
    @FXML
    Button attach;
    @FXML
    Button ok;
    @FXML
    Button cancel;
    @FXML
    ComboBox<String> authorName = new ComboBox<>();
    @FXML
    Button addAuthorName;
    @FXML
    Button deleteAuthorName;
    private DBConnector connector = new DBConnector().getConnector();

    PreparedStatement preparedStatement;
    Connection connection;


    public BooksFormController() throws SQLException {
        connection= connector.getConnection();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        authorName.setPromptText("Author Name");
        authorName.setEditable(true);
    }

    @FXML
    private String saveData() {

        String st = "INSERT INTO students ( student_id, first_name, last_name, faculty, contact_number,email) VALUES (?,?,?,?,?,?)";
        String insertAuthor ="INSERT INTO authors (first_name,last_name)";
        String book_group ="INSERT INTO author (isbn, description, title,quantity)";
        //String insertAuthor ="INSERT INTO author (first_name,last_name)";
        //String insertAuthor ="INSERT INTO author (first_name,last_name)";
        //preparedStatement = (PreparedStatement) connection.prepareStatement(st);
        //preparedStatement.setString(1, id.getText());
        //preparedStatement.setString(2, name.getText());
        //preparedStatement.setString(3, surname.getText());
        //preparedStatement.setString(4, faculty.getText());
        //preparedStatement.setString(5, contact_num.getText());
        //preparedStatement.setString(6, email.getText());

        //preparedStatement.executeUpdate();

//            fetRowList();
        //clear fields
        clearFields();
        return "Success";

    }
    private void clearFields() {
        /*id.clear();
        name.clear();
        surname.clear();
        faculty.clear();
        contact_num.clear();
        email.clear();*/
    }

    public void handleAdd() {
        boolean exists = false;
        for (Object obj: authorName.getItems()){
            if(obj.toString() == authorName.getValue()) {
                exists = true;
                break;
            }
        }
        if(!exists){
            authorName.getItems().add(authorName.getValue());
            authorName.setValue("");
        }
    }

    public void handleDelete() {
        authorName.getItems().remove(authorName.getValue());
        authorName.setValue("");
    }

    public void cancelButtonHandler()
    {
        ((Stage)cancel.getScene().getWindow()).close();
    }

}
