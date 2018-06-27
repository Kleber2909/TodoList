package com.fa7.todolist.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


import com.fa7.todolist.persistence.room.AppDatabase;
import com.fa7.todolist.persistence.room.MainDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopiaBanco {
    public void CopiaBancoParaSD(Context context){
        File f=new File("/data/user/0/com.fa7.todolist/databases/todo-list-db.db");
        FileInputStream fis=null;
        FileOutputStream fos=null;

        try
        {
            fis=new FileInputStream(f);
            fos=new FileOutputStream("/mnt/sdcard/todo-list-db.db");
            while(true)
            {
                int i=fis.read();
                if(i!=-1)
                {fos.write(i);}
                else
                {break;}
            }
            fos.flush();
            Toast.makeText(context, "DB dump OK", Toast.LENGTH_LONG).show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Toast.makeText(context, "DB dump ERROR", Toast.LENGTH_LONG).show();
        }
        finally
        {
            try
            {
                fos.close();
                fis.close();
            }
            catch(IOException ioe)
            {}
        }
    }
}
