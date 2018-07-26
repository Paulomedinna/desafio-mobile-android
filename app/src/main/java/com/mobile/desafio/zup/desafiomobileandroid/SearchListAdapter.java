package com.mobile.desafio.zup.desafiomobileandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchListAdapter extends BaseAdapter {

    private JSONArray arrayList;
    private Context context;

    public SearchListAdapter(JSONArray arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return arrayList.length();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        convertView = ((Activity)context).getLayoutInflater().inflate(R.layout.list_search, null);

        try {
            JSONObject json = (JSONObject) arrayList.get(i);
            String Title = json.getString("Title");
            String Year = json.getString("Year");
            String Poster = json.getString("Poster");

            TextView title = convertView.findViewById(R.id.title);
            TextView year = convertView.findViewById(R.id.year);
            ImageView imageLoad = convertView.findViewById(R.id.imageLoad);
            Glide.with(context).load(Poster).into(imageLoad);

            title.setText(Title);
            year.setText(Year);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
