package com.dblab;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Farzad on 10/22/2017.
 */
public class State {
    long userID;
    StateNode.StateName currentState;
    String action;

    public void setAction(String action) {
        this.action = action;
    }
    public String getAction() {
        return action;
    }
    public void setUserID(long userID){
        this.userID = userID;
    };
    public long getUserID() {
        return userID;
    }
    public void setCurrentState(StateNode.StateName currentState) {
        this.currentState = currentState;
    }
    public StateNode.StateName getCurrentState() {
        return currentState;
    }
    void update(Connection connection){
        if(currentState ==  StateNode.StateName.Start) {
            if (action == "/Help") {
                try {
                    connection.createStatement().executeQuery("update base set state = "+ "'" + new StateNode().enum2string(StateNode.StateName.Help) + "'" +" where chatID = " + userID);
                }catch(SQLException e){e.printStackTrace();};
                currentState = StateNode.StateName.Help;
            }
            else if (action == "/Menu"){
                try {
                    connection.createStatement().executeQuery("update base set state = "+ "'" + new StateNode().enum2string(StateNode.StateName.Menu) + "'" +" where chatID = " + userID);
                }catch(SQLException e){e.printStackTrace();};
                currentState = StateNode.StateName.Menu;
            }
        }
        else if(currentState ==  StateNode.StateName.Help) {
            if (action == "/Help") {
                try {
                    connection.createStatement().executeQuery("update base set state = "+ "'" + new StateNode().enum2string(StateNode.StateName.Help) + "'" +" where chatID = " + userID);
                }catch(SQLException e){e.printStackTrace();};
                currentState = StateNode.StateName.Help;
            }
            else if (action == "/Menu"){
                try {
                    connection.createStatement().executeQuery("update base set state = "+ "'" + new StateNode().enum2string(StateNode.StateName.Menu) + "'" +" where chatID = " + userID);
                }catch(SQLException e){e.printStackTrace();};
                currentState = StateNode.StateName.Menu;
            }
        }
        else if(currentState ==  StateNode.StateName.Menu) {
            if (action == "/Help") {
                try {
                    connection.createStatement().executeQuery("update base set state = "+ "'" + new StateNode().enum2string(StateNode.StateName.Help) + "'" +" where chatID = " + userID);
                }catch(SQLException e){e.printStackTrace();};
                currentState = StateNode.StateName.Help;
            }
            else if (action == "/Menu"){
                try {
                    connection.createStatement().executeQuery("update base set state = "+ "'" + new StateNode().enum2string(StateNode.StateName.Menu) + "'" +" where chatID = " + userID);
                }catch(SQLException e){e.printStackTrace();};
                currentState = StateNode.StateName.Menu;
            }
        }
    };
    void sendMessage(TelegramBot bot){
        if(currentState == StateNode.StateName.Start){
            SendMessage request = new SendMessage(userID, "Salam :D")
                    .parseMode(ParseMode.HTML)
                    .disableWebPagePreview(true)
                    .disableNotification(true)
                    .replyToMessageId(1)
                    .replyMarkup(new ForceReply());

            SendResponse sendResponse = bot.execute(request);
            boolean ok = sendResponse.isOk();
            Message message = sendResponse.message();

        }
        else if(currentState == StateNode.StateName.Help){
            SendMessage request = new SendMessage(userID, "Salam help :D")
                    .parseMode(ParseMode.HTML)
                    .disableWebPagePreview(true)
                    .disableNotification(true)
                    .replyToMessageId(1)
                    .replyMarkup(new ForceReply());

            SendResponse sendResponse = bot.execute(request);
            boolean ok = sendResponse.isOk();
            Message message = sendResponse.message();
        }
        else if(currentState == StateNode.StateName.Menu){
            SendMessage request = new SendMessage(userID, "Salam menu :D")
                    .parseMode(ParseMode.HTML)
                    .disableWebPagePreview(true)
                    .disableNotification(true)
                    .replyToMessageId(1)
                    .replyMarkup(new ForceReply());

            SendResponse sendResponse = bot.execute(request);
            boolean ok = sendResponse.isOk();
            Message message = sendResponse.message();
        }
    };
}