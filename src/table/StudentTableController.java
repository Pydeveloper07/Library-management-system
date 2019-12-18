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
import java.sql.*;
import java.util.ResourceBundle;

public class StudentTableController implements Initializable {

    @FXML
    private TableView<ModelTable> table;
    @FXML
    private TableColumn<ModelTable,String> col_id;
    @FXML
    private TableColumn<ModelTable,String> col_name;
    @FXML
    private TableColumn<ModelTable,String> col_surname;
    @FXML
    private TableColumn<ModelTable,String> col_faculty;
    @FXML
    private TableColumn<ModelTable,String> col_contact_num;
    @FXML
    private TableColumn<ModelTable,String> col_email;
    @FXML
    private TableColumn<ModelTable,String> col_pass;
    @FXML
    private Button search;

    private DBConnector connector = new DBConnector().getConnector();

    ObservableList<ModelTable> oblist = FXCollections.observableArrayList();

    PreparedStatement preparedStatement;
    Connection connection;
    public void search(){
        try
        {
            connection = connector.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE students SET first_name=? WHERE students.student_id=?");

            statement.setString(1, "John");
            statement.setString(2, "U1810001");
            statement.executeUpdate();
            statement.close();
            connection.close();
        }
       catch(SQLException ex){
        ex.printStackTrace();
       }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {



        try {
            connection = connector.getConnection();
            ResultSet resultSet=connection.createStatement().executeQuery("SELECT * FROM students");
            while (resultSet.next()){
                oblist.add(new ModelTable(
                        resultSet.getString("student_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("faculty"),
                        resultSet.getString("contact_number"),
                        resultSet.getString("email"),
                        resultSet.getString("password_code")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));


        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_name.setCellFactory(TextFieldTableCell.forTableColumn());
        col_name.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ModelTable, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<ModelTable, String> t) {
                        ModelTable modelTable = table.getSelectionModel().getSelectedItem();
                        ((ModelTable) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setName(t.getNewValue());
                        try {
                            connection = connector.getConnection();
                            PreparedStatement statement = connection.prepareStatement(
                                    "UPDATE students SET first_name=? WHERE student_id=?");

                            statement.setString(1, modelTable.getName());
                            statement.setString(2, modelTable.getId());
                            statement.executeUpdate();
                            statement.close();
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );



        col_surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        col_surname.setCellFactory(TextFieldTableCell.forTableColumn());
        col_surname.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ModelTable, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<ModelTable, String> t) {
                        ModelTable modelTable = table.getSelectionModel().getSelectedItem();
                        ((ModelTable) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setSurname(t.getNewValue());
                        try {
                            connection = connector.getConnection();
                            PreparedStatement statement = connection.prepareStatement(
                                    "UPDATE students SET last_name=? WHERE student_id=?");
                            statement.setString(1, modelTable.getSurname());
                            statement.setString(2, modelTable.getId());
                            statement.executeUpdate();
                            statement.close();
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        col_faculty.setCellValueFactory(new PropertyValueFactory<>("faculty"));
        col_faculty.setCellFactory(TextFieldTableCell.forTableColumn());
        col_faculty.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ModelTable, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<ModelTable, String> t) {
                            ModelTable modelTable = table.getSelectionModel().getSelectedItem();
                            ((ModelTable) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            ).setFaculty(t.getNewValue());
                            try {
                                connection = connector.getConnection();
                                PreparedStatement statement = connection.prepareStatement(
                                        "UPDATE students SET faculty=? WHERE student_id=?");
                                statement.setString(1, modelTable.getFaculty());
                                statement.setString(2, modelTable.getId());
                                statement.executeUpdate();
                                statement.close();
                                connection.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
        );

        col_contact_num.setCellValueFactory(new PropertyValueFactory<>("contact_num"));
        col_contact_num.setCellFactory(TextFieldTableCell.forTableColumn());
        col_contact_num.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ModelTable, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<ModelTable, String> t) {
                        ModelTable modelTable = table.getSelectionModel().getSelectedItem();
                        ((ModelTable) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setContact_num(t.getNewValue());
                        try {
                            connection = connector.getConnection();
                            PreparedStatement statement = connection.prepareStatement(
                                    "UPDATE students SET contact_number=? WHERE student_id=?");
                            statement.setString(1, modelTable.getContact_num());
                            statement.setString(2, modelTable.getId());
                            statement.executeUpdate();
                            statement.close();
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_email.setCellFactory(TextFieldTableCell.forTableColumn());
        col_email.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ModelTable, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<ModelTable, String> t) {
                        ModelTable modelTable = table.getSelectionModel().getSelectedItem();
                        ((ModelTable) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setEmail(t.getNewValue());
                        try {
                            connection = connector.getConnection();
                            PreparedStatement statement = connection.prepareStatement(
                                    "UPDATE students SET email=? WHERE student_id=?");
                            statement.setString(1, modelTable.getEmail());
                            statement.setString(2, modelTable.getId());
                            statement.executeUpdate();
                            statement.close();
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        col_pass.setCellValueFactory(new PropertyValueFactory<>("password"));
        col_pass.setCellFactory(TextFieldTableCell.forTableColumn());
        col_pass.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ModelTable, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<ModelTable, String> t) {
                        ModelTable modelTable = table.getSelectionModel().getSelectedItem();
                        ((ModelTable) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setPassword(t.getNewValue());
                        try {
                            connection = connector.getConnection();
                            PreparedStatement statement = connection.prepareStatement(
                                    "UPDATE students SET password_code=? WHERE student_id=?");
                            statement.setString(1, modelTable.getPassword());
                            statement.setString(2, modelTable.getId());
                            statement.executeUpdate();
                            statement.close();
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );


        table.setEditable(true);
        table.setItems(oblist);
    }
}
