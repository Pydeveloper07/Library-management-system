package table;

import dataBase.DBConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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

public class BookTableController implements Initializable {

    @FXML
    private TableView<ModelBookTable> table;
    @FXML
    private TableColumn<ModelBookTable,String> col_isbn;
    @FXML
    private TableColumn<ModelBookTable,String> col_title;
    @FXML
    private TableColumn<ModelBookTable,String> col_author;
    @FXML
    private TableColumn<ModelBookTable,String> col_category;
    @FXML
    private TableColumn<ModelBookTable,String> col_descrip;
    @FXML
    private TableColumn<ModelBookTable,String> col_quantity;
    @FXML
    private TableColumn<ModelBookTable,String> col_available;

    private DBConnector connector = new DBConnector().getConnector();

    ObservableList<ModelBookTable> oblist = FXCollections.observableArrayList();

    PreparedStatement preparedStatement;
    Connection connection;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            connection = connector.getConnection();
            ResultSet resultSet=connection.createStatement().executeQuery(
                    "SELECT book_group.isbn, category, first_name, last_name, descrip, title, quantity, available FROM book_group INNER JOIN author_books ON " +
                            "book_group.isbn=author_books.isbn INNER JOIN authors ON author_books.author_id=authors.author_id");
            if (resultSet.next()){
                oblist.add(new ModelBookTable(
                        resultSet.getString("isbn"),
                        resultSet.getString("category"),
                        resultSet.getString("first_name") + resultSet.getString("last_name"),
                        resultSet.getString("descrip"),
                        resultSet.getString("title"),
                        resultSet.getString("quantity"),
                        resultSet.getString("available")
                        ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        col_title.setCellValueFactory(new PropertyValueFactory<>("category"));
        col_title.setCellFactory(TextFieldTableCell.forTableColumn());
        col_title.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ModelBookTable, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<ModelBookTable, String> t) {
                        ModelBookTable modelBookTable = table.getSelectionModel().getSelectedItem();
                        ((ModelBookTable) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setTitle(t.getNewValue());
                        try {
                            connection = connector.getConnection();
                            PreparedStatement statement = connection.prepareStatement(
                                    "UPDATE book_group SET category=? WHERE isbn=?");

                            statement.setString(1, modelBookTable.getCategory());
                            statement.setString(2, modelBookTable.getIsbn());
                            statement.executeUpdate();
                            statement.close();
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        col_author.setCellValueFactory(new PropertyValueFactory<>("author"));
        col_author.setCellFactory(TextFieldTableCell.forTableColumn());
        col_author.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ModelBookTable, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<ModelBookTable, String> t) {
                        ModelBookTable modelBookTable = table.getSelectionModel().getSelectedItem();
                        ((ModelBookTable) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setAuthor(t.getNewValue());
                        try {
                            connection = connector.getConnection();
                            PreparedStatement statement = connection.prepareStatement(
                                    "UPDATE authors SET first_name=? last_name=? WHERE author_id=?");
                            statement.setString(1, modelBookTable.getAuthor());
                            statement.setString(2, modelBookTable.getCategory());
                            statement.executeUpdate();
                            statement.close();
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        col_descrip.setCellValueFactory(new PropertyValueFactory<>("descrip"));
        col_descrip.setCellFactory(TextFieldTableCell.forTableColumn());
        col_descrip.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ModelBookTable, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<ModelBookTable, String> t) {
                        ModelBookTable modelBookTable = table.getSelectionModel().getSelectedItem();
                        ((ModelBookTable) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setDescrip(t.getNewValue());
                        try {
                            connection = connector.getConnection();
                            PreparedStatement statement = connection.prepareStatement(
                                    "UPDATE book_group SET descrip=? WHERE isbn=?");
                            statement.setString(1, modelBookTable.getDescrip());
                            statement.setString(2, modelBookTable.getIsbn());
                            statement.executeUpdate();
                            statement.close();
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_title.setCellFactory(TextFieldTableCell.forTableColumn());
        col_title.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ModelBookTable, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<ModelBookTable, String> t) {
                        ModelBookTable modelBookTable = table.getSelectionModel().getSelectedItem();
                        ((ModelBookTable) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setTitle(t.getNewValue());
                        try {
                            connection = connector.getConnection();
                            PreparedStatement statement = connection.prepareStatement(
                                    "UPDATE book_group SET title=? WHERE isbn=?");

                            statement.setString(1, modelBookTable.getTitle());
                            statement.setString(2, modelBookTable.getIsbn());
                            statement.executeUpdate();
                            statement.close();
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_quantity.setCellFactory(TextFieldTableCell.forTableColumn());

        col_available.setCellValueFactory(new PropertyValueFactory<>("available"));
        col_quantity.setCellFactory(TextFieldTableCell.forTableColumn());

        table.setEditable(true);
        table.setItems(oblist);
    }
}
