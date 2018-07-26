package com.mobile.desafio.zup.desafiomobileandroid;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobile.desafio.zup.desafiomobileandroid.FixVars.DrawConfig;
import com.mobile.desafio.zup.desafiomobileandroid.FixVars.SQL_scripts;
import com.mobile.desafio.zup.desafiomobileandroid.FixVars.SqlOpenScript;

import org.json.JSONException;
import org.json.JSONObject;

public class SelectedSearch extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_search);

        try {
            initPage();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initPage() throws JSONException {

        JSONObject form = new JSONObject(getIntent().getStringExtra("form"));
        putDataHeader(form);

        if(form.has("imdbID"))
            pageRulles(form.getString("imdbID"));

//        SqlOpenScript openScript = new SqlOpenScript(this);
//        SQLiteDatabase dbase = openScript.getWritableDatabase();
//        dbase.close();
    }

    private void pageRulles(String imdbID) {
        SQL_scripts.getResearcheLine(imdbID, this,

                new SQL_scripts.positiveCall() {
                    @Override
                    public void get(String form) {
                        Log.e("had Form", form);
                    }
                }, new SQL_scripts.negativeCall() {
                    @Override
                    public void get() {
                        Log.e("dind had Form", "-----");
                        //            const url = `${urlBase}?apikey=${apikey}&i=${imdbID}`
                    }
                });
    }

    private void putDataHeader(JSONObject form) throws JSONException { //
        if(form.has("Title")) {
            ((TextView) findViewById(R.id.title)).setText(form.getString("Title"));
            DrawConfig.begin(this, form.getString("Title"), true, null);
        }

        if(form.has("Year"))
            ((TextView)findViewById(R.id.year)).setText(form.getString("Year"));

        if(form.has("Poster"))
            Glide.with(this).load(form.getString("Poster")).into(((ImageView)findViewById(R.id.imageLoad)));

    }
}
