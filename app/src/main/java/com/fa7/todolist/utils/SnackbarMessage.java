package com.fa7.todolist.utils;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;

public class SnackbarMessage {
    public static void setButtonMessage(Button button, final String msg) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public static void setButtonMessage(FloatingActionButton button, final String msg) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public static void setProgressMessage(View view, boolean action, String msg) {
        String acao = (action ? "Sucesso" : "Erro");
        Snackbar.make(view, acao + msg, Snackbar.LENGTH_LONG).show();
    }
}
