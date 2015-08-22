package com.example.project.views.contact_edit;

import android.content.Context;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

@EBean
public class EditActivityIntent {

    @RootContext
    Context context;

    public void start(long contactId) {
        EditActivity_.intent(context).contactId(contactId).start();
    }

    public void start() {
        EditActivity_.intent(context).start();
    }
}
