package com.fmanda.autopartdashboard.helper;

import android.database.Cursor;
import android.util.Log;

import com.fmanda.autopartdashboard.model.BaseModel;
import com.fmanda.autopartdashboard.model.TableField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.lang.Class;
/**
 * Created by fmanda on 08/02/17.
 */

public class ModelHelper {
    public ModelHelper() {
    }

    public static List<Field> getFields(Object obj){
        List<Field> fields = new ArrayList<Field>();

        Class<?> current = obj.getClass();
        while (current.getSuperclass() != null) {
            for (Field field : current.getDeclaredFields()) {
                fields.add(field);
            }
            current = current.getSuperclass();
            if (!BaseModel.class.isAssignableFrom(current)) { break; }
        }
        return fields;
    }

    public static List<Field> getDBFields(BaseModel obj){
        List<Field> fields = new ArrayList<Field>();

        Class<?> current = obj.getClass();
        while (current.getSuperclass() != null) {
            for (Field field : current.getDeclaredFields()) {
                if (field.isAnnotationPresent(TableField.class)) fields.add(field);
            }
            current = current.getSuperclass();
            if (!BaseModel.class.isAssignableFrom(current)) { break; }
        }
        return fields;
    }

    public static List<String> getDBFieldNames(BaseModel obj){
        List<String> fields = new ArrayList<String>();

        for (Field field : ModelHelper.getDBFields(obj)) {
            fields.add(field.getName());
        }
        return fields;
    }

