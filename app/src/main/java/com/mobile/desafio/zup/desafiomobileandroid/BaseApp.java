package com.mobile.desafio.zup.desafiomobileandroid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.mobile.desafio.zup.desafiomobileandroid.FixVars.DrawConfig;
import com.mobile.desafio.zup.desafiomobileandroid.FixVars.PublicFunctions;
import com.mobile.desafio.zup.desafiomobileandroid.FixVars.SearchDialogConfig;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class BaseApp extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_app);

        DrawConfig.begin(this, getString(R.string.titlePage_BaseApp), true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SearchDialogConfig.begin(view.getContext(), new SearchDialogConfig.MyCall() {
                    @Override
                    public void getStrings(String title, String type, String year, SearchDialogConfig.CloseDialog closeDialog) {
                        try {
                            initPage(title, type, year, closeDialog);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void initPage(String title, String type, String year, final SearchDialogConfig.CloseDialog closeDialog) throws UnsupportedEncodingException {

        Log.e("title", title);
        Log.e("type", type);
        Log.e("year", year);

        if (!type.isEmpty()) type = "&type="+type; else type = "";
        if (!year.isEmpty()) year = "&y="+year; else year = "";

        title =  URLEncoder.encode(title.trim(), "utf-8");
        type =  URLEncoder.encode(type.trim(), "utf-8");
        year =  URLEncoder.encode(year.trim(), "utf-8");

        final String url ="http://www.omdbapi.com/?apikey="+PublicFunctions.apikey+type+year+"&s="+title;

        PublicFunctions._REST_get(this, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                closeDialog.init();
            }
        });
    }
}
