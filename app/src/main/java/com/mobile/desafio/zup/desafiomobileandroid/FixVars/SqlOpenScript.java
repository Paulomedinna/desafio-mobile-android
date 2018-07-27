package com.mobile.desafio.zup.desafiomobileandroid.FixVars;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlOpenScript extends SQLiteOpenHelper {

    private static final String DB_NAME = "database";
    private static final int VERSION = 2;

    public SqlOpenScript(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_scripts.tb_researches());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_scripts.drop_tables());
        onCreate(sqLiteDatabase);
    }
}
