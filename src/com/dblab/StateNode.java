package com.dblab;

/**
 * Created by Farzad on 10/23/2017.
 */
public class StateNode {
    enum StateName{
        Start,
        Help,
        Menu,
        Admin_Start,
        Admin_Help,
        Admin_Menu
    };
    public static String enum2string(StateName stateName){
        String finalAnswer = new String();
        if(stateName.equals(StateName.Start))
            finalAnswer = "Start";
        else if(stateName.equals(StateName.Help))
            finalAnswer = "Help";
        else if(stateName.equals(StateName.Menu))
            finalAnswer = "Menu";
        else if(stateName.equals(StateName.Admin_Start))
            finalAnswer = "Admin_Start";
        else if(stateName.equals(StateName.Admin_Help))
            finalAnswer = "Admin_Help";
        else if(stateName.equals(StateName.Admin_Menu))
            finalAnswer = "Admin_Menu";
        return finalAnswer;
    };
    public StateName string2enum(String string){
        StateName st = StateName.Start;
        if(string.equals("Start"))
            st = StateName.Start;
        else if(string.equals("Help"))
            st = StateName.Help;
        else if(string.equals("Menu"))
            st = StateName.Menu;
        else if(string.equals("Admin_Start"))
            st = StateName.Admin_Start;
        else if(string.equals("Admin_Help"))
            st = StateName.Admin_Help;
        else if(string.equals("Admin_Menu"))
            st = StateName.Admin_Menu;
        return st;
    };
}
