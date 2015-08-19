package com.example.project.view.contact_list;

import android.content.Context;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

@EBean
public class ContactListActivityIntent {

    @RootContext
    Context context;

    public void start() {
        ContactListActivity_.intent(context).start();
    }
}
