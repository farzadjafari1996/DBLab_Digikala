package com.dblab;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
                        int chatID = update.message().messageId().intValue();
                        state.setUserID(chatID);
                        String action = update.message().text();
                        state.setAction(action);
                        System.out.println("zereshkkkkkkk !!!!");
                        ResultSet resultSet = connection.createStatement().executeQuery("select state from base(chatID, state) where chatID = " + chatID);
                        if(resultSet.getRow() == 0)
                            state.setCurrentState(StateNode.StateName.Start);//set initial state
                        else {
                            resultSet.next();
                            state.setCurrentState(stateNode.string2enum(resultSet.getString("state")));//set state that taken from db
                        }
                        System.out.println("1111");
                        state.update(connection);
                        System.out.println("2222");
                        state.sendMessage(bot);
                        //connection.createStatement().execute("insert into base(txt) values (\""+ update.message().text()+"\")");
                    }catch (SQLException e){e.printStackTrace();};
                }
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        });
    };
}
