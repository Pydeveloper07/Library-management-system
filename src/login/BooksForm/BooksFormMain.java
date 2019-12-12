package BooksForm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BooksFormMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("BooksForm.fxml"));
        primaryStage.setTitle("New Book");
        primaryStage.setScene(new Scene(root, 400, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
