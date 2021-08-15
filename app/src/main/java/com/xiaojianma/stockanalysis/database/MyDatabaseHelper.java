package com.xiaojianma.stockanalysis.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_BOOK = "CREATE TABLE Book(\n" +
            "id INTEGER PRIMARY key autoincrement,\n" +
            "作者 text,\n" +
            "price REAL,\n" +
            "pages INTEGER,\n" +
            "name text\n" +
            ");";

    public static final String CREATE_CATEGORY = "CREATE TABLE Category(\n" +
            "id integer PRIMARY key autoincrement,\n" +
            "category_name text,\n" +
            "category_code integer\n" +
            ");";

    private Context mContext;

    public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_BOOK);
        sqLiteDatabase.execSQL(CREATE_CATEGORY);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists Book");
        sqLiteDatabase.execSQL("drop table if exists Category");
        onCreate(sqLiteDatabase);
        Toast.makeText(mContext, "Upgrade succeeded", Toast.LENGTH_SHORT).show();
    }
}
