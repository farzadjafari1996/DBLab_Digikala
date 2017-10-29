package com.dblab;

import com.pengrad.telegrambot.TelegramBot;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        // write your code here
        TelegramBot bot = new TelegramBot("444383849:AAHL2WwNZrGYuZDu3jGORLP5kbSOSJWzO2I");

        JdbcLogin jdbcLogin = new JdbcLogin();
        Connection connection = jdbcLogin.StartBdd();
        ActionListener actionListener = new ActionListener();
        System.out.println("before execute");
        actionListener.execute(bot, connection);
        System.out.println("after execute");
    }
}
