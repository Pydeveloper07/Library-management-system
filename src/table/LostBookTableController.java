package table;

import dataBase.DBConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LostBookTableController implements Initializable {

    @FXML
    private TableView<ModelLostBookTable> table;
    @FXML
    private TableColumn<ModelLostBookTable,String> col_book_id;
    @FXML
    private TableColumn<ModelLostBookTable,String> col_isbn;
    @FXML
    private TableColumn<ModelLostBookTable,String> col_student_id;
    @FXML
    private TableColumn<ModelLostBookTable,String> col_lost_date;
    @FXML
    private TextField filter;
    private DBConnector connector = new DBConnector().getConnector();

    ObservableList<ModelLostBookTable> oblist = FXCollections.observableArrayList();

    PreparedStatement preparedStatement;
    Connection connection;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            connection = connector.getConnection();
            ResultSet resultSet=connection.createStatement().executeQuery(
                    "SELECT * FROM lost_books");
            while (resultSet.next()){
                oblist.add(new ModelLostBookTable(
                        resultSet.getString("book_id"),
                        resultSet.getString("isbn"),
                        resultSet.getString("student_id"),
                        resultSet.getString("Lost_date")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        col_book_id.setCellValueFactory(new PropertyValueFactory<>("book_id"));
        col_isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        col_student_id.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        col_student_id.setCellFactory(TextFieldTableCell.forTableColumn());

        col_lost_date.setCellValueFactory(new PropertyValueFactory<>("lost_date"));
        col_lost_date.setCellFactory(TextFieldTableCell.forTableColumn());

        table.setEditable(true);
        table.setItems(oblist);

        FilteredList<ModelLostBookTable> filteredData = new FilteredList<>(oblist, b -> true);
        filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String upperCaseFilter = newValue.toUpperCase();

                if (person.getIsbn().toUpperCase().indexOf(upperCaseFilter) != -1 ) {
                    return true; // Filter matches first name.
                } else if (person.getStudent_id().toUpperCase().indexOf(upperCaseFilter) != -1 ) {
                    return true; // Filter matches first name.
                }
                else
                    return false;
            });
        });

        SortedList<ModelLostBookTable> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);

    }



}
