package com.mobile.desafio.zup.desafiomobileandroid.FixVars;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class SQL_scripts {

    public interface positiveCall {
        void get(String form) throws JSONException;
    }

    public interface GetArray {
        void get(JSONArray array);
    }

    public interface negativeCall {
        void get() throws UnsupportedEncodingException;
    }

    public static String tb_researches() {
        return "    CREATE TABLE `tb_researches` (\n" +
                "  `id` INTEGER PRIMARY KEY,\n" +
                "  `flag` varchar(100) NOT NULL,\n" +
                "  `form` varchar(1000) NOT NULL,\n" +
                "  `registeredAt` datetime NOT NULL \n" +
                ")";
    }

    public static String drop_tables() {
        return " DROP TABLE IF EXISTS tb_researches;  ";
    }

    private static String script_researcheLine() {
        return " SELECT * FROM tb_researches where flag = ?";
    }

    private static String script_researcheLines() {
        return " SELECT * FROM tb_researches where 1";
    }


    private static String script_insert_researche(String flag, String form) {
        return " INSERT INTO tb_researches (flag, form, registeredAt) values " +
                " ( '" + flag + "', '" + form + "', datetime('now')) ";
    }

    public static void postResearcheLine(String flag, String form, Context context) {

        SQLiteDatabase db = new SqlOpenScript(context).getWritableDatabase();
        db.execSQL(script_insert_researche(flag, form));
        Log.e("postResearcheLine", "insert");
        db.close();
    }

    public static void getResearcheLines(Context context, GetArray hadLines) throws JSONException {
        String[] flagA = new String[]{};

        SQLiteDatabase db = new SqlOpenScript(context).getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(script_researcheLines(), flagA);

        JSONArray array = new JSONArray();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            array.put(new JSONObject(cursor.getString(cursor.getColumnIndex("form"))));

            while (cursor.moveToNext()) {
                array.put(new JSONObject(cursor.getString(cursor.getColumnIndex("form"))));
            }

            hadLines.get(array);

        } else
            hadLines.get(array);


        db.close();
    }


    public static void getResearcheLine(String flag, Context context, positiveCall hadLine, negativeCall didnHad) throws JSONException {
        String[] flagA = new String[]{flag};

        SQLiteDatabase db = new SqlOpenScript(context).getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(script_researcheLine(), flagA);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            hadLine.get(cursor.getString(cursor.getColumnIndex("form")));
        } else {
            try {
                didnHad.get();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        db.close();
    }
}