    public static Object getFieldValue(Field field, BaseModel obj){
        Object value = null;
        field.setAccessible(true);
        try {
            value = field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getQuotValue(Field field, BaseModel obj){
        Log.d("test", field.getName());
        Object value = getFieldValue(field, obj);
        String str = "";
        if (field.getType().isAssignableFrom(Integer.class) || field.getType().isAssignableFrom(int.class))  {
            str = "0";
            if (value != null) str = String.valueOf((Integer) value);
        }else if (field.getType().isAssignableFrom(Double.class)
                || field.getType().isAssignableFrom(double.class)
                || field.getType().isAssignableFrom(Float.class)) {
            str = "0.0";
            if (value != null) str = String.valueOf((double) value);
        }else if (field.getType().isAssignableFrom(String.class)){
            if (value != null) str = (String) value;
            str = "'" + str + "'";
        }else if (field.getType().isAssignableFrom(Date.class)) {
            str = "0";
            if (value != null) {
                Date dt = (Date) value;
                str = String.valueOf(dt.getTime());
            }
        }
        return str;
    }

    public static String getReadValue(Field field, BaseModel obj){
        Log.d("test", field.getName());
        Object value = getFieldValue(field, obj);
        String str = "";
        if (field.getType().isAssignableFrom(Integer.class) || field.getType().isAssignableFrom(int.class))  {
            str = "0";
            if (value != null) str = String.valueOf((Integer) value);
        }else if (field.getType().isAssignableFrom(Double.class)
                || field.getType().isAssignableFrom(double.class)
                || field.getType().isAssignableFrom(Float.class)) {
            str = "0.0";
            if (value != null) str = String.valueOf((Double) value);
        }else if (field.getType().isAssignableFrom(String.class)){
            if (value != null) str = (String) value;
            str = "'" + str + "'";
        }else if (field.getType().isAssignableFrom(Date.class)) {
            if (value != null) {
                Date dt = (Date) value;
                str = dt.toString();
            }
        }
        return str;
    }




    private static String getFieldName(Field field){
        String fieldName = "";
        if (field.isAnnotationPresent(TableField.class)) {
            fieldName = field.getAnnotation(TableField.class).fieldName();
        }
        if (fieldName == "") fieldName = field.getName();
        return fieldName;
    }

    private static Field getFieldByName(BaseModel obj, String fieldName){
        Field returnfield = null;
        String objFieldName;
        List<Field> fields = getDBFields(obj);
        for (Field field : fields) {
            objFieldName = field.getAnnotation(TableField.class).fieldName();
            if (objFieldName == "") objFieldName = field.getName();

            if (objFieldName.toLowerCase().equals(fieldName.toLowerCase())){
                returnfield = field;
                return returnfield;
            }
        }
        return returnfield;
    }

    private static String getInsertFields(BaseModel obj){
        String str = "";
        List<Field> fields = getDBFields(obj);
        for (Field field : fields) {
            if (field.getName().toLowerCase() == "id") continue;
            if (str != "") str = str + ",";
            str = str + getFieldName(field);
        }
        return str;
    }

    private static String getInsertValues(BaseModel obj){
        String str = "";
        List<Field> fields = getDBFields(obj);
        for (Field field : fields) {
            if (field.getName().toLowerCase() == "id") continue;
            if (str != "") str = str + ",";
            str = str + getQuotValue(field, obj);
        }
        return str;
    }

    public static String generateSQL(BaseModel obj){
        if (obj.getId() <= 0) {
            return generateSQLInsert(obj);
        }else{
            return generateSQLUpdate(obj);
        }

    }

    public static String generateSQLInsert(BaseModel obj){
        String str ="insert into " + obj.getTableName() + "(";
        str += getInsertFields(obj) + ") values(";
        str += getInsertValues(obj) + ");";
        return str;
    }

    public static String generateSQLUpdate(BaseModel obj){
        String str = "";

        List<Field> fields = getDBFields(obj);
        for (Field field : fields) {
            if (field.getName().toLowerCase().equals("id")) continue;
            if (!str.equals("")) str += ", ";
            str += getFieldName(field) + " = " + getQuotValue(field, obj);
        }
        str = "update " + obj.getTableName() + " set " + str;
        str += " where id = " + String.valueOf(obj.getId()) + ";";
        return str;
    }

    public static String generateMetaData(BaseModel obj){
        String str = "";
        for (Field field : getDBFields(obj)) {
            if (str!="") str+= ",\n ";
            str += getFieldName(field) + " ";

            if (field.getName().toLowerCase().equals("id")){
                str += "integer not null primary key";
            }
            else if ((field.getType().isAssignableFrom(Integer.class) || field.getType().isAssignableFrom(Double.class))
                    || (field.getType().isAssignableFrom(Float.class) || field.getType().isAssignableFrom(int.class))) {
                str += "real default 0";
            }else if (field.getType().isAssignableFrom(Date.class)) {
                str += "real not null default 0";
            }else{
                str += "text";
            }

        }

        str = "create table " + obj.getTableName() + "( \n" + str + "\n);";
        Log.d(obj.getTableName(),str);
        return str;
    }

    public static String generateDropMetaData(BaseModel obj){
        return "drop table if exists " + obj.getTableName() + ";";
    }

    public static void copyObject(BaseModel source, BaseModel into){
        List<Field> fields = getFields(source);
        for (Field field : fields) {
            field.setAccessible(true);

            try {
                field.set(into, field.get(source));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    public static boolean loadFromCursor(BaseModel obj, Cursor cursor){
        Boolean result = false;
        if (cursor.getCount() == 0) return false;
        if (cursor.isAfterLast()) return false;

        for (int i=0; i<cursor.getColumnCount(); i++){
            String fieldName = cursor.getColumnName(i);
            Field field = getFieldByName(obj, fieldName);
            if (field == null) continue;
            field.setAccessible(true);
            try {
                if (field.getType().isAssignableFrom(Integer.class) || field.getType().isAssignableFrom(int.class))  {
                    field.set(obj, cursor.getInt(i));
                }else if (field.getType().isAssignableFrom(Double.class)
                        || field.getType().isAssignableFrom(double.class)
                        || field.getType().isAssignableFrom(Float.class)) {
                    field.set(obj, cursor.getDouble(i));
                }else if (field.getType().isAssignableFrom(String.class)){
                    field.set(obj, cursor.getString(i) );
                }else if (field.getType().isAssignableFrom(Date.class)) {
                    Date dt = new Date(cursor.getLong(i));
                    field.set(obj, dt);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        result = true;
        return result;
    }

    public static String debugToString(BaseModel obj){
        String str = "Debug Object " + obj.getClass().getName();
        List<Field> fields = getDBFields(obj);
        for (Field field : fields) {
            str += "\n";
            str += field.getName() + " = " + getReadValue(field, obj);
        }
        return str;
    }

}


