package com.dblab;

/**
 * Created by sana on 10/29/2017.
 */
public class SQLHandler {
    public String select(String tableName, String[] selectedField, String[] condition){
        String selectInstruction = new String("SELECT ");

        for (int i = 0; i < selectedField.length; i++) {
            if (i < selectedField.length - 1) {
                selectInstruction = selectInstruction + selectedField[i] + ", ";
            }else{
                selectInstruction = selectInstruction + selectedField[i] + " FROM " + tableName + " WHERE ";
            }
        }

        for (int i = 0; i < condition.length; i++) {
            if (i < condition.length - 1) {
                selectInstruction = selectInstruction + condition[i] + ", ";
            }else{
                selectInstruction = selectInstruction + condition[i] + ";";
            }
        }
        return selectInstruction;
    }// end of select


    //UPDATE table_name SET column_name = value [, column_name = value ...] [WHERE condition]
    public String update(String tableName, String[] updatedFieldValue, String[] condition){
        String updateInstruction = new String("UPDATE ");
        updateInstruction = updateInstruction + tableName + " SET ";

        for (int i = 0; i < updatedFieldValue.length; i++) {
            if (i < updatedFieldValue.length - 1) {
                updateInstruction = updateInstruction + updatedFieldValue[i] + ", ";
            }else{
                updateInstruction = updateInstruction + updatedFieldValue[i] + " WHERE ";
            }
        }

        for (int i = 0; i < condition.length; i++) {
            if (i < condition.length - 1) {
                updateInstruction = updateInstruction + condition[i] + ", ";
            }else{
                updateInstruction = updateInstruction + condition[i] + ";";
            }
        }
        return updateInstruction;
    }// end of update


    //DELETE FROM table_name [WHERE condition];
    public String delete(String tableName, String[] deletedFieldValue, String[] condition){
        String deleteInstruction = new String("DELETE ");

        for (int i = 0; i < deletedFieldValue.length; i++) {
            if (i < deletedFieldValue.length - 1) {
                deleteInstruction = deleteInstruction + deletedFieldValue[i] + ", ";
            }else{
                deleteInstruction = deleteInstruction + deletedFieldValue[i] + " FROM " + tableName +  " WHERE ";
            }
        }

        for (int i = 0; i < condition.length; i++) {
            if (i < condition.length - 1) {
                deleteInstruction = deleteInstruction + condition[i] + ", ";
            }else{
                deleteInstruction = deleteInstruction + condition[i] + ";";
            }
        }
        return deleteInstruction;
    }// end of delete



    //INSERT INTO table (column1 [, column2, column3 ... ]) VALUES (value1 [, value2, value3 ... ])
    public String insert(String tableName, String[] insertedField, String[] insertedValue){
        String deleteInstruction = new String("INSERT INTO " + tableName + " (");

        for (int i = 0; i < insertedField.length; i++) {
            if (i < insertedField.length - 1) {
                deleteInstruction = deleteInstruction + insertedField[i] + ", ";
            }else{
                deleteInstruction = deleteInstruction + insertedField[i] + ") VALUES (";
            }
        }

        for (int i = 0; i < insertedValue.length; i++) {
            if (i < insertedValue.length - 1) {
                deleteInstruction = deleteInstruction + insertedValue[i] + ", ";
            }else{
                deleteInstruction = deleteInstruction + insertedValue[i] + ");";
            }
        }
        return deleteInstruction;
    }// end of insert
}
