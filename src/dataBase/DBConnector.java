package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnector {

    private static final String URL = "jdbc:derby:classpath:dataBase/lms";
    private static final String USERNAME = "java_masters";
    private static final String PASSWORD = "forever";

    private Connection connection; // manages connection
    private PreparedStatement selectAllPeople;
    private PreparedStatement selectPeopleByLastName;
    private PreparedStatement insertNewPerson;

    public static Connection getConnection() throws SQLException {
        Connection connection= DriverManager.getConnection(URL,USERNAME,PASSWORD);
        return connection;
    }

}
