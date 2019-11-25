package statistic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class LineChartSample extends Application {
    @Override
    public void start(Stage stage) throws Exception{

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        //xAxis.setLabel("Month");
        final LineChart<String,Number> lineChart =
                new LineChart<String,Number>(xAxis,yAxis);
        lineChart.setTitle("Satatistics, 2019");

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


        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(series);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}