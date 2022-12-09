package com.revature.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static Connection connection;

    private ConnectionManager(){}

    public static Connection getConnection(){
        if(connection == null){
            connect();
        }

        return connection;
    }

    private static void connect(){
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream input = loader.getResourceAsStream("jdbc.properties");
            Properties prop = new Properties();
            prop.load(input);

            StringBuilder connectionString = new StringBuilder();
            connectionString.append("jdbc:postgresql://");
            connectionString.append(prop.getProperty("host"));
            connectionString.append(":");
            connectionString.append(prop.getProperty("port"));
            connectionString.append("/");
            connectionString.append(prop.getProperty("dbname"));
            connectionString.append("?user=");
            connectionString.append(prop.getProperty("username"));
            connectionString.append("&password=");
            connectionString.append(prop.getProperty("password"));

            Class.forName(prop.getProperty("driver"));

            connection = DriverManager.getConnection(connectionString.toString());



        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
