package mainPage;

import WindowLoader.WindowLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class MainPageAdminController {
    private WindowLoader windowLoader = new WindowLoader().getWindowLoader();
    private MainController mainController = new MainController().getMainController();
    @FXML
    private Label topLabel;
    @FXML
    private Button homeMenuItem;
    @FXML
    private Button librariansMenuItem;
    @FXML
    private Button studentsMenuItem;
    @FXML
    private Button booksMenuItem;
    @FXML
    private Button statisticsMenuItem;
    private ArrayList<Button> buttons = new ArrayList<>();
    @FXML
    private GridPane homePane;
    @FXML
    private GridPane librariansPane;
    @FXML
    private GridPane studentsPane;
    @FXML
    private GridPane booksPane;
    @FXML
    private GridPane statisticsPane;
    @FXML
    private Circle avatar;
public void initialize() throws MalformedURLException {

    //Avatar setting
    //setAvatar();
    windowLoader.loadLineChart(statisticsPane);
    buttons.add(homeMenuItem);
    buttons.add(librariansMenuItem);
    buttons.add(studentsMenuItem);
    buttons.add(booksMenuItem);
    buttons.add(statisticsMenuItem);

    //Set Home Page as default
    homePane.toFront();
    topLabel.setText(mainController.letterSpace(homeMenuItem.getText().toUpperCase(), 4));
    homeMenuItem.setStyle("-fx-background-color: rgb(16, 128, 5); -fx-text-fill: white;");

    //Setting letter spaced menu item names
    for(Button button:buttons){
        button.setText(mainController.letterSpace(button.getText(), 1));
    }
}
    @FXML
    public void setHomePane(MouseEvent event){
        mainController.setPane(event, homePane, buttons, topLabel);
    }
    @FXML
    public void setLibrariansPane(MouseEvent event){
        mainController.setPane(event, librariansPane, buttons, topLabel);
    }
    @FXML
    public void setStudentsPane(MouseEvent event){
        mainController.setPane(event, studentsPane, buttons, topLabel);
    }
    @FXML
    public void setBooksPane(MouseEvent event){
        mainController.setPane(event, booksPane, buttons, topLabel);
    }
    @FXML
    public void setStatisticsPane(MouseEvent event){
        mainController.setPane(event, statisticsPane, buttons, topLabel);
    }
    @FXML
    private void addNewBookWindow(){
        windowLoader.loadAddNewBookWindow();
    }
    @FXML
    private void addNewUserWindow(){
        windowLoader.loadAddNewUserWindow();
    }
    @FXML
    private void setAvatar() throws MalformedURLException {
/*
        int width = 963;    //width of the image
        int height = 640;   //height of the image
        BufferedImage imageBuf = null;
        File f = null;

        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Jpeg files","*.jpg") );
        File selectedFile= fileChooser.showOpenDialog(null);

        //create and set image
        Image image= new Image(selectedFile.toURL().toString());
        //img1.setImage(image);
*/
        Image image=new Image("images/avatar-icon-images-4.jpg",false);
        avatar.setFill(new ImagePattern(image));
        avatar.setEffect(new DropShadow(+25d,0d,+2d, Color.DARKSEAGREEN));
/*
        //write
        try{
            ImageIO.write(ImageIO.read(
                    new File(selectedFile.toString())),
                    "jpg",
                    new File("images\\"+"write.jpg"));
        }catch(IOException e){
            System.out.println("Error: "+e);
        }
*/

}
}
