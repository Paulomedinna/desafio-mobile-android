package com.mobile.desafio.zup.desafiomobileandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.mobile.desafio.zup.desafiomobileandroid.FixVars.DrawConfig;
import com.mobile.desafio.zup.desafiomobileandroid.FixVars.PublicFunctions;

public class AppInfo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);

        DrawConfig.begin(this, getString(R.string.app_name), true, null);
    }


    public void openExternalUrl(View view) {
        PublicFunctions.openSiteZup(this);
    }

    public void comeBack(View view) {
        finish();
    }

    public void openRepository(View view) {
        PublicFunctions.openUrl(view.getContext(), "https://github.com/murilooliveiraspinello/desafio-mobile");
    }

    public void openWhatsapp(View view) {
        PublicFunctions.openUrl(view.getContext(), "https://bit.ly/2JwCg1g");
    }

    public void openLinkedin(View view) {
        PublicFunctions.openUrl(view.getContext(), "https://www.linkedin.com/in/murilo-oliveira-spinello/");
    }

    public void openGit(View view) {
        PublicFunctions.openUrl(view.getContext(), "https://github.com/murilooliveiraspinello");
    }

    public void openFacebook(View view) {
        PublicFunctions.openUrl(view.getContext(), "https://www.facebook.com/murilooliveiraspinello");
    }
}
