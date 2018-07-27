package com.mobile.desafio.zup.desafiomobileandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
    private String state_title;
    private String state_type;
    private String state_year;
    private String state_page = "1";

    private SearchDialogConfig.CloseDialog state_closeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_app);


        DrawConfig.begin(this, getString(R.string.titlePage_BaseApp), true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                searchDialogInit();
            }
        });
    }

    private void searchDialogInit() {
        SearchDialogConfig.begin(this, new SearchDialogConfig.MyCall() {
            @Override
            public void getStrings(String title, String type, String year, SearchDialogConfig.CloseDialog closeDialog) {
                try {

                    state_title = title;
                    state_type = type;
                    state_year = year;
                    state_closeDialog = closeDialog;
                    state_page = "1";

                    initPage(title, type, year, state_page, closeDialog);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void searchClick(View view) {
        searchDialogInit();
    }

    private void initPage(String title, String type, String year, String page, final SearchDialogConfig.CloseDialog closeDialog) throws UnsupportedEncodingException {


        state_title = title;
        state_type = type;
        state_year = year;
        state_closeDialog = closeDialog;
        state_page = page;

        listSearch = findViewById(R.id.listSearch);

        title = URLEncoder.encode(title.trim(), "utf-8");
        type = URLEncoder.encode(type.trim(), "utf-8");
        year = URLEncoder.encode(year.trim(), "utf-8");

        if (!type.isEmpty()) type = "&type=" + type;
        else type = "";
        if (!year.isEmpty()) year = "&y=" + year;
        else year = "";

        final String url = "http://www.omdbapi.com/?apikey=" + PublicFunctions.apikey + type + year + "&s=" + title + "&page=" + page;

        PublicFunctions._REST_get(this, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                closeDialog.init();

                if (response.has("Search")) {
                    try {
                        final JSONArray Search = response.getJSONArray("Search");
                        listSearch.setAdapter(new SearchListAdapter(Search, listSearch.getContext()));

                        ((View) findViewById(R.id.noResultsArea)).setVisibility(View.GONE);

                        paginationRulles(Integer.parseInt(response.getString("totalResults")));

                        listSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                try {
                                    Log.e("itemClick", Search.getJSONObject(i).toString());

                                    Intent intent = new Intent(getApplicationContext(), SelectedSearch.class);
                                    intent.putExtra("form", Search.getJSONObject(i).toString());

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

    @SuppressLint("SetTextI18n")
    private void paginationRulles(int totalResults) {

        View btn_BEFORE = findViewById(R.id.btn_BEFORE);
        View btn_AFTER = findViewById(R.id.btn_AFTER);
        View paginationArea = findViewById(R.id.paginationArea);

        if (totalResults <= 10)
            paginationArea.setVisibility(View.GONE);
        else {
            paginationArea.setVisibility(View.VISIBLE);

            ((TextView) findViewById(R.id.pageCount)).setText(
                    state_page + "/" + String.valueOf((int) Math.ceil(totalResults / 10))
            );

            if (Integer.parseInt(state_page) == 1)
                btn_BEFORE.setVisibility(View.GONE);
            else
                btn_BEFORE.setVisibility(View.VISIBLE);


            if (Integer.parseInt(state_page) < Math.ceil(totalResults / 10))
                btn_AFTER.setVisibility(View.VISIBLE);
            else
                btn_AFTER.setVisibility(View.GONE);

            btn_BEFORE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        initPage(state_title, state_type, state_year, String.valueOf(Integer.parseInt(state_page) - 1), state_closeDialog);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            });

            btn_AFTER.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        initPage(state_title, state_type, state_year, String.valueOf(Integer.parseInt(state_page) + 1), state_closeDialog);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            });
        }


    }

    public void myListClick(View view) {

        startActivity(new Intent(this, MySaveList.class));
    }
}
