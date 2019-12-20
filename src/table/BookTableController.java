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
    private TableColumn<ModelBookTable,String> col_descrip;
    @FXML
    private TableColumn<ModelBookTable,String> col_quantity;
    @FXML
    private TableColumn<ModelBookTable,String> col_available;
    @FXML
    private Button search;

    private DBConnector connector = new DBConnector().getConnector();

    ObservableList<ModelBookTable> oblist = FXCollections.observableArrayList();

    PreparedStatement preparedStatement;
    Connection connection;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            connection = connector.getConnection();
            ResultSet resultSet=connection.createStatement().executeQuery("SELECT * FROM book_group");
            while (resultSet.next()){
                oblist.add(new ModelBookTable(
                        resultSet.getString("isbn"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("descrip"),
                        resultSet.getString("quantity"),
                        resultSet.getString("available")
                        ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));


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



        col_author.setCellValueFactory(new PropertyValueFactory<>("author"));
//        col_surname.setCellFactory(TextFieldTableCell.forTableColumn());
//        col_surname.setOnEditCommit(
//                new EventHandler<TableColumn.CellEditEvent<ModelStudentTable, String>>() {
//                    @Override
//                    public void handle(TableColumn.CellEditEvent<ModelStudentTable, String> t) {
//                        ModelStudentTable modelStudentTable = table.getSelectionModel().getSelectedItem();
//                        ((ModelStudentTable) t.getTableView().getItems().get(
//                                t.getTablePosition().getRow())
//                        ).setSurname(t.getNewValue());
//                        try {
//                            connection = connector.getConnection();
//                            PreparedStatement statement = connection.prepareStatement(
//                                    "UPDATE students SET last_name=? WHERE student_id=?");
//                            statement.setString(1, modelStudentTable.getSurname());
//                            statement.setString(2, modelStudentTable.getId());
//                            statement.executeUpdate();
//                            statement.close();
//                            connection.close();
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//        );

        col_descrip.setCellValueFactory(new PropertyValueFactory<>("descrip"));
//        col_faculty.setCellFactory(TextFieldTableCell.forTableColumn());
//        col_faculty.setOnEditCommit(
//                new EventHandler<TableColumn.CellEditEvent<ModelStudentTable, String>>() {
//                    @Override
//                    public void handle(TableColumn.CellEditEvent<ModelStudentTable, String> t) {
//                        ModelStudentTable modelStudentTable = table.getSelectionModel().getSelectedItem();
//                        ((ModelStudentTable) t.getTableView().getItems().get(
//                                t.getTablePosition().getRow())
//                        ).setFaculty(t.getNewValue());
//                        try {
//                            connection = connector.getConnection();
//                            PreparedStatement statement = connection.prepareStatement(
//                                    "UPDATE students SET faculty=? WHERE student_id=?");
//                            statement.setString(1, modelStudentTable.getFaculty());
//                            statement.setString(2, modelStudentTable.getId());
//                            statement.executeUpdate();
//                            statement.close();
//                            connection.close();
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//        );

        col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
//        col_contact_num.setCellFactory(TextFieldTableCell.forTableColumn());
//        col_contact_num.setOnEditCommit(
//                new EventHandler<TableColumn.CellEditEvent<ModelStudentTable, String>>() {
//                    @Override
//                    public void handle(TableColumn.CellEditEvent<ModelStudentTable, String> t) {
//                        ModelStudentTable modelStudentTable = table.getSelectionModel().getSelectedItem();
//                        ((ModelStudentTable) t.getTableView().getItems().get(
//                                t.getTablePosition().getRow())
//                        ).setContact_num(t.getNewValue());
//                        try {
//                            connection = connector.getConnection();
//                            PreparedStatement statement = connection.prepareStatement(
//                                    "UPDATE students SET contact_number=? WHERE student_id=?");
//                            statement.setString(1, modelStudentTable.getContact_num());
//                            statement.setString(2, modelStudentTable.getId());
//                            statement.executeUpdate();
//                            statement.close();
//                            connection.close();
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//        );

        col_available.setCellValueFactory(new PropertyValueFactory<>("available"));
//        col_email.setCellFactory(TextFieldTableCell.forTableColumn());
//        col_email.setOnEditCommit(
//                new EventHandler<TableColumn.CellEditEvent<ModelStudentTable, String>>() {
//                    @Override
//                    public void handle(TableColumn.CellEditEvent<ModelStudentTable, String> t) {
//                        ModelStudentTable modelStudentTable = table.getSelectionModel().getSelectedItem();
//                        ((ModelStudentTable) t.getTableView().getItems().get(
//                                t.getTablePosition().getRow())
//                        ).setEmail(t.getNewValue());
//                        try {
//                            connection = connector.getConnection();
//                            PreparedStatement statement = connection.prepareStatement(
//                                    "UPDATE students SET email=? WHERE student_id=?");
//                            statement.setString(1, modelStudentTable.getEmail());
//                            statement.setString(2, modelStudentTable.getId());
//                            statement.executeUpdate();
//                            statement.close();
//                            connection.close();
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//        );


        table.setEditable(true);
        table.setItems(oblist);
    }
}
