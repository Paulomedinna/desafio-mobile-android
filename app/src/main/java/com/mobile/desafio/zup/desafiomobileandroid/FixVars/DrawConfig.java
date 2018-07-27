package com.mobile.desafio.zup.desafiomobileandroid.FixVars;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.desafio.zup.desafiomobileandroid.AppInfo;
import com.mobile.desafio.zup.desafiomobileandroid.BaseApp;
import com.mobile.desafio.zup.desafiomobileandroid.MySaveList;
import com.mobile.desafio.zup.desafiomobileandroid.R;

public class DrawConfig {


    public static void begin(final Context context, String title, Boolean showBackBtn, View.OnClickListener searchClick) {


        TextView titlePage;
        final DrawerLayout drawer_layout;
        final Button barBtn, searchBtn, backBtn;
        final LinearLayout toHome, toAbout, toMyBookmarks;
        final ImageView logoInDraw;


        logoInDraw = ((Activity) context).findViewById(R.id.logoInDraw);
        toHome = ((Activity) context).findViewById(R.id.toHome);
        toAbout = ((Activity) context).findViewById(R.id.toAbout);
        toMyBookmarks = ((Activity) context).findViewById(R.id.toMyBookmarks);

        titlePage = ((Activity) context).findViewById(R.id.titlePage);
        searchBtn = ((Activity) context).findViewById(R.id.searchBtn);
        backBtn = ((Activity) context).findViewById(R.id.backBtn);
        barBtn = ((Activity) context).findViewById(R.id.barBtn);
        drawer_layout = ((Activity) context).findViewById(R.id.drawer_layout);

        if (showBackBtn) backBtn.setVisibility(View.VISIBLE);

        logoInDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PublicFunctions.openSiteZup(context);
            }
        });

        if (searchClick != null)
            searchBtn.setOnClickListener(searchClick);
        else searchBtn.setVisibility(View.GONE);

        drawer_layout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                barBtn.setText(context.getString(R.string.icon_times));
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                barBtn.setText(context.getString(R.string.icon_bars));
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        titlePage.setText(title);

        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context.getClass() != BaseApp.class)
                    context.startActivity(new Intent(context, BaseApp.class));

            }
        });

        toAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (context.getClass() != AppInfo.class)
                    context.startActivity(new Intent(context, AppInfo.class));
            }
        });

        toMyBookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context.getClass() != MySaveList.class)
                    context.startActivity(new Intent(context, MySaveList.class));
            }
        });

        barBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (drawer_layout.isDrawerOpen(Gravity.END)) {
                    drawer_layout.closeDrawer(Gravity.END);
                } else {
                    drawer_layout.openDrawer(Gravity.END);
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) context).finish();
            }
        });

    }
}
