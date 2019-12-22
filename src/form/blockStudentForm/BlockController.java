package form.blockStudentForm;

import dataBase.DBConnector;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BlockController {
    private static String userId;
    private DBConnector connector = new DBConnector().getConnector();
    @FXML
    private TextField student_id;
    @FXML
    private TextArea block_reason;
    public void initialize(){
        try{
            Connection connection = connector.getConnection();
            PreparedStatement p1 = connection.prepareStatement("SELECT student_id FROM  students WHERE student_id=?");
            p1.setString(1, userId);
            ResultSet r = p1.executeQuery();
            if(r.next()){
                student_id.setText(r.getString("student_id"));
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    public static void setUserId(String user_id){
        userId = user_id;
    }
    @FXML
    public void okBtnHandler(){
        try{
            Connection connection = connector.getConnection();
            PreparedStatement p1 = connection.prepareStatement("INSERT INTO blocked_students VALUES(?,?)");
            p1.setString(1, userId);
            p1.setString(2, block_reason.getText());
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
