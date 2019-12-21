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

public class ReservedStudentsTableController implements Initializable {

    @FXML
    private TableView<ReservedStudentsModelTable> table;
    @FXML
    private TableColumn<ReservedStudentsModelTable, String> col_id;
    @FXML
    private TableColumn<ReservedStudentsModelTable, String> col_isbn;
    @FXML
    private TableColumn<ReservedStudentsModelTable, String> col_reservation_date;
    @FXML
    private TableColumn<ReservedStudentsModelTable, String> col_waiting_until;
    @FXML
    private TextField filter;
    private WindowLoader windowLoader = new WindowLoader().getWindowLoader();

    private DBConnector connector = new DBConnector().getConnector();

    ObservableList<ReservedStudentsModelTable> oblist = FXCollections.observableArrayList();

    PreparedStatement preparedStatement;
    Connection connection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            connection = connector.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM waiting_list");
            while (resultSet.next()) {
                oblist.add(new ReservedStudentsModelTable(
                        resultSet.getString("student_id"),
                        resultSet.getString("isbn"),
                        resultSet.getString("reservation_date"),
                        resultSet.getString("waiting_until")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("student_id"));

        col_isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        col_isbn.setCellFactory(TextFieldTableCell.forTableColumn());

        col_reservation_date.setCellValueFactory(new PropertyValueFactory<>("reservation_date"));
        col_reservation_date.setCellFactory(TextFieldTableCell.forTableColumn());

        col_waiting_until.setCellValueFactory(new PropertyValueFactory<>("waiting_until"));
        col_waiting_until.setCellFactory(TextFieldTableCell.forTableColumn());

        table.setEditable(false);
        table.setItems(oblist);

        FilteredList<ReservedStudentsModelTable> filteredData = new FilteredList<>(oblist, b -> true);
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

        SortedList<ReservedStudentsModelTable> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }
    @FXML
    public void deleteUserTable(){

//        ModelTable currentPerson = (ModelTable) ((ModelTable) t.getTableView().getItems().get(
//                t.getTablePosition().getRow())  ) ;
//        //remove selected item from the table list
//        data.remove(currentPerson);
    }
}
