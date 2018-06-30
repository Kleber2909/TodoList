package com.fa7.todolist.utils;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
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
        final ForegroundColorSpan whiteSpan;
        SpannableStringBuilder snackbarText = new SpannableStringBuilder(acao + msg);
        if(action) {
            whiteSpan = new ForegroundColorSpan(ContextCompat.getColor(view.getContext(), android.R.color.holo_green_dark));
            snackbarText.setSpan(whiteSpan, 0, snackbarText.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        } else {
            whiteSpan = new ForegroundColorSpan(ContextCompat.getColor(view.getContext(), android.R.color.holo_red_dark));
            snackbarText.setSpan(whiteSpan, 0, snackbarText.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        }

        Snackbar.make(view, snackbarText, Snackbar.LENGTH_LONG).show();
    }

    public static void setProgressMessage(View view, String msg) {
        final ForegroundColorSpan whiteSpan = new ForegroundColorSpan(ContextCompat.getColor(view.getContext(), android.R.color.holo_blue_bright));
        SpannableStringBuilder snackbarText = new SpannableStringBuilder(msg);
        snackbarText.setSpan(whiteSpan, 0, snackbarText.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        Snackbar.make(view, snackbarText, Snackbar.LENGTH_LONG).show();
    }
}