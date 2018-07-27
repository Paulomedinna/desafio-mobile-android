package com.mobile.desafio.zup.desafiomobileandroid;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.mobile.desafio.zup.desafiomobileandroid.FixVars.DrawConfig;
import com.mobile.desafio.zup.desafiomobileandroid.FixVars.PublicFunctions;
import com.mobile.desafio.zup.desafiomobileandroid.FixVars.SQL_scripts;
import com.mobile.desafio.zup.desafiomobileandroid.FixVars.SqlOpenScript;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SelectedSearch extends Activity {

    public interface Getform {
        void getForm(JSONObject json);
    }

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

        if (form.has("imdbID"))
            pageRulles(form.getString("imdbID"));

        ((View) findViewById(R.id.btnBack)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ((View) findViewById(R.id.btnToMyList)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(view.getContext(), MySaveList.class));
            }
        });

    }

    private void pageRulles(final String imdbID) throws JSONException {
        SQL_scripts.getResearcheLine(imdbID, this,

                new SQL_scripts.positiveCall() {
                    @Override
                    public void get(String form) throws JSONException {
                        Log.e("had Form", form);
                        putDataHeader(new JSONObject(form));
                    }
                }, new SQL_scripts.negativeCall() {
                    @Override
                    public void get() throws UnsupportedEncodingException {
                        Log.e("dind had Form", "-----");

                        getRestForm(imdbID, new Getform() {
                            @Override
                            public void getForm(JSONObject json) {
                                try {
                                    putDataHeader(json);
                                    SQL_scripts.postResearcheLine(imdbID, json.toString(), getApplicationContext());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
    }

    private void putDataHeader(final JSONObject form) throws JSONException { //
        if (form.has("Title")) {
            ((TextView) findViewById(R.id.title)).setText(form.getString("Title"));
            DrawConfig.begin(this, form.getString("Title"), true, null);
        }

        if (form.has("Year"))
            ((TextView) findViewById(R.id.year)).setText(form.getString("Year"));

        if (form.has("Poster"))
            Glide.with(this).load(form.getString("Poster")).into(((ImageView) findViewById(R.id.imageLoad)));

        if (form.has("Metascore"))
            ((TextView) findViewById(R.id.metascore)).setText(form.getString("Metascore"));
        if (form.has("imdbRating"))
            ((TextView) findViewById(R.id.imdb)).setText(form.getString("imdbRating"));
        if (form.has("imdbVotes"))
            ((TextView) findViewById(R.id.votes)).setText(form.getString("imdbVotes"));
        if (form.has("Director"))
            ((TextView) findViewById(R.id.director)).setText(form.getString("Director"));
        if (form.has("Production"))
            ((TextView) findViewById(R.id.production)).setText(form.getString("Production"));
        if (form.has("Awards"))
            ((TextView) findViewById(R.id.awards)).setText(form.getString("Awards"));
        if (form.has("Writer"))
            ((TextView) findViewById(R.id.writer)).setText(form.getString("Writer"));
        if (form.has("Actors"))
            ((TextView) findViewById(R.id.actor)).setText(form.getString("Actors"));
        if (form.has("Website")) {
            ((TextView) findViewById(R.id.website)).setText(form.getString("Website"));

            ((TextView) findViewById(R.id.website)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        PublicFunctions.openUrl(view.getContext(), form.getString("Website"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        }


    }


    private void getRestForm(String imdbID, final Getform getform) throws UnsupportedEncodingException {

        Log.e("imdbID", imdbID);

        imdbID = URLEncoder.encode(imdbID.trim(), "utf-8");


        final String url = "http://www.omdbapi.com/?apikey=" + PublicFunctions.apikey + "&i=" + imdbID;
        //            const url = `${urlBase}?apikey=${apikey}&i=${imdbID}`

        PublicFunctions._REST_get(this, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                getform.getForm(response);
            }
        });
    }
}
