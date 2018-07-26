package com.mobile.desafio.zup.desafiomobileandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.mobile.desafio.zup.desafiomobileandroid.FixVars.DrawConfig;
import com.mobile.desafio.zup.desafiomobileandroid.FixVars.PublicFunctions;
import com.mobile.desafio.zup.desafiomobileandroid.FixVars.SearchDialogConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class BaseApp extends Activity {

    private ListView listSearch;

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

        listSearch = findViewById(R.id.listSearch);

        title =  URLEncoder.encode(title.trim(), "utf-8");
        type =  URLEncoder.encode(type.trim(), "utf-8");
        year =  URLEncoder.encode(year.trim(), "utf-8");

        if (!type.isEmpty()) type = "&type="+type; else type = "";
        if (!year.isEmpty()) year = "&y="+year; else year = "";

        final String url ="http://www.omdbapi.com/?apikey="+PublicFunctions.apikey+type+year+"&s="+title;

        PublicFunctions._REST_get(this, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                closeDialog.init();

                if(response.has("Search")){
                    try {
                        final JSONArray Search = response.getJSONArray("Search");
                        listSearch.setAdapter( new SearchListAdapter(Search,listSearch.getContext()));

                        listSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                try {
                                    Log.e("itemClick", Search.getJSONObject(i).toString());

                                    Intent intent = new Intent(getApplicationContext(), SelectedSearch.class);
                                    intent.putExtra("form",Search.getJSONObject(i).toString());

                                    startActivity(intent);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
