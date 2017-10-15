package com.dblab;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
	// write your code here
        TelegramBot bot = new TelegramBot("444383849:AAHL2WwNZrGYuZDu3jGORLP5kbSOSJWzO2I");
        GetUpdates getUpdates = new GetUpdates().limit(100).offset(0).timeout(0);
        JdbcLogin jdbcLogin = new JdbcLogin();
        Connection connection = jdbcLogin.StartBdd();
        // async
        bot.execute(getUpdates, new Callback<GetUpdates, GetUpdatesResponse>() {
            @Override
            public void onResponse(GetUpdates request, GetUpdatesResponse response) {
                List<Update> updates = response.updates();
                for (Update update : updates) {
                    try{
                        connection.createStatement().execute("insert into base(txt) values (\""+ update.message().text()+"\")");
                    }catch (SQLException e){e.printStackTrace();};
                }
            }

            @Override
            public void onFailure(GetUpdates request, IOException e) {

            }
        });
    }

    public static class JdbcLogin {
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
}
