package com.example.gfgapp;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;

public class Modal {
    private boolean adapterStatement;
    private int searchAdapterId;
    private View view;
    private DisplayMetrics metrics;
    private Context context;

    private int screeWidth;

    public int getScreeWidth() {
        return screeWidth;
    }

    public void setScreeWidth(int screeWidth) {
        this.screeWidth = screeWidth;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public boolean isAdapterStatement() {
        return adapterStatement;
    }

    public void setAdapterStatement(boolean adapterStatement) {
        this.adapterStatement = adapterStatement;
    }

    public int getSearchAdapterId() {
        return searchAdapterId;
    }

    public void setSearchAdapterId(int searchAdapterId) {
        this.searchAdapterId = searchAdapterId;
    }

    public Modal(boolean adapterStatement) {
        this.adapterStatement = adapterStatement;
    }
}
