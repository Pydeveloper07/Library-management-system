package form.addStudentsForm;

import dataBase.DBConnector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentFormController implements Initializable {
    @FXML
    private Button cancelBtn;
    @FXML
    private ComboBox<String> facultyCombo = new ComboBox<>();

    @FXML
    TextField id;
    @FXML
    TextField name;
    @FXML
    TextField surname;
    @FXML
    TextField contact_number;
    @FXML
    TextField email;
    @FXML
    TextField password;

    PreparedStatement preparedStatement;
    Connection connection;


    public StudentFormController() throws SQLException {
        connection= DBConnector.getConnection();
    }

    @FXML
    private String saveData() {

        try {
            String students = "INSERT INTO students ( student_id, first_name, last_name, faculty, contact_number,email,password_code) VALUES (?,?,?,?,?,?,?)";
            preparedStatement = (PreparedStatement) connection.prepareStatement(students);
            preparedStatement.setString(1, id.getText());
            preparedStatement.setString(2, name.getText());
            preparedStatement.setString(3, surname.getText());
            preparedStatement.setString(4, facultyCombo.getValue());
            preparedStatement.setString(5, contact_number.getText());
            preparedStatement.setString(6, email.getText());
            preparedStatement.setString(7, password.getText());

            preparedStatement.executeUpdate();

//            fetRowList();
            //clear fields
            clearFields();
            return "Success";

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return "Exception";
        }
    }
    private void clearFields() {
        id.clear();
        name.clear();
        surname.clear();
        contact_number.clear();
        email.clear();
        password.clear();
    }


    public void initialize (URL location, ResourceBundle resources)
    {
        facultyCombo.getItems().addAll("CSE", "ICE", "SOL");
        facultyCombo.setPromptText("CSE");
    }





    public void cancelButtonHandler()
    {
        ((Stage)cancelBtn.getScene().getWindow()).close();
    }
}
