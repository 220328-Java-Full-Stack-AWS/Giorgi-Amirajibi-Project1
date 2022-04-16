package com.revature.connectivity;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {

    private static Connection connection = null;

    private ConnectionManager(){

    }

    public static Connection getConnection(){
        if (connection != null ){
            return connection;
        }
        else {
            Properties properties = new Properties();
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = loader.getResourceAsStream("application.properties");

            try {
                properties.load(inputStream);
                String connectionString = "jdbc:postgresql://" + properties.getProperty("hostname") + ":" + properties.getProperty("port") + "/" + properties.getProperty("dbname");
                connection = DriverManager.getConnection(connectionString,properties.getProperty("username"),properties.getProperty("password"));
                System.out.println("Connection to Database was successful");

                /*
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("status","success");
                */

                return connection;

            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        return connection;
    }

}
