package com.mobile.desafio.zup.desafiomobileandroid.FixVars;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mobile.desafio.zup.desafiomobileandroid.R;

import org.json.JSONException;
import org.json.JSONObject;

public class PublicFunctions {

    public interface GetInt{
        void get(int i);
    }

    public static final String apikey = "933dbdbc";

    public static void openSiteZup(Context context) {
        ((Activity) context).startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.url_zup_site))));
    }

    public static void _REST_get(final Context context, final String url, final Response.Listener<JSONObject> responseOk) {

        final View progressBar = ((Activity) context).findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        Log.e("_REST_get", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("onResponse", response.toString());

                        if (response.has("Error")) {
                            try {

                                Log.e(":::getString", response.getString("Error"));
                                errorRequisitionReturn(context, url, responseOk, response.getString("Error"), false);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            responseOk.onResponse(response);
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        progressBar.setVisibility(View.GONE);
                        Log.e("onErrorResponse", error.toString());

                        errorRequisitionReturn(context, url, responseOk, "Erro na requisição", true);

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }

    public static void errorRequisitionReturn(final Context context, final String url, final Response.Listener<JSONObject> responseOk, String message, Boolean retry) {
        if (retry)
            new AlertDialog.Builder(context)
                    .setTitle("Requisição")
                    .setMessage(replaceUsualErrorResponse(message, context) + "\nTentar novamente?")
                    .setPositiveButton("Novamente", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            _REST_get(context, url, responseOk);
                        }
                    })
                    .setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
        else
            new AlertDialog.Builder(context)
                    .setTitle("Requisição")
                    .setMessage(replaceUsualErrorResponse(message, context))
                    .setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
    }

    private static String replaceUsualErrorResponse(String error, Context context) {
        if (error.contains("Too many results."))
            return context.getString(R.string.no_resultss_found);
        else if (error.contains("not found")) return context.getString(R.string.no_resultss_found);
        else return error;
    }

    public static void showKeyboard(Context context, EditText editText) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }


    public static void hideKeyboard(Context context, View view) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



    public static void SelectItemDialog(Context context, String title, CharSequence[] charSequence, final GetInt getInt) {


        new AlertDialog.Builder(context).setTitle(title)
                .setSingleChoiceItems(charSequence, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getInt.get(i);
                    }
                }).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
                .show();
    }
}
