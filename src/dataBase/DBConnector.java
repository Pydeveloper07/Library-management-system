package dataBase;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static File f = new File("src/dataBase/lms.sql");
    private static String absolutePath = f.getAbsolutePath();
    private static final String URL = "jdbc:derby:" + getAbsolutePath();
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
    private static String getAbsolutePath(){
        int length = absolutePath.length();
        return absolutePath.substring(0, length - 4);
    }
}
