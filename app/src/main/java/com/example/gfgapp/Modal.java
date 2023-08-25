package com.example.gfgapp;

public class Modal {
private boolean adapterStatement;
private int searchAdapterId;

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
