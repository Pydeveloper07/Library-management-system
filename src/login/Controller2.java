package login;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.scene.BoundsAccessor;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class Controller2 {
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
    private ArrayList<Button> buttons = new ArrayList<>();
    @FXML
    private GridPane homePane;
    @FXML
    private GridPane librariansPane;
    @FXML
    private GridPane studentsPane;
    @FXML
    private GridPane booksPane;
public void initialize(){
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    //xAxis.setLabel("Month");
    final LineChart<String,Number> lineChart =
            new LineChart<String,Number>(xAxis,yAxis);
    lineChart.setTitle("Satatisticcs, 2019");

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
    buttons.add(homeMenuItem);
    buttons.add(librariansMenuItem);
    buttons.add(studentsMenuItem);
    buttons.add(booksMenuItem);

    //Set Home Page as default
    homePane.toFront();
    topLabel.setText(letterSpace(homeMenuItem.getText().toUpperCase(), 4));
    homeMenuItem.setStyle("-fx-background-color: rgb(16, 128, 5); -fx-text-fill: white;");
    homePane.add(lineChart, 0, 0);

    //Setting letter spaced menu item names
    for(Button button:buttons){
        button.setText(letterSpace(button.getText(), 1));
    }

}
    @FXML
    public void setHomePane(MouseEvent event){
        homePane.toFront();
        setActiveButton((Button)event.getSource());
    }
    @FXML
    public void setLibrariansPane(MouseEvent event){
        librariansPane.toFront();
        setActiveButton((Button)event.getSource());
    }
    @FXML
    public void setStudentsPane(MouseEvent event){
        studentsPane.toFront();
        setActiveButton((Button)event.getSource());
    }
    @FXML
    public void setBooksPane(MouseEvent event){
        booksPane.toFront();
        setActiveButton((Button)event.getSource());
    }
    private void setActiveButton(Button btn){
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
    private String letterSpace(String text, double space){
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
