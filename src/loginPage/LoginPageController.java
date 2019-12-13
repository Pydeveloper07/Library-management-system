package loginPage;

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
    public void loginButtonHandler(){
        final String DATABASE_URL = "jdbc:derby:C:\\Users\\User\\JavaPrograms\\mubina\\src\\lms";
        final String USERNAME = "java_masters";
        final String PASSWORD = "forever";
        final String QUERY = "SELECT password_code FROM students WHERE student_id=? " +
                "UNION SELECT password_code FROM librarian WHERE librarian_id=? " +
                "UNION SELECT password_code FROM admin_table WHERE admin_id=?";

        try(
                Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        ){
            PreparedStatement getPersonById = connection.prepareStatement(QUERY);
            String id;
            try {
                id = textField.getText();
                getPersonById.setString(1, id);
                getPersonById.setString(2, id);
                getPersonById.setString(3, id);
            }
            catch(Exception ex){
                passwordField.setText("");
                textField.setText("");
                textField.setStyle("-fx-border-color:red;");
            }
            ResultSet resultSet = getPersonById.executeQuery();
            resultSet.next();
            if((String.valueOf(resultSet.getString("password_code")).equals(passwordField.getText())))
                accessMainWindow();
            else{
                passwordField.clear();
                textField.clear();
                passwordField.setStyle("-fx-border-color:red;");
                textField.setStyle("-fx-border-color:red;");
                warnLabel1.setVisible(true);
                warnLabel2.setVisible(true);
            }
        }
        catch (SQLException ex){
            passwordField.clear();
            textField.clear();
            passwordField.setStyle("-fx-border-color:red;");
            textField.setStyle("-fx-border-color:red;");
            warnLabel1.setVisible(true);
            warnLabel2.setVisible(true);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void accessMainWindow() throws Exception{
        Stage stage1 = (Stage)minBtn.getScene().getWindow();
        stage1.close();
        windowLoader.loadMainWindow();
    }
}
