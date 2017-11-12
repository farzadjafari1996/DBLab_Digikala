package com.dblab;

/**
 * Created by Farzad on 10/23/2017.
 */
public class StateNode {
    enum StateName{
        Start,
        Menu,
        Show,
        Admin_Start,
        Admin_Add,
        Admin_Delete
    };
    public static String enum2string(StateName stateName){
        String finalAnswer = new String();
        if(stateName.equals(StateName.Start))
            finalAnswer = "Start";
        else if(stateName.equals(StateName.Menu))
            finalAnswer = "Menu";
        else if(stateName.equals(StateName.Show))
            finalAnswer = "Show";
        else if(stateName.equals(StateName.Admin_Start))
            finalAnswer = "Admin_Start";
        else if(stateName.equals(StateName.Admin_Add))
            finalAnswer = "Admin_Add";
        else if(stateName.equals(StateName.Admin_Delete))
            finalAnswer = "Admin_Delete";
        return finalAnswer;
    };
    public StateName string2enum(String string){
        StateName st = StateName.Start;
        if(string.equals("Start"))
            st = StateName.Start;
        else if(string.equals("Menu"))
            st = StateName.Menu;
        else if(string.equals("Show"))
            st = StateName.Show;
        else if(string.equals("Admin_Start"))
            st = StateName.Admin_Start;
        else if(string.equals("Admin_Add"))
            st = StateName.Admin_Add;
        else if(string.equals("Admin_Delete"))
            st = StateName.Admin_Delete;
        return st;
    };
}
