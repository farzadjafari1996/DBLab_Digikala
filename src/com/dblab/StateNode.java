package com.dblab;

/**
 * Created by Farzad on 10/23/2017.
 */
public class StateNode {
    enum StateName{
        Start ,
        Help ,
        Menu
    };
    public String enum2string(StateName stateName){
        String finalAnswer = new String();
        if(stateName == StateName.Start)
            finalAnswer = "Start";
        else if(stateName == StateName.Help)
            finalAnswer = "Help";
        else if(stateName == StateName.Menu)
            finalAnswer = "Menu";
        return finalAnswer;
    };
    public StateName string2enum(String string){
        StateName st = StateName.Start;
        if(string == "Start")
            st = StateName.Start;
        else if(string == "Help")
            st = StateName.Help;
        else if(string == "Menu")
            st = StateName.Menu;
        return st;
    };
}
