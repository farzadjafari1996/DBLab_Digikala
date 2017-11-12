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
    public void sendMessage(TelegramBot bot){
        SendMessage request = null;
        userMessageSender(request);
        SendResponse sendResponse = bot.execute(request);
        boolean ok = sendResponse.isOk();
        Message message = sendResponse.message();
    };
    void update(Connection connection){
        adminStateMachineHandler(connection);
        userStateMachineHandler(connection);
    };

    //admin message sender

    //user message sender
    private void userMessageSender(SendMessage request){
        if(currentState.equals(StateNode.StateName.Start)){
            if(this.getAction().equals("/start")) {
                request = new SendMessage(userID, "سلام ادمین به بات دیجی کالا خوش آمدید !")
                        .parseMode(ParseMode.HTML)
                        .disableWebPagePreview(true)
                        .disableNotification(true)
                        .replyToMessageId(0);
            }
            else if(this.getAction().equals("/help")) {
                request = new SendMessage(userID, "در این بات شما میتوانید در سایت دیجی کالا گردش کنید و محصولات متفاوت را مقایسه. و محصول اضافه کنید.")
                        .parseMode(ParseMode.HTML)
                        .disableWebPagePreview(true)
                        .disableNotification(true)
                        .replyToMessageId(0);            }
            else{
                request = new SendMessage(userID, "دستور اشتباه وارد کرده اید. لطفا دستور درست را وارد کنید.")
                        .parseMode(ParseMode.HTML)
                        .disableWebPagePreview(true)
                        .disableNotification(true)
                        .replyToMessageId(0);
            }
        }
        else if(currentState.equals(StateNode.StateName.Help)){
            if(this.getAction().equals("/help")) {
                request = new SendMessage(userID, "در این بات شما میتوانید در سایت دیجی کالا گردش کنید و محصولات متفاوت را مقایسه کنید.")
                        .parseMode(ParseMode.HTML)
                        .disableWebPagePreview(true)
                        .disableNotification(true)
                        .replyToMessageId(0);
            }
            else if(this.getAction().equals("/menu")) {
                request = new SendMessage(userID, "هر دستوری را که مایلید انتخاب کنید تا وارد مراحل بعدی شوید." + "\n 1.خرید \n 2.مقایسه \n 3.جستجو")
                        .parseMode(ParseMode.HTML)
                        .disableWebPagePreview(true)
                        .disableNotification(true)
                        .replyToMessageId(0);
            }

            else{
                request = new SendMessage(userID, "دستور اشتباه وارد کرده اید. لطفا دستور درست را وارد کنید.")
                        .parseMode(ParseMode.HTML)
                        .disableWebPagePreview(true)
                        .disableNotification(true)
                        .replyToMessageId(0);
            }
        }
        else if(currentState == StateNode.StateName.Menu){
            if(this.getAction().equals("/menu")) {
                request = new SendMessage(userID, "هر دستوری را که مایلید انتخاب کنید تا وارد مراحل بعدی شوید." + "\n 1.خرید \n 2.مقایسه \n 3.جستجو")
                        .parseMode(ParseMode.HTML)
                        .disableWebPagePreview(true)
                        .disableNotification(true)
                        .replyToMessageId(0);
            }
            else{
                request = new SendMessage(userID, "دستور اشتباه وارد کرده اید. لطفا دستور درست را وارد کنید.")
                        .parseMode(ParseMode.HTML)
                        .disableWebPagePreview(true)
                        .disableNotification(true)
                        .replyToMessageId(0);
            }
        }
        else{
            request = new SendMessage(userID, "یا للعجبا.")
                    .parseMode(ParseMode.HTML)
                    .disableWebPagePreview(true)
                    .disableNotification(true)
                    .replyToMessageId(0);
        }
    }

    //user state machine handler
    private void userStateMachineHandler(Connection connection)
    {
        SQLHandler sqlHandler = new SQLHandler();
        if(currentState.equals(StateNode.StateName.Start)) {
            if (action.equals("/help")) {
                try {
                    connection.createStatement().execute(sqlHandler.update("digiLab.users", new String[]{"state = 'Help'"}, new String[]{"chatID = " + Long.toString(this.getUserID())}));;
                    this.setCurrentState(StateNode.StateName.Help);
                }catch(SQLException e){e.printStackTrace();};
                currentState = StateNode.StateName.Help;
            }
        }
        else if(currentState.equals(StateNode.StateName.Help)) {
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
    private void adminStateMachineHandler(Connection connection){
        SQLHandler sqlHandler = new SQLHandler();
        if(currentState.equals(StateNode.StateName.Admin_Start)) {
            if (action.equals("/admin_help")) {
                try {
                    connection.createStatement().execute(sqlHandler.update("digiLab.users", new String[]{"state = 'Admin_Help'"}, new String[]{"chatID = " + Long.toString(this.getUserID())}));;
                    this.setCurrentState(StateNode.StateName.Admin_Help);
                }catch(SQLException e){e.printStackTrace();};
                currentState = StateNode.StateName.Admin_Help;
            }
            //TODO add reset state
        }
        else if(currentState.equals(StateNode.StateName.Admin_Help)) {
            if (action.equals("/admin_menu")){
                try {
                    connection.createStatement().execute(sqlHandler.update("digiLab.users", new String[]{"state = 'Admin_Menu'"}, new String[]{"chatID = " + Long.toString(this.getUserID())}));;
                    this.setCurrentState(StateNode.StateName.Admin_Menu);
                }catch(SQLException e){e.printStackTrace();};
                currentState = StateNode.StateName.Admin_Menu;
            }
            //TODO add reset state
        }
        //TODO add menu state
    };
}