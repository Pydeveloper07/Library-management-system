package table;

import WindowLoader.WindowLoader;
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

public class LibrariansTableController implements Initializable {

    @FXML
    private TableView<LibrariansModelTable> table;
    @FXML
    private TableColumn<LibrariansModelTable, String> col_id;
    @FXML
    private TableColumn<LibrariansModelTable, String> col_name;
    @FXML
    private TableColumn<LibrariansModelTable, String> col_surname;
    @FXML
    private TableColumn<LibrariansModelTable, String> col_contact_num;
    @FXML
    private TableColumn<LibrariansModelTable, String> col_email;
    @FXML
    private TextField filter;
    private WindowLoader windowLoader = new WindowLoader().getWindowLoader();
    @FXML
    private void addNewLibrarianWindow(){
        windowLoader.loadAddNewLibrarianWindow();
    }


    private DBConnector connector = new DBConnector().getConnector();

    ObservableList<LibrariansModelTable> oblist = FXCollections.observableArrayList();

    PreparedStatement preparedStatement;
    Connection connection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            connection = connector.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery(
                    "SELECT librarian_id, first_name, last_name, contact_number, email FROM librarian");
            while (resultSet.next()) {
                oblist.add(new LibrariansModelTable(
                        resultSet.getString("librarian_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
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
                new EventHandler<TableColumn.CellEditEvent<LibrariansModelTable, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<LibrariansModelTable, String> t) {
                        LibrariansModelTable modelTable = table.getSelectionModel().getSelectedItem();
                        ((LibrariansModelTable) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setName(t.getNewValue());
                        try {
                            connection = connector.getConnection();
                            PreparedStatement statement = connection.prepareStatement(
                                    "UPDATE librarian SET first_name=? WHERE librarian_id=?");

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
                new EventHandler<TableColumn.CellEditEvent<LibrariansModelTable, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<LibrariansModelTable, String> t) {
                        LibrariansModelTable modelTable = table.getSelectionModel().getSelectedItem();
                        ((LibrariansModelTable) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setSurname(t.getNewValue());
                        try {
                            connection = connector.getConnection();
                            PreparedStatement statement = connection.prepareStatement(
                                    "UPDATE librarian SET last_name=? WHERE librarian_id=?");
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

        col_contact_num.setCellValueFactory(new PropertyValueFactory<>("contact_num"));
        col_contact_num.setCellFactory(TextFieldTableCell.forTableColumn());
        col_contact_num.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<LibrariansModelTable, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<LibrariansModelTable, String> t) {
                        LibrariansModelTable modelTable = table.getSelectionModel().getSelectedItem();
                        ((LibrariansModelTable) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setContact_num(t.getNewValue());
                        try {
                            connection = connector.getConnection();
                            PreparedStatement statement = connection.prepareStatement(
                                    "UPDATE librarian SET contact_number=? WHERE librarian_id=?");
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
                new EventHandler<TableColumn.CellEditEvent<LibrariansModelTable, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<LibrariansModelTable, String> t) {
                        LibrariansModelTable modelTable = table.getSelectionModel().getSelectedItem();
                        ((LibrariansModelTable) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setEmail(t.getNewValue());
                        try {
                            connection = connector.getConnection();
                            PreparedStatement statement = connection.prepareStatement(
                                    "UPDATE librarian SET email=? WHERE librarian_id=?");
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

        FilteredList<LibrariansModelTable> filteredData = new FilteredList<>(oblist, b -> true);
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

        SortedList<LibrariansModelTable> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }
    @FXML
    public void deleteLibrarian(){
        LibrariansModelTable modelTable = table.getSelectionModel().getSelectedItem();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM librarian WHERE librarian_id=?");
            String id = modelTable.getId();
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

}
