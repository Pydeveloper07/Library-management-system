package table;

import WindowLoader.WindowLoader;
import dataBase.DBConnector;
import form.blockStudentForm.BlockController;
import form.chargeStudentForm.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class StudentTableController implements Initializable {

    @FXML
    private TableView<ModelTable> table;
    @FXML
    private TableColumn<ModelTable, String> col_id;
    @FXML
    private TableColumn<ModelTable, String> col_name;
    @FXML
    private TableColumn<ModelTable, String> col_surname;
    @FXML
    private TableColumn<ModelTable, String> col_faculty;
    @FXML
    private TableColumn<ModelTable, String> col_contact_num;
    @FXML
    private TableColumn<ModelTable, String> col_email;
    @FXML
    private TextField filter;
    private WindowLoader windowLoader = new WindowLoader().getWindowLoader();
    @FXML
    private void addNewUserWindow(){
        windowLoader.loadAddNewUserWindow();
    }

    private DBConnector connector = new DBConnector().getConnector();

    ObservableList<ModelTable> oblist = FXCollections.observableArrayList();

    PreparedStatement preparedStatement;
    Connection connection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            connection = connector.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM students");
            while (resultSet.next()) {
                oblist.add(new ModelTable(
                        resultSet.getString("student_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("faculty"),
                        resultSet.getString("contact_number"),
                        resultSet.getString("email")));
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
        table.setEditable(true);
        table.setItems(oblist);

        FilteredList<ModelTable> filteredData = new FilteredList<>(oblist, b -> true);
        filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String upperCaseFilter = newValue.toUpperCase();

                if (person.getId().toUpperCase().indexOf(upperCaseFilter) != -1 ) {
                    return true; // Filter matches first name.
                } else if (person.getName().toUpperCase().indexOf(upperCaseFilter) != -1) {
                    return true; // Filter matches last name.
                }
                else if (person.getSurname().toUpperCase().indexOf(upperCaseFilter)!=-1)
                    return true;
                else
                    return false;
            });
        });

        SortedList<ModelTable> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }

    @FXML
    public void deleteUser(){
        ModelTable modelTable = table.getSelectionModel().getSelectedItem();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM students WHERE student_id=?");
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

    @FXML
    public void blockUser(){
        ModelTable modelTable = table.getSelectionModel().getSelectedItem();
        BlockController.setUserId(modelTable.getId());
        windowLoader.loadBlockedStudentForm();
    }
    @FXML
    public void chargeUser(){
        ModelTable modelTable = table.getSelectionModel().getSelectedItem();
        Controller.setUserId(modelTable.getId());
        windowLoader.loadChargeStudentForm();
    }
}
