package form.addBooksForm;

import dataBase.DBConnector;
import getJSON.Send_HTTP_Request;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
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
    Button cancel;
    @FXML
    Spinner<Integer> spinner = new Spinner<Integer>();
    @FXML
    ComboBox<String> authorName = new ComboBox<>();
    @FXML
    Button addAuthorName;
    @FXML
    Button deleteAuthorName;

    private Send_HTTP_Request sendHttpRequest = new Send_HTTP_Request();
    private DBConnector connector = new DBConnector().getConnector();

    PreparedStatement preparedStatement;
    Connection connection;


    public BooksFormController() throws SQLException {
        connection = connector.getConnection();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Value factory.
        initSpinner();
        authorName.setPromptText("Author Name");
        authorName.setEditable(true);
    }
    @FXML
    private void initSpinner() {
       spinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100,1));
        }

    private void saveDateOnce() throws SQLException {
        for (int i=0;i<spinner.getValue();i++){
            saveData();
        }
    }

    @FXML
    private void setAttach(){

        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Jpeg files","*.jpg") );
        File selectedFile= fileChooser.showOpenDialog(null);

        //create and set image
        Image image= new Image(selectedFile.toURI().toString());
        photo.setImage(image);


    }


    @FXML
    private String saveData() throws SQLException {

        String insertAuthors ="INSERT INTO authors (first_name,last_name) VALUES (?,?)";
        String insertBook_group ="INSERT INTO book_group (isbn, descrip, title) VALUES (?,?,?)";
        //String insertAuthor_books ="INSERT INTO author_books (isbn) VALUES (?)";
        String insertBook ="INSERT INTO book (isbn) VALUES (?)";
        preparedStatement = (PreparedStatement) connection.prepareStatement(insertAuthors);
        for (Object obj: authorName.getItems()){
            String[] arrOfStr = obj.toString().split(" ", 2);
            if(arrOfStr.length==1){
            preparedStatement.setString(1,arrOfStr[0]);
            preparedStatement.setString(2,"");
            }else{
                preparedStatement.setString(1,arrOfStr[0]);
                preparedStatement.setString(2,arrOfStr[1]);
            }
        }
        try{
        int a=Integer.parseInt(isbn.getText());



            preparedStatement = (PreparedStatement) connection.prepareStatement(insertBook_group);
            preparedStatement.setInt(1, a);
            preparedStatement.setString(2, description.getText());
            preparedStatement.setString(3, title.getText());

            //preparedStatement = (PreparedStatement) connection.prepareStatement(insertAuthor_books);
            //preparedStatement.setString(1, isbn.getText());

            preparedStatement = (PreparedStatement) connection.prepareStatement(insertBook);
            preparedStatement.setInt(1, a);
        }catch (Exception e) {}
        preparedStatement.executeUpdate();

//            fetRowList();
        //clear fields
        clearFields();
        return "Success";

    }
    private void clearFields() {
        isbn.clear();
        title.clear();
        description.clear();
        authorName.getItems().removeAll();
        //photo.setImage(new Image("index.png"));
        //spinner email.clear();
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


    public void getData() throws Exception {
        sendHttpRequest.call_me(isbn.getText());
    }
}
