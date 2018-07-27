package com.mobile.desafio.zup.desafiomobileandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mobile.desafio.zup.desafiomobileandroid.FixVars.DrawConfig;
import com.mobile.desafio.zup.desafiomobileandroid.FixVars.SQL_scripts;

import org.json.JSONArray;
import org.json.JSONException;

public class MySaveList extends Activity {

    private ListView listSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_save_list);

        DrawConfig.begin(this, "Minha Lista", true, null);

        try {
            initPage();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initPage() throws JSONException {

        listSearch = findViewById(R.id.listSearch);

        SQL_scripts.getResearcheLines(this, new SQL_scripts.GetArray() {
            @Override
            public void get(final JSONArray array) {

                listSearch.setAdapter(new SearchListAdapter(array, listSearch.getContext()));
                listSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        try {
                            Log.e("itemClick", array.getJSONObject(i).toString());

                            Intent intent = new Intent(getApplicationContext(), SelectedSearch.class);
                            intent.putExtra("form", array.getJSONObject(i).toString());

                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }
}
