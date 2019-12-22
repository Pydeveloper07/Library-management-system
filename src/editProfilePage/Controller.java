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
            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT student_id, first_name, last_name, password FROM students WHERE student_id=?");
            PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT librarian_id, first_name, last_name, password FROM librarian WHERE librarian_id=?");
            PreparedStatement preparedStatement3 = connection.prepareStatement("SELECT admin_id, first_name, last_name, password FROM admin_table WHERE admin_id=?");
            preparedStatement1.setString(1, id);
            preparedStatement2.setString(1, id);
            preparedStatement3.setString(1, id);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            ResultSet resultSet3 = preparedStatement3.executeQuery();
            if (resultSet1.next()) {
                person = 1;
                username.setText(resultSet1.getString("student_id"));
                firstname.setText(resultSet1.getString("first_name"));
                lastname.setText(resultSet1.getString("last_name"));
                oldpassword.setText(resultSet1.getString("password"));
            } else if (resultSet2.next()) {
                person = 2;
                username.setText(resultSet2.getString("librarian_id"));
                firstname.setText(resultSet2.getString("first_name"));
                lastname.setText(resultSet2.getString("last_name"));
                oldpassword.setText(resultSet2.getString("password"));
            } else if (resultSet3.next()) {
                person = 3;
                username.setText(resultSet3.getString("admin_id"));
                firstname.setText(resultSet3.getString("first_name"));
                lastname.setText(resultSet3.getString("last_name"));
                oldpassword.setText(resultSet3.getString("password"));
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
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT password FROM ? WHERE ?=?");
            ResultSet resultSet;
            switch (person) {
                case 1:
                    preparedStatement.setString(1, "students");
                    preparedStatement.setString(2, "student_id");
                    preparedStatement.setString(3, userSession.getUserId());
                    resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    oldPassword = resultSet.getString("password");
                    break;
                case 2:
                    preparedStatement.setString(1, "librarian");
                    preparedStatement.setString(2, "librarian_id");
                    preparedStatement.setString(3, userSession.getUserId());
                    resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    oldPassword = resultSet.getString("password");
                    break;
                case 3:
                    preparedStatement.setString(1, "admin_table");
                    preparedStatement.setString(2, "admin_id");
                    preparedStatement.setString(3, userSession.getUserId());
                    resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    oldPassword = resultSet.getString("password");
                    break;
            }
            if (oldPassword == oldpassword.getText()) {
                if (newpassword.getText() == newpasswordagain.getText()) {
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
            PreparedStatement preparedStatement1 = connection.prepareStatement("UPDATE students SET first_name=?, last_name=?, password=? WHERE student_id=?");
            PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE librarian SET first_name=?, last_name=?, password=? WHERE librarian_id=?");
            PreparedStatement preparedStatement3 = connection.prepareStatement("UPDATE admin_table SET first_name=?, last_name=?, password=? WHERE admin_id=?");
            preparedStatement1.setString(1, firstname.getText());
            preparedStatement1.setString(2, lastname.getText());
            preparedStatement1.setString(3, newpassword.getText());

            preparedStatement2.setString(1, firstname.getText());
            preparedStatement2.setString(2, lastname.getText());
            preparedStatement2.setString(3, newpassword.getText());

            preparedStatement3.setString(1, firstname.getText());
            preparedStatement3.setString(2, lastname.getText());
            preparedStatement3.setString(3, newpassword.getText());
            switch (person) {
                case 1:
                    preparedStatement1.executeUpdate();
                    break;
                case 2:
                    preparedStatement2.executeUpdate();
                    break;
                case 3:
                    preparedStatement3.executeUpdate();
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