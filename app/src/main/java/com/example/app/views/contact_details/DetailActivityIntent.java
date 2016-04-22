package com.example.app.views.contact_details;

import android.content.Context;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

@EBean
public class DetailActivityIntent {

    @RootContext
    Context context;

    public void start(long contactId) {
        DetailActivity_.intent(context).contactId(contactId).start();
    }
}
