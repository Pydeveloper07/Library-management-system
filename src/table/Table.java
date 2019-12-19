package table;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Table extends Application {
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("StudentTable.fxml"));
        stage.setScene(new Scene(root, 1408 , 690));
        stage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}