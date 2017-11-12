package com.dblab;

/**
 * Created by Farzad on 10/23/2017.
 */
public class StateNode {
    enum StateName{
        Start,
        Menu,
        Show,
        Add,
        Delete
    };
    public static String enum2string(StateName stateName){
        String finalAnswer = new String();
        if(stateName.equals(StateName.Start))
            finalAnswer = "Start";
        else if(stateName.equals(StateName.Menu))
            finalAnswer = "Menu";
        else if(stateName.equals(StateName.Show))
            finalAnswer = "Show";
        else if(stateName.equals(StateName.Add))
            finalAnswer = "Add";
        else if(stateName.equals(StateName.Delete))
            finalAnswer = "Delete";
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
        else if(string.equals("Add"))
            st = StateName.Add;
        else if(string.equals("Delete"))
            st = StateName.Delete;
        return st;
    };
}
