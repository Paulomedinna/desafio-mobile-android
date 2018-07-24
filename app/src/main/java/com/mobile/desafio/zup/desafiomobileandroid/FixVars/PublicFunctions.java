package com.mobile.desafio.zup.desafiomobileandroid.FixVars;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.mobile.desafio.zup.desafiomobileandroid.R;

public class PublicFunctions {

    public static void openSiteZup(Context context){
        ((Activity)context).startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.url_zup_site))));
    }

}
