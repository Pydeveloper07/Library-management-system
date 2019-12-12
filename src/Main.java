import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("loginPage/logInPage.fxml"));
        stage.setScene(new Scene(root, 608 , 690));
        stage.initStyle(StageStyle.UNDECORATED);
//        Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
//        stage.setScene(new Scene(root, 1144 , 622));
        stage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
    // test
}
