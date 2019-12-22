package form.addStudentsForm;

import dataBase.DBConnector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
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
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField contact_number;
    @FXML
    private TextField email;
    @FXML
    TextField password;
    private DBConnector connector = new DBConnector().getConnector();

    PreparedStatement preparedStatement;
    Connection connection;


    public StudentFormController() throws SQLException {
        connection = connector.getConnection();
    }

    @FXML
    private void handleEventSave(){
        if (id.getText().isEmpty()||name.getText().isEmpty()||
                surname.getText().isEmpty()||contact_number.getText().isEmpty()||
                email.getText().isEmpty()||password.getText().isEmpty())
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
        }
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
            preparedStatement.close();
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
        facultyCombo.setValue("CSE");
    }
    public void cancelButtonHandler()
    {
        ((Stage)cancelBtn.getScene().getWindow()).close();
    }
}
