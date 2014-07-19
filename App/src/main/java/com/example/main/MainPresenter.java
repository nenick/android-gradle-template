package com.example.main;

import com.example.databaseexample.DatabaseActivity_;
import com.example.restexample.RestActivity_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

@EBean
public class MainPresenter {

    @RootContext
    MainActivity view;

    public void onOpenDatabaseExample() {
        DatabaseActivity_.intent(view).start();
    }

    public void onOpenRestExample() {
        RestActivity_.intent(view).start();
    }
}
