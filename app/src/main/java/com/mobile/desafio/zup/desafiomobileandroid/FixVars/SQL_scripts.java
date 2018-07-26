package com.mobile.desafio.zup.desafiomobileandroid.FixVars;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SQL_scripts {

    public interface positiveCall{
        void get(String form);
    }
    public interface negativeCall{
        void get();
    }

    public static String tb_researches () {
        return "    CREATE TABLE `tb_researches` (\n" +
                "  `id` INTEGER PRIMARY KEY,\n" +
                "  `flag` varchar(100) NOT NULL,\n" +
                "  `form` varchar(1000) NOT NULL,\n" +
                "  `registeredAt` datetime NOT NULL \n" +
                ")";
    }

    public static String drop_tables () {
        return " DROP TABLE IF EXISTS tb_researches;  ";
    }

    public static String script_researcheLine () {
        return " SELECT * FROM tb_researches where flag = ?";
    }


    public static String script_insert_researche() {
        return " INSERT INTO tb_researches (flag, form, registeredAt) values " +
                " ( ?, ?, ?) ";
    }

    public static void getResearcheLine(String flag, Context context, positiveCall hadLine, negativeCall didnHad ){
        String [] flagA = new String[]{flag};

        SQLiteDatabase db = new SqlOpenScript(context).getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(script_researcheLine(), flagA);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            hadLine.get(cursor.getString(cursor.getColumnIndex("form")));
        } else {
            didnHad.get();
        }

        db.close();

    }
}
