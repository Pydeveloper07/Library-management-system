package mainPage;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import loginPage.UserSession;

import java.io.File;
import java.util.ArrayList;

public class MainController {
    private UserSession userSession = UserSession.getInstance();
    private MainController mainController = null;
    public MainController getMainController(){
        if(mainController == null){
            mainController = new MainController();
        }
        return mainController;
    }

    //load user photo and name
    public void loadUserDetails(Label userNameLabel, Circle avatar){
        userNameLabel.setText(userSession.getFirstName() + " " + userSession.getLastName());
        //Avatar setting
        File file = new File("./src/images/userPhoto/"+userSession.getUserId()+".jpg");
        if (file.exists()) {
            avatar.setFill(new ImagePattern(new Image("images/userPhoto/" + userSession.getUserId() + ".jpg")));
        }else{
            Image image = new Image("images/demo.jpg",false);
            avatar.setFill(new ImagePattern(image));
        }
        avatar.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
    }

    public void initializeStudentTable(BorderPane pane){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../table/StudentTable.fxml"));
            pane.setCenter(fxmlLoader.load());
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void initializeHome(BorderPane pane){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../homePage/homePage.fxml"));
            pane.setCenter(fxmlLoader.load());
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void initializeBookTable(BorderPane pane){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../table/BookTable.fxml"));
            pane.setCenter(fxmlLoader.load());
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void setPane(MouseEvent event, GridPane pane, ArrayList<Button> buttons, Label topLabel){
        pane.toFront();
        setActiveButton(buttons, (Button)event.getSource(), topLabel);
    }
    private void setActiveButton(ArrayList<Button> buttons, Button btn, Label topLabel){
        for(Button button:buttons){
            if(btn == button){
                btn.setStyle("-fx-background-color: rgb(16, 128, 5); -fx-text-fill: white;");
                topLabel.setText(letterSpace(btn.getText().toUpperCase(), 4));
            }
            else{
                button.setStyle("-fx-background-color: rgb(255, 255, 255); -fx-text-fill: black;");
            }
        }
    }
    public String letterSpace(String text, double space){
        String resultText = "";
        for(int i = 0; i < text.length(); i++){
            resultText += text.charAt(i);
            for(int j = 0; j < space; j++){
                resultText += " ";
            }
        }
        return resultText;
    }


}
