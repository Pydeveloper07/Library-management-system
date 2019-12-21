package table;

import dataBase.DBConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class IssuedBookTableController implements Initializable {

    @FXML
    private TableView<ModelIssuedBookTable> table;
    @FXML
    private TableColumn<ModelIssuedBookTable,String> col_isbn;
    @FXML
    private TableColumn<ModelIssuedBookTable,String> col_student_id;
    @FXML
    private TableColumn<ModelIssuedBookTable,String> col_issued_date;
    @FXML
    private TableColumn<ModelIssuedBookTable,String> col_due_date;

    private DBConnector connector = new DBConnector().getConnector();

    ObservableList<ModelIssuedBookTable> oblist = FXCollections.observableArrayList();

    PreparedStatement preparedStatement;
    Connection connection;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            connection = connector.getConnection();
            ResultSet resultSet=connection.createStatement().executeQuery(
                    "SELECT * FROM ordered_books");
            while (resultSet.next()){
                oblist.add(new ModelIssuedBookTable(
                        resultSet.getString("isbn"),
                        resultSet.getString("student_id"),
                        resultSet.getString("issued_date"),
                        resultSet.getString("due_date")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        col_student_id.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        col_student_id.setCellFactory(TextFieldTableCell.forTableColumn());

        col_issued_date.setCellValueFactory(new PropertyValueFactory<>("issued_date"));
        col_issued_date.setCellFactory(TextFieldTableCell.forTableColumn());

        col_due_date.setCellValueFactory(new PropertyValueFactory<>("due_date"));
        col_due_date.setCellFactory(TextFieldTableCell.forTableColumn());

        table.setEditable(true);
        table.setItems(oblist);
    }
}
