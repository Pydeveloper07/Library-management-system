package form.chargeStudentForm;

import dataBase.DBConnector;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Controller {
    private static String user_id;
    @FXML
    private TextField student_id;
    @FXML
    private TextField book_id;
    @FXML
    private DatePicker due_date;
    @FXML
    private TextField chargeAmount;

    private DBConnector connector = new DBConnector().getConnector();
    public static void setUserId(String userId){
        user_id = userId;
    }
    public void initialize(){
        try{
            Connection connection = connector.getConnection();
            PreparedStatement p1 = connection.prepareStatement("SELECT student_id FROM  students WHERE student_id=?");
            p1.setString(1, user_id);
            ResultSet r = p1.executeQuery();
            if(r.next()){
                student_id.setText(r.getString("student_id"));
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleEventSave(){
        if (student_id.getText().isEmpty()||book_id.getText().isEmpty()||
                chargeAmount.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText("Please, fill all necessary fields!");
            alert.showAndWait();
        }
        else{
            saveData();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information!");
            alert.setHeaderText("Successfully inserted");
            alert.showAndWait();
            cancelButtonHandler();
        }
    }

    @FXML
    public void saveData(){
        try{
            Connection connection = connector.getConnection();
            PreparedStatement p1 = connection.prepareStatement("INSERT INTO fined_students VALUES(?,?,?,?)");
            p1.setString(1, user_id);
            p1.setInt(2, Integer.parseInt(book_id.getText()));
            p1.setString(3, String.valueOf(due_date.getValue()));
            p1.setInt(4, Integer.parseInt(chargeAmount.getText()));
            p1.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    @FXML
    public void cancelButtonHandler(){
        ((Stage)student_id.getScene().getWindow()).close();
    }
}
