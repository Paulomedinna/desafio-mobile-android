package com.mobile.desafio.zup.desafiomobileandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mobile.desafio.zup.desafiomobileandroid.FixVars.PublicFunctions;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void openExternalUrl(View view) {
        PublicFunctions.openSiteZup(this);
    }


    public void myFancyMethod(View v) {
        Intent intent = new Intent(this, BaseApp.class);
        startActivity(intent);
    }
}
