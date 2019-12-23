package table;

import WindowLoader.WindowLoader;
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

public class BlockedStudentsTableController implements Initializable {

    @FXML
    private TableView<BlockedStudentsModelTable> table;
    @FXML
    private TableColumn<BlockedStudentsModelTable, String> col_id;
    @FXML
    private TableColumn<BlockedStudentsModelTable, String> col_reason;
    @FXML
    private TextField filter;
    private WindowLoader windowLoader = new WindowLoader().getWindowLoader();

    private DBConnector connector = new DBConnector().getConnector();

    ObservableList<BlockedStudentsModelTable> oblist = FXCollections.observableArrayList();

    PreparedStatement preparedStatement;
    Connection connection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            connection = connector.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT student_id, reason FROM blocked_students");
            while (resultSet.next()) {
                oblist.add(new BlockedStudentsModelTable(
                        resultSet.getString("student_id"),
                        resultSet.getString("reason")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));

        col_reason.setCellValueFactory(new PropertyValueFactory<>("reason"));
        col_reason.setCellFactory(TextFieldTableCell.forTableColumn());

        table.setEditable(false);
        table.setItems(oblist);

        FilteredList<BlockedStudentsModelTable> filteredData = new FilteredList<>(oblist, b -> true);
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

        SortedList<BlockedStudentsModelTable> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }
    @FXML
    public void unblock(){
        BlockedStudentsModelTable modelTable = table.getSelectionModel().getSelectedItem();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM blocked_students WHERE student_id=?");
            String id = modelTable.getId();
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();

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
