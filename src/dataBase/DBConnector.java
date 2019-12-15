package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private static final String URL = "jdbc:derby:classpath:dataBase/lms";
    private static final String USERNAME = "java_masters";
    private static final String PASSWORD = "forever";

    private Connection connection = null; // manages connection
    private DBConnector connector = null;
    public Connection getConnection() throws SQLException {
        connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        return connection;
    }
    public DBConnector getConnector(){
        if(connector == null){
            connector = new DBConnector();
        }
        return connector;
    }
}
