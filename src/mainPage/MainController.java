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
import javafx.stage.FileChooser;
import loginPage.UserSession;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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
        userNameLabel.setText(userSession.getFirstName().toUpperCase() + " " + userSession.getLastName().toUpperCase());
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
    public void initializeTotalStudentsTable(BorderPane pane){
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
            pane.setTop(fxmlLoader.load());
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void initializeStudentHome(BorderPane pane){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../homePage/StudentHomePage.fxml"));
            pane.setTop(fxmlLoader.load());
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }


    public void initializeLibrariansTable(BorderPane pane){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../table/LibrariansTable.fxml"));
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
    public void initializeIssuedBookTable(BorderPane pane){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../table/IssuedBooksTable.fxml"));
            pane.setCenter(fxmlLoader.load());
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void initializeLostBookTable(BorderPane pane){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../table/LostBooksTable.fxml"));
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
    public void initializeFinedStudentsTable(BorderPane pane){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../table/FinedStudentsTable.fxml"));
            pane.setCenter(fxmlLoader.load());
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void initializeBlockedStudentsTable(BorderPane pane){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../table/BlockedStudentsTable.fxml"));
            pane.setCenter(fxmlLoader.load());
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void initializeReservedStudentsTable(BorderPane pane){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../table/ReservedStudentsTable.fxml"));
            pane.setCenter(fxmlLoader.load());
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void setAvatar(Circle avatar) throws MalformedURLException {
        int width = 130;    //width of the image
        int height = 130;   //height of the image
        BufferedImage imageBuf = null;
        File f = null;

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Jpeg files", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        //create and set image
        Image image = new Image(selectedFile.toURI().toString());

        //Image image=new Image("images/demo.jpg",false);
        avatar.setFill(new ImagePattern(image));
        avatar.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));

        //write
        try {

            ImageIO.write(ImageIO.read(
                    new File(selectedFile.toString())),
                    "jpg",
                    new File("./src/images/userPhoto/"+userSession.getUserId()+".jpg"));
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}
