package com.dblab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Farzad on 10/22/2017.
 */
public class JdbcLogin {
    public String Login;
    public String MotDePasse;
    private boolean Logged = false;

    public Connection StartBdd(){
        String driverName = "com.mysql.jdbc.Driver";
        try{
            Class.forName(driverName); // here is the ClassNotFoundException
        }catch (ClassNotFoundException e){e.printStackTrace();}

        String serverName = "localhost";
        String mydatabase = "digiLab";
        String url = "jdbc:mysql://" + serverName + "/" + mydatabase;

        String username = "digiLab";
        String password = "12345";
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        }catch (SQLException e){e.printStackTrace();}
        return null;
    }
}
