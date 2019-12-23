package windowLoader;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mainPage.MainPageAdminController;
import mainPage.MainPageLibrarianController;
import mainPage.MainPageUserController;

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

    public void loadBlockedStudentForm(){
        Stage stage = new Stage();
        Parent root;
        try{
            root = FXMLLoader.load(getClass().getResource("../form/blockStudentForm/BlockReason.fxml"));
            stage.setScene(new Scene(root, 510 , 357));
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Block Student");
        stage.setResizable(false);
        stage.show();
    }
    public void loadIssueBookForm(){
        Stage stage = new Stage();
        Parent root;
        try{
            root = FXMLLoader.load(getClass().getResource("../form/issuedDateBook/IssuedForm.fxml"));
            stage.setScene(new Scene(root, 320 , 318));
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Issue Book");
        stage.setResizable(false);
        stage.show();
    }
    public void loadMainWindow(int title){
        Stage stage = new Stage();
        Parent root = new BorderPane();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../mainPage/mainPage.fxml"));
        fxmlLoader.setRoot(root);
        switch(title){
            case 0:
                fxmlLoader.setController(new MainPageAdminController()); break;
            case 1:
                fxmlLoader.setController(new MainPageLibrarianController()); break;
            case 2:
                fxmlLoader.setController(new MainPageUserController()); break;
        }
        try{
            fxmlLoader.load();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        stage.setTitle("IUT Library Management System");
        stage.setOnCloseRequest(e-> Platform.exit());
        stage.show();
    }
    public void loadAddNewUserWindow(){
        Stage stage = new Stage();
        Parent root;
        try{
            root = FXMLLoader.load(getClass().getResource("../form/addStudentsForm/StudentForm.fxml"));
            stage.setScene(new Scene(root, 355 , 370));
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Add New Student");
        stage.setResizable(false);
        stage.show();
    }
    public void loadAddNewLibrarianWindow(){
        Stage stage = new Stage();
        Parent root;
        try{
            root = FXMLLoader.load(getClass().getResource("../form/addLibrarianForm/LibrarianForm.fxml"));
            stage.setScene(new Scene(root, 360 , 400));
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Add New Librarian");
        stage.setResizable(false);
        stage.show();
    }
    public void loadPieChart(BorderPane borderPane){
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Lost Books", 13),
                        new PieChart.Data("Available Books", 25),
                        new PieChart.Data("Issued Books", 10));
        final PieChart chart = new PieChart(pieChartData);
        borderPane.setCenter(chart);
    }
    public void loadStudentHome(BorderPane borderPane){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../homePage/StudentHomePage.fxml"));
            borderPane.setCenter(fxmlLoader.load());
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void loadLineChart(GridPane statisticsPane){
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        //xAxis.setLabel("Month");
        final LineChart<String,Number> lineChart =
                new LineChart<String,Number>(xAxis,yAxis);
        lineChart.setTitle("Statistics, 2019");

        XYChart.Series series = new XYChart.Series();
        series.setName("Number of books");
        series.getData().add(new XYChart.Data("Jan", 0));
        series.getData().add(new XYChart.Data("Feb", 0));
        series.getData().add(new XYChart.Data("Mar", 60));
        series.getData().add(new XYChart.Data("Apr", 0));
        series.getData().add(new XYChart.Data("May", 100));
        series.getData().add(new XYChart.Data("Jun", 0));
        series.getData().add(new XYChart.Data("Jul", 150));
        series.getData().add(new XYChart.Data("Aug", 45));
        series.getData().add(new XYChart.Data("Sep", 43));
        series.getData().add(new XYChart.Data("Oct", 17));
        series.getData().add(new XYChart.Data("Nov", 29));
        series.getData().add(new XYChart.Data("Dec", 25));
        lineChart.getData().add(series);
        statisticsPane.add(lineChart, 0, 0);
    }
    public void loadAddNewBookWindow(){
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../form/addBooksForm/BooksForm.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root, 400 , 700));
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Add New Book");
        stage.setResizable(false);
        stage.show();
    }
    public void loadChargeStudentForm(){
        Stage stage = new Stage();
        Parent root;
        try{
            root = FXMLLoader.load(getClass().getResource("../form/chargeStudentForm/ChargeAmount.fxml"));
            stage.setScene(new Scene(root, 350 , 350));
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Charge Student");
        stage.setResizable(false);
        stage.show();
    }
    public void loadLoginWindow(){
        Stage stage = new Stage();
        Parent root = null;
        try{
            root = FXMLLoader.load(getClass().getResource("../loginPage/logInPage.fxml"));
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        stage.setScene(new Scene(root, 608 , 690));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
    public void loadEditProfileWindow(){
        Stage stage = new Stage();
        Parent root = null;
        try{
            root = FXMLLoader.load(getClass().getResource("../editProfilePage/editProfilePage.fxml"));
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        stage.setScene(new Scene(root, 468 , 388));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public void loadBookInfoWindow(){
        Stage stage = new Stage();
        Parent root;
        try{
            root = FXMLLoader.load(getClass().getResource("../bookInfo/bookInfo.fxml"));
            stage.setScene(new Scene(root, 600 , 400));
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Book Info");
        stage.setResizable(false);
        stage.show();
    }
}
