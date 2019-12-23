package form.issuedDateBook;

import dataBase.DBConnector;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import table.BookTableController;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class IssuedFormController
{
    private static String book_title;
    private static String isbn;
    @FXML
    private TextField studentID;
    @FXML
    private TextField bookTitle;
    @FXML
    private DatePicker issuedDate;
    @FXML
    private DatePicker dueDate;

    private DBConnector connector = new DBConnector().getConnector();

    Connection connection;

    public void initialize() {
        bookTitle.setText(book_title);
        studentID.setPromptText("Student ID");
    }
    public void setBook_title(String title, String is){
        book_title = title;
        isbn = is;
    }
    @FXML
    public void okButtonHandler(){
        if (studentID.getText().isEmpty() || bookTitle.getText().isEmpty() ||
                String.valueOf(issuedDate.getValue()).isEmpty() ||
                String.valueOf(dueDate.getValue()).isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText("Please, fill all necessary fields!");
            alert.showAndWait();
        }
        else{
            try{
                connection = connector.getConnection();
                PreparedStatement p = connection.prepareStatement("SELECT available FROM book_group WHERE isbn=?");
                p.setString(1, isbn);
                ResultSet r = p.executeQuery();
                r.next();
                if(r.getInt("available") == 0){
                    reserve();
                }
                else{
                    r.close();
                    p.close();
                    connection.close();
                    issueBook();
                }
            }
            catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
    private void reserve(){
        try{
            connection = connector.getConnection();
            PreparedStatement p = connection.prepareStatement(
                    "INSERT INTO waiting_list(student_id, isbn, reservation_date) VALUES(?,?,?)");
            p.setString(1, studentID.getText());
            p.setString(2, isbn);
            p.setString(3, String.valueOf(issuedDate.getValue()));
            p.executeUpdate();
            p.close();
            connection.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information!");
            alert.setHeaderText("Because there is that kind of available book you have been put into Reservation List!");
            alert.showAndWait();
            ((Stage)studentID.getScene().getWindow()).close();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    private void issueBook(){
        try{
            connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ordered_books VALUES(?,?,?,?)");
            preparedStatement.setString(1, isbn);
            preparedStatement.setString(2, studentID.getText());
            preparedStatement.setString(3, String.valueOf(issuedDate.getValue()));
            preparedStatement.setString(4, String.valueOf(dueDate.getValue()));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information!");
        alert.setHeaderText("Successfully Issued!");
        alert.showAndWait();
        ((Stage)studentID.getScene().getWindow()).close();
    }

    public void cancelButtonHandler()
    {
        ((Stage)studentID.getScene().getWindow()).close();
    }
}
