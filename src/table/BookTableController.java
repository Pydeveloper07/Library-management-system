package table;

import WindowLoader.WindowLoader;
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
    private TableColumn<ModelBookTable,String> col_author_name;
    @FXML
    private TableColumn<ModelBookTable,String> col_category;
    @FXML
    private TableColumn<ModelBookTable,String> col_descrip;
    @FXML
    private TableColumn<ModelBookTable,String> col_published_date;
    @FXML
    private TableColumn<ModelBookTable,String> col_quantity;
    @FXML
    private TableColumn<ModelBookTable,String> col_available;

    private DBConnector connector = new DBConnector().getConnector();

    ObservableList<ModelBookTable> oblist = FXCollections.observableArrayList();

    PreparedStatement preparedStatement;
    Connection connection;
    private WindowLoader windowLoader = new WindowLoader().getWindowLoader();


    @FXML
    private void addNewBook(){
        windowLoader.loadAddNewBookWindow();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {



        try {
            connection = connector.getConnection();
            ResultSet resultSet=connection.createStatement().executeQuery(
                    "SELECT book_group.isbn, category, author_name, descrip, title, " +
                            "published_date, quantity, available FROM book_group INNER JOIN authors ON " +
                            "book_group.isbn=authors.isbn");
            while (resultSet.next()){
                oblist.add(new ModelBookTable(
                        resultSet.getString("isbn"),
                        resultSet.getString("category"),
                        resultSet.getString("author_name"),
                        resultSet.getString("descrip"),
                        resultSet.getString("title"),
                        resultSet.getString("published_date"),
                        resultSet.getString("quantity"),
                        resultSet.getString("available")
                        ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        col_category.setCellFactory(TextFieldTableCell.forTableColumn());
        col_category.setOnEditCommit(
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

        col_author_name.setCellValueFactory(new PropertyValueFactory<>("author_name"));
        col_author_name.setCellFactory(TextFieldTableCell.forTableColumn());
        col_author_name.setOnEditCommit(
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
                                    "UPDATE authors SET author_name=? WHERE isbn=?");
                            statement.setString(1, modelBookTable.getAuthor());
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

        col_published_date.setCellValueFactory(new PropertyValueFactory<>("published_date"));
        col_published_date.setCellFactory(TextFieldTableCell.forTableColumn());
        col_published_date.setOnEditCommit(
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
                                    "UPDATE book_group SET published_date=? WHERE isbn=?");

                            statement.setString(1, modelBookTable.getPublished_date());
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

    @FXML
    public void deleteBook(){
        ModelBookTable modelTable = table.getSelectionModel().getSelectedItem();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM book_group WHERE isbn=?");
            String isbn = modelTable.getIsbn();
            preparedStatement.setString(1, isbn);
            preparedStatement.executeUpdate();

            oblist.remove(table.getSelectionModel().getSelectedIndex());
            table.getSelectionModel().clearSelection();
//            table.getItems().clear();
            table.getItems().addAll(oblist);
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}
