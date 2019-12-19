package loginPage;

import dataBase.DBConnector;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.sql.*;
import WindowLoader.WindowLoader;

public class LoginPageController {
    private double xOffSet;
    private double yOffSet;
    private WindowLoader windowLoader = new WindowLoader().getWindowLoader();
    private DBConnector connector = new DBConnector().getConnector();
    private Connection connection;
    @FXML
    Label pleaseLoginLabel;
    @FXML
    private TextField textField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginBtn;
    @FXML
    private Button closeBtn;
    @FXML
    private Label warnLabel1;
    @FXML
    private Label warnLabel2;
    public void initialize(){
        Font font = Font.loadFont("Parisienne-Regular.ttf", 12);
        pleaseLoginLabel.setFont(font);
        warnLabel1.setVisible(false);
        warnLabel2.setVisible(false);
    }
    @FXML
    private Button minBtn;
    public void mouseClicked(MouseEvent event){
        if(event.getSource() == textField){
            textField.setStyle("-fx-background-color: rgb(255, 255, 119);");
        }
        else{
            passwordField.setStyle("-fx-background-color: rgb(255, 255, 119);");
        }
    }
    public void closeButtonClickedHandler(MouseEvent event){
        Stage stage = (Stage)closeBtn.getScene().getWindow();
        stage.close();
    }
    public void minButtonClickedHandler(MouseEvent event){
        Stage stage = (Stage)minBtn.getScene().getWindow();
        stage.setIconified(true);
    }
    public void mousePressedHandler(MouseEvent event){
        Stage stage = (Stage)minBtn.getScene().getWindow();
        xOffSet = stage.getX() - event.getScreenX();
        yOffSet = stage.getY() - event.getScreenY();
    }
    public void dragWindow(MouseEvent event){
        Stage stage = (Stage)minBtn.getScene().getWindow();
        stage.setX(xOffSet + event.getScreenX());
        stage.setY(yOffSet + event.getScreenY());
    }
    private void resetLoginInputs(){
        passwordField.clear();
        textField.clear();
        passwordField.setStyle("-fx-border-color:red;");
        textField.setStyle("-fx-border-color:red;");
        warnLabel1.setVisible(true);
        warnLabel2.setVisible(true);
    }
    public void loginButtonHandler(){
        try{
            connection = connector.getConnection();
            PreparedStatement getStudentById = connection.prepareStatement("SELECT student_id, first_name, last_name, password_code FROM students WHERE student_id=?");
            PreparedStatement getLibrarianById = connection.prepareStatement("SELECT librarian_id, first_name, last_name, password_code FROM librarian WHERE librarian_id=?");
            PreparedStatement getAdminById = connection.prepareStatement("SELECT admin_id, first_name, last_name, password_code FROM admin_table WHERE admin_id=?");
            String id;
            try {
                id = textField.getText();
                getStudentById.setString(1, id);
                getLibrarianById.setString(1, id);
                getAdminById.setString(1, id);
            }
            catch(Exception ex){
                resetLoginInputs();
            }
            ResultSet resultSet1 = getStudentById.executeQuery();
            ResultSet resultSet2 = getLibrarianById.executeQuery();
            ResultSet resultSet3 = getAdminById.executeQuery();
            Stage stage1 = (Stage)minBtn.getScene().getWindow();
            if(resultSet1.next()){
                if((String.valueOf(resultSet1.getString("password_code")).equals(passwordField.getText()))){
                    stage1.close();
                    initUser(resultSet1);
                    windowLoader.loadMainWindow(2);
                }
            }
            else if(resultSet2.next()){
                if((String.valueOf(resultSet2.getString("password_code")).equals(passwordField.getText()))){
                    stage1.close();
                    initUser(resultSet2);
                    windowLoader.loadMainWindow(1);
                }

            }
            else if(resultSet3.next()){
                if((String.valueOf(resultSet3.getString("password_code")).equals(passwordField.getText()))){
                    stage1.close();
                    initUser(resultSet3);
                    windowLoader.loadMainWindow(0);
                }
            }
            connection.close();
            resetLoginInputs();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    public void initUser(ResultSet resultSet) throws SQLException{
        String password = resultSet.getString("password_code");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        UserSession.setInstance(new UserSession(textField.getText(), firstName, lastName));
    }
}
