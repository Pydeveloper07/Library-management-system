package editProfilePage;

import dataBase.DBConnector;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import loginPage.UserSession;

import java.sql.*;

public class Controller {
    DBConnector connector = new DBConnector().getConnector();
    UserSession userSession = UserSession.getInstance();
    private int person;
    @FXML
    private TextField username;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField oldpassword;
    @FXML
    private TextField newpassword;
    @FXML
    private TextField newpasswordagain;

    public void initialize() {
        String id = userSession.getUserId();
        try {
            Connection connection = connector.getConnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT student_id, first_name, last_name, password_code FROM students WHERE student_id=?");
            PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT librarian_id, first_name, last_name, password_code FROM librarian WHERE librarian_id=?");
            PreparedStatement preparedStatement3 = connection.prepareStatement("SELECT admin_id, first_name, last_name, password_code FROM admin_table WHERE admin_id=?");
            preparedStatement1.setString(1, id);
            preparedStatement2.setString(1, id);
            preparedStatement3.setString(1, id);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            ResultSet resultSet3 = preparedStatement3.executeQuery();
            if (resultSet1.next()) {
                username.setText(resultSet1.getString("student_id"));
                firstname.setText(resultSet1.getString("first_name"));
                lastname.setText(resultSet1.getString("last_name"));
                person = 1;
            } else if (resultSet2.next()) {
                username.setText(resultSet2.getString("librarian_id"));
                firstname.setText(resultSet2.getString("first_name"));
                lastname.setText(resultSet2.getString("last_name"));
                person = 2;
            } else if (resultSet3.next()) {
                username.setText(resultSet3.getString("admin_id"));
                firstname.setText(resultSet3.getString("first_name"));
                lastname.setText(resultSet3.getString("last_name"));
                person = 3;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    public void saveBtnHandler() {
        String oldPassword = "";
        try {
            Connection connection = connector.getConnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT password_code FROM students WHERE student_id=?");
            PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT password_code FROM librarian WHERE librarian_id=?");
            PreparedStatement preparedStatement3 = connection.prepareStatement("SELECT password_code FROM admin_table WHERE admin_id=?");
            ResultSet resultSet;
            switch (person) {
                case 1:
                    preparedStatement1.setString(1, userSession.getUserId());
                    resultSet = preparedStatement1.executeQuery();
                    resultSet.next();
                    oldPassword = resultSet.getString("password_code");
                    break;
                case 2:
                    preparedStatement2.setString(1, userSession.getUserId());
                    resultSet = preparedStatement2.executeQuery();
                    resultSet.next();
                    oldPassword = resultSet.getString("password_code");
                    break;
                case 3:
                    preparedStatement3.setString(1, userSession.getUserId());
                    resultSet = preparedStatement3.executeQuery();
                    resultSet.next();
                    oldPassword = resultSet.getString("password_code");
                    break;
            }
            if (oldPassword.equals(oldpassword.getText())) {
                if (newpassword.getText().equals(newpasswordagain.getText())) {
                    saveButtonHandler();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText("New passwords don't match!");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText("Old password is incorrect!");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void saveButtonHandler() {
        try {
            Connection connection = connector.getConnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement("UPDATE students SET first_name=?, last_name=?, password_code=? WHERE student_id=?");
            PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE librarian SET first_name=?, last_name=?, password_code=? WHERE librarian_id=?");
            PreparedStatement preparedStatement3 = connection.prepareStatement("UPDATE admin_table SET first_name=?, last_name=?, password_code=? WHERE admin_id=?");
            preparedStatement1.setString(1, firstname.getText());
            preparedStatement1.setString(2, lastname.getText());
            preparedStatement1.setString(3, newpassword.getText());
            preparedStatement1.setString(4, userSession.getUserId());

            preparedStatement2.setString(1, firstname.getText());
            preparedStatement2.setString(2, lastname.getText());
            preparedStatement2.setString(3, newpassword.getText());
            preparedStatement2.setString(4, userSession.getUserId());

            preparedStatement3.setString(1, firstname.getText());
            preparedStatement3.setString(2, lastname.getText());
            preparedStatement3.setString(3, newpassword.getText());
            preparedStatement3.setString(4, userSession.getUserId());
            Alert alert = null;
            switch (person) {
                case 1:
                    preparedStatement1.executeUpdate();
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Update");
                    alert.setHeaderText("Successfully Updated!");
                    alert.showAndWait();
                    cancelButtonHandler();
                    break;
                case 2:
                    preparedStatement2.executeUpdate();
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Update");
                    alert.setHeaderText("Successfully Updated!");
                    alert.showAndWait();
                    cancelButtonHandler();
                    break;
                case 3:
                    preparedStatement3.executeUpdate();
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Update");
                    alert.setHeaderText("Successfully Updated!");
                    alert.showAndWait();
                    cancelButtonHandler();
                    break;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    public void cancelButtonHandler() {
        ((Stage)username.getScene().getWindow()).close();
    }
}