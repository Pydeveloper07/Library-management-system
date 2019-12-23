package table;

import dataBase.DBConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
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
    @FXML
    private TextField filter;
    private DBConnector connector = new DBConnector().getConnector();
    private static int count=0;
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
                        count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        col_student_id.setCellValueFactory(new PropertyValueFactory<>("student_id"));

        col_issued_date.setCellValueFactory(new PropertyValueFactory<>("issued_date"));

        col_due_date.setCellValueFactory(new PropertyValueFactory<>("due_date"));

        table.setEditable(true);
        table.setItems(oblist);


        FilteredList<ModelIssuedBookTable> filteredData = new FilteredList<>(oblist, b -> true);
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

        SortedList<ModelIssuedBookTable> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);

    }
    @FXML
    private void delete(){
        ModelIssuedBookTable modelIssuedBookTable = table.getSelectionModel().getSelectedItem();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM ordered_books WHERE isbn=?");
            String isbn = modelIssuedBookTable.getIsbn();
            preparedStatement.setString(1, isbn);
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
    @FXML
    private void addLost(){
        ModelIssuedBookTable modelIssuedBookTable = table.getSelectionModel().getSelectedItem();
        try{
            PreparedStatement p = connection.prepareStatement("SELECT book_id FROM book WHERE isbn=?");
            p.setString(1, modelIssuedBookTable.getIsbn());
            ResultSet r = p.executeQuery();
            r.next();
            System.out.println(r.getString("book_id"));

            String students = "INSERT INTO lost_books ( book_id, isbn,student_id,lost_date) VALUES (?,?,?,?)";
            preparedStatement = (PreparedStatement) connection.prepareStatement(students);
            preparedStatement.setString(1, String.valueOf(count));
            preparedStatement.setString(2, modelIssuedBookTable.getIsbn());
            preparedStatement.setString(3, modelIssuedBookTable.getStudent_id());
            preparedStatement.setString(4, modelIssuedBookTable.getIssued_date());
            preparedStatement.executeUpdate();

            oblist.remove(table.getSelectionModel().getSelectedIndex());
            table.getSelectionModel().clearSelection();
            table.getItems().clear();
            table.getItems().addAll(oblist);
            count--;
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

}
