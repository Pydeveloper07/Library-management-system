package form.addBooksForm;

import dataBase.DBConnector;
import getJSON.Send_HTTP_Request;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
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
    DatePicker publishedYear;
    @FXML
    Button deleteAuthorName;
    @FXML
    TextField subject;

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

        try {
            ImageIO.write(ImageIO.read(
                    new File(selectedFile.toString())),
                    "jpg",
                    new File("./src/images/bookPhoto/"+isbn.getText()+".jpg"));
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }


    @FXML
    private void saveData() throws SQLException {
        String insertBook_group ="INSERT INTO book_group (isbn, category, descrip, title, published_date) VALUES (?,?,?,?,?)";
        String insertAuthors ="INSERT INTO authors (author_name,isbn) VALUES (?,?)";
        String insertBook ="INSERT INTO book (isbn) VALUES (?)";
        preparedStatement = (PreparedStatement) connection.prepareStatement(insertAuthors);

        try{
            preparedStatement = (PreparedStatement) connection.prepareStatement(insertBook_group);
            preparedStatement.setString(1, isbn.getText());
            preparedStatement.setString(2, subject.getText());
            preparedStatement.setString(3, description.getText());
            preparedStatement.setString(4, title.getText());
            preparedStatement.setString(5, String.valueOf(publishedYear.getValue()));
            preparedStatement.executeUpdate();

            preparedStatement = (PreparedStatement) connection.prepareStatement(insertAuthors);
            String author = "";
            for (Object obj: authorName.getItems()){
                author +=authorName.getValue();
            }
            System.out.println(author);
            preparedStatement.setString(1,author);
            preparedStatement.setString(2, isbn.getText());
            preparedStatement.executeUpdate();

            preparedStatement = (PreparedStatement) connection.prepareStatement(insertBook);
            preparedStatement.setString(1, isbn.getText());
            preparedStatement.executeUpdate();


        }catch (Exception e) {
            System.out.println(e);
        }

        clearFields();
    }

    @FXML
    private void handleEventSave() throws SQLException {
        if (isbn.getText().isEmpty()||title.getText().isEmpty()||
                authorName.getItems().isEmpty()||
                description.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText("Please, fill all necessary fields!");
            alert.showAndWait();
        }
        else{
            saveDateOnce();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information!");
            alert.setHeaderText("Successfully inserted");
            alert.showAndWait();
        }
    }

    private void clearFields() {
        isbn.clear();
        title.clear();
        description.clear();
        authorName.getItems().removeAll();
        subject.clear();
        photo.setImage(new Image("images/demoBook.png"));
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
        sendHttpRequest.call_me(isbn.getText(), this);
    }
    public void setPhoto(String url) {
        System.out.println("Entered to setPhoto");
        Image image= new Image(url);
        photo.setImage(image);
        System.out.println("Set to Photo");
    }
    public void setTitle(String txt) {
        title.setText(txt);
    }
    public void setPublishedYear(String txt) {
        publishedYear.setValue(LocalDate.parse(txt));
    }
    public void setDescription(String txt) {
        description.setText(txt);
    }
    public void pressAdd(String txt) {
        boolean exists = false;
        for (Object obj: authorName.getItems()){
            if(obj.toString() == txt) {
                exists = true;
                break;
            }
        }
        if(!exists){
            authorName.getItems().add(txt);
            authorName.setValue("");
        }
    }
}
