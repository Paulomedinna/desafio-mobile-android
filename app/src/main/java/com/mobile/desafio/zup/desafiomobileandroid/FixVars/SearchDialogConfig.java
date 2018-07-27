package com.mobile.desafio.zup.desafiomobileandroid.FixVars;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.desafio.zup.desafiomobileandroid.R;

public class SearchDialogConfig {

    public interface MyCall {
        void getStrings(String title, String type, String year, CloseDialog closeDialog);
    }


    public interface CloseDialog {
        void init();
    }


    public static void begin(final Context context, final MyCall getResult) {
        final View dialogSearch = ((Activity) context).findViewById(R.id.diagloSearch);

        final EditText edt_title = ((Activity) context).findViewById(R.id.edt_title);
        final TextView edt_type = ((Activity) context).findViewById(R.id.edt_type);
        final EditText edt_year = ((Activity) context).findViewById(R.id.edt_year);

        final CharSequence[] typeList = new CharSequence[]{"movie", "series", "episode"};

        if (dialogSearch.getVisibility() == View.VISIBLE) {
            PublicFunctions.hideKeyboard(context, edt_type);
            dialogSearch.setVisibility(View.GONE);
            edt_type.setText("");
            edt_title.setText("");
            edt_year.setText("");
        } else {
            dialogSearch.setVisibility(View.VISIBLE);
            edt_title.requestFocus();
            PublicFunctions.showKeyboard(context, edt_title);
        }


        edt_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PublicFunctions.SelectItemDialog(context, "Selecione o Tipo", typeList, new PublicFunctions.GetInt() {
                    @Override
                    public void get(int i) {
                        edt_type.setText(typeList[i].toString());
                    }
                });
            }
        });


        ((View) ((Activity) context).findViewById(R.id.btnCancelSearch)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PublicFunctions.hideKeyboard(context, edt_type);
                dialogSearch.setVisibility(View.GONE);
                edt_type.setText("");
                edt_title.setText("");
                edt_year.setText("");
            }
        });


        ((View) ((Activity) context).findViewById(R.id.btnStartSearch)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edt_title.getText().toString().isEmpty()) {
                    showError(context, "Título obrigatório");
                    edt_title.requestFocus();
                    PublicFunctions.showKeyboard(context, edt_title);

                } else {
                    getResult.getStrings(edt_title.getText().toString(), edt_type.getText().toString(), edt_year.getText().toString(), new CloseDialog() {
                        @Override
                        public void init() {
                            edt_type.setText("");
                            edt_title.setText("");
                            edt_year.setText("");
                            PublicFunctions.hideKeyboard(context, edt_type);
                            dialogSearch.setVisibility(View.GONE);

                        }
                    });
                }
            }
        });


    }


    public static void showError(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
