package com.dblab;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by Farzad on 10/22/2017.
 */
public class ActionListener {
    public void execute(TelegramBot bot, Connection connection){
        StateNode stateNode = new StateNode();
        bot.setUpdatesListener(new UpdatesListener() {
            @Override
            public int process(List<Update> updates) {
                // process updates
                for (Update update : updates) {
                    try{

                        State state = new State();
                        SQLHandler sqlHandler = new SQLHandler();
                        long chatID = update.message().chat().id();
                        state.setUserID(chatID);
                        String action = update.message().text();
                        state.setAction(action);
                        //ResultSet resultSet = connection.createStatement().executeQuery("select state from base where chatID = " + "'" + chatID + "'");
                        ResultSet resultSet = connection.createStatement().executeQuery(sqlHandler.select("", new String[]{"0"}, new String[]{"0"}));
                        //System.out.println(action);
                        //System.out.println(chatID);
                        if(resultSet.getRow() == 0) {
                            state.setCurrentState(StateNode.StateName.Start);//set initial state
                            //insert new row
                        }
                        else {
                            resultSet.next();
                            state.setCurrentState(stateNode.string2enum(resultSet.getString("state")));//set state that taken from db
                            state.update(connection);
                        }
                        state.sendMessage(bot);
                        //connection.createStatement().execute("insert into base(txt) values (\""+ update.message().text()+"\")");
                    }catch (SQLException e){e.printStackTrace();};
                }
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        });
    };
}
