package com.example.app.views.common.testwrapper;

import android.app.Activity;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

@EBean
public class ViewFinisher {

    @RootContext
    Activity activity;

    public void finish() {
        activity.finish();
    }
}
