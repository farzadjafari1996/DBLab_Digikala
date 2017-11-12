package com.dblab;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

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
                        ResultSet resultSet = connection.createStatement().executeQuery(sqlHandler.select("digiLab.users", new String[]{"state"}, new String[]{"chatID = " + Long.toString(chatID)}));
                        int length = 0;
                        resultSet.last();
                        length = resultSet.getRow();
                        resultSet.beforeFirst();
                        if(length == 0) {
                            state.setCurrentState(StateNode.StateName.Start);//set initial state
                            //insert new row
                            connection.createStatement().execute(sqlHandler.insert("digiLab.users", new String[]{"chatID, state"}, new String[]{Long.toString(chatID), StateNode.enum2string(StateNode.StateName.Start)}));
                            state.setAction("/start");
                            state.setCurrentState(StateNode.StateName.Start);
                            SendMessage request = null;
                            request = new SendMessage(chatID,"سلام به بات دیجی کالا خوش آمدید !")
                                    .parseMode(ParseMode.HTML)
                                    .disableWebPagePreview(true)
                                    .disableNotification(true)
                                    .replyToMessageId(0);
                            SendResponse sendResponse = bot.execute(request);
                            boolean ok = sendResponse.isOk();
                            Message message = sendResponse.message();
                        }
                        else {
                            resultSet.next();
                            state.setCurrentState(stateNode.string2enum(resultSet.getString("state")));//set state that taken from db
                            state.setAction(update.message().text());
                            state.update(connection, bot);
                        }
                    }catch (SQLException e){e.printStackTrace();};
                }
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        });
    };
}
