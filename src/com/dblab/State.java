package com.dblab;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

import java.sql.Connection;
import java.sql.ResultSet;
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

    void update(Connection connection, TelegramBot bot){
        SQLHandler sqlHandler = new SQLHandler();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(sqlHandler.select("admins",new String[]{"*"},new String[]{"chatID = " + this.getUserID()}));
            resultSet.last();
            if (resultSet.getRow() != 0)
                adminStateMachineHandler(connection, bot);
            else
                userStateMachineHandler(connection, bot);
        }catch (SQLException e){e.printStackTrace();};
    };

    //user state machine handler
    private void userStateMachineHandler(Connection connection, TelegramBot bot)
    {
        SQLHandler sqlHandler = new SQLHandler();
        SendMessage request = null;
        if(currentState.equals(StateNode.StateName.Start)) {
            if (action.equals("/help")) {
                try {
                    request = new SendMessage(userID,"سلام ادمین به بات دیجی کالا خوش آمدید ! لطفا از منوی زیر عمل مورد نظر خود را انتخاب کنید." + "\n" + "1. اضافه کردن : /admin_add" + "\n" + "2. حذف کردن : /admin_delete")
                            .parseMode(ParseMode.HTML)
                            .disableWebPagePreview(true)
                            .disableNotification(true)
                            .replyToMessageId(0);

                    SendResponse sendResponse = bot.execute(request);
                    boolean ok = sendResponse.isOk();
                    Message message = sendResponse.message();

                    connection.createStatement().execute(sqlHandler.update("digiLab.users", new String[]{"state = 'Help'"}, new String[]{"chatID = " + Long.toString(this.getUserID())}));;
                    this.setCurrentState(StateNode.StateName.Menu);
                }catch(SQLException e){e.printStackTrace();};
                currentState = StateNode.StateName.Menu;
            }
        }
        else if(currentState.equals(StateNode.StateName.Menu)) {
            if (action.equals("/menu")){
                try {
                    connection.createStatement().execute(sqlHandler.update("digiLab.users", new String[]{"state = 'Menu'"}, new String[]{"chatID = " + Long.toString(this.getUserID())}));;
                    this.setCurrentState(StateNode.StateName.Menu);
                }catch(SQLException e){e.printStackTrace();};
                currentState = StateNode.StateName.Menu;
            }
        }
        else if(currentState.equals(StateNode.StateName.Menu)) {
            //TODO add menu and other items
        }
    }

    //admin state machine handler
    private void adminStateMachineHandler(Connection connection, TelegramBot bot){
        SQLHandler sqlHandler = new SQLHandler();
        if(currentState.equals(StateNode.StateName.Admin_Start)) {
            if (action.equals("/admin_help")) {
                try {
                    connection.createStatement().execute(sqlHandler.update("digiLab.users", new String[]{"state = 'Admin_Help'"}, new String[]{"chatID = " + Long.toString(this.getUserID())}));;
                    this.setCurrentState(StateNode.StateName.Admin_Add);
                }catch(SQLException e){e.printStackTrace();};
                currentState = StateNode.StateName.Admin_Add;
            }
            //TODO add reset state
        }
        else if(currentState.equals(StateNode.StateName.Admin_Delete)) {
            if (action.equals("/admin_menu")){
                try {
                    connection.createStatement().execute(sqlHandler.update("digiLab.users", new String[]{"state = 'Admin_Menu'"}, new String[]{"chatID = " + Long.toString(this.getUserID())}));;
                    this.setCurrentState(StateNode.StateName.Admin_Delete);
                }catch(SQLException e){e.printStackTrace();};
                currentState = StateNode.StateName.Admin_Delete;
            }
            //TODO add reset state
        }
        //TODO add menu state
    };
}