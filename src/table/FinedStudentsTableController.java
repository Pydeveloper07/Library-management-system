package table;

import windowLoader.WindowLoader;
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

public class FinedStudentsTableController implements Initializable {

    @FXML
    private TableView<FinedStudentsModelTable> table;
    @FXML
    private TableColumn<FinedStudentsModelTable, String> col_id;
    @FXML
    private TableColumn<FinedStudentsModelTable, String> col_book_id;
    @FXML
    private TableColumn<FinedStudentsModelTable, String> col_due_date;
    @FXML
    private TableColumn<FinedStudentsModelTable, String> col_charge_amount;
    @FXML
    private TextField filter;
    private WindowLoader windowLoader = new WindowLoader().getWindowLoader();

    private DBConnector connector = new DBConnector().getConnector();

    ObservableList<FinedStudentsModelTable> oblist = FXCollections.observableArrayList();

    PreparedStatement preparedStatement;
    Connection connection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            connection = connector.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM fined_students");
            while (resultSet.next()) {
                oblist.add(new FinedStudentsModelTable(
                        resultSet.getString("student_id"),
                        resultSet.getString("book_id"),
                        resultSet.getString("due_date"),
                        resultSet.getString("charge_amount")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));

        col_book_id.setCellValueFactory(new PropertyValueFactory<>("book_id"));
        col_book_id.setCellFactory(TextFieldTableCell.forTableColumn());

        col_due_date.setCellValueFactory(new PropertyValueFactory<>("due_date"));
        col_due_date.setCellFactory(TextFieldTableCell.forTableColumn());

        col_charge_amount.setCellValueFactory(new PropertyValueFactory<>("charge_amount"));
        col_charge_amount.setCellFactory(TextFieldTableCell.forTableColumn());

        table.setEditable(false);
        table.setItems(oblist);

        FilteredList<FinedStudentsModelTable> filteredData = new FilteredList<>(oblist, b -> true);
        filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String upperCaseFilter = newValue.toUpperCase();

                if (person.getId().toUpperCase().indexOf(upperCaseFilter) != -1 ) {
                    return true; // Filter matches first name.
                }
                else
                    return false;
            });
        });

        SortedList<FinedStudentsModelTable> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }
    @FXML
    public void deleteUserTable(){
        FinedStudentsModelTable modelTable = table.getSelectionModel().getSelectedItem();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM fined_students WHERE student_id=?");
            String id = modelTable.getId();
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            oblist.remove(table.getSelectionModel().getSelectedIndex());
            table.getSelectionModel().clearSelection();
            table.getItems().clear();
            table.getItems().addAll(oblist);

        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}
