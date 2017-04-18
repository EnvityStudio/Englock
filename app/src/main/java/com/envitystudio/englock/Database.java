package com.envitystudio.englock;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by thuan on 4/3/2017.
 */

public class Database {
    // duong dan cua file du lieu
    private String path;
    private SQLiteDatabase sqLiteDatabase;

    public Database()
    {


    }
    // chuyen file du lieu tu assets -> internal app
    public void CopyDatabase(Context context)
    {
        try {
            // bước kiểm tra xem database co trong may chua
            path = Environment.getDataDirectory().getAbsolutePath() + "/data/com.envitystudio.englock/Database";
            File file = new File(path);
            if(file.exists())
            {
                return;
            }

         // bước 1: Mở 1 luồng để đọc dữ liệu
            // Sử dụng  InputStream
            InputStream is = context.getAssets().open("Database");

            // bước 2: Mở 1 luồng để ghi dữ liệu

            FileOutputStream fos = new FileOutputStream(file);
            /// Bước 3: tiến hành đọc ghi
            byte [] b = new byte[1024];
            int length;
            while ((length = is.read(b))!=-1)
            {
                fos.write(b,0,length);
            }
            // đóng lại các luồng
            is.close();
            fos.close();

        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void Open()
    {
        sqLiteDatabase= SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READONLY);
    }
    private void Close()
    {
        sqLiteDatabase.close();
    }
    public Word[]GetRandoomTwoWord()
    {
        Open();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM TECHNOLOGY ORDER BY RANDOM() LIMIT 2",null);
        Word [] words = new Word[2];
        cursor.moveToFirst();
        int i =0;
        while(!cursor.isAfterLast())
        {
            String en = cursor.getString(cursor.getColumnIndex("EnglishMean"));
            String vi = cursor.getString(cursor.getColumnIndex("VietNameMean"));
            words[i] = new Word(en,vi);
            i++;
            cursor.moveToNext();
        }
        cursor.close();
        Close();
        return words;
    }
}
