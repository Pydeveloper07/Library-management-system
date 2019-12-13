package WindowLoader;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class WindowLoader {
    private WindowLoader windowLoader = null;
    public WindowLoader getWindowLoader(){
        synchronized(this) {
            if (windowLoader == null){
                windowLoader = new WindowLoader();
            }
        }
        return windowLoader;
    }
    public void loadMainWindow(){
        Stage stage = null;
        Parent root = null;
        try{
            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("../mainPage/mainPage.fxml"));
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        stage.setScene(new Scene(root, 1144 , 622));
        stage.show();
    }
    public void loadAddNewUserWindow(){
        Stage stage = new Stage();
        Parent root;
        try{
            root = FXMLLoader.load(getClass().getResource("../form/addStudentsForm/StudentForm.fxml"));
            stage.setScene(new Scene(root, 305 , 370));
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Add New Student");
        stage.setResizable(false);
        stage.show();
    }
}
