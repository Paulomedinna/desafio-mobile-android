package com.mobile.desafio.zup.desafiomobileandroid;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.TextView;

import com.mobile.desafio.zup.desafiomobileandroid.FixVars.DrawConfig;

public class BaseApp extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_app);


        DrawConfig.begin(this, getString(R.string.titlePage_BaseApp), true);
    }
}
