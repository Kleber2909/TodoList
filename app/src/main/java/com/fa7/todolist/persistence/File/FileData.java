package com.fa7.todolist.persistence.File;

import android.content.Context;
import android.content.SharedPreferences;

public class FileData {
    private static final String prefsName = "TodoList";

    public static void saveText(Context context, String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefsName, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getText(Context context, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefsName, 0);
        return sharedPreferences.getString(key, "");
    }

    public void delText(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefsName, 0);
        sharedPreferences.edit().clear();
    }
}
