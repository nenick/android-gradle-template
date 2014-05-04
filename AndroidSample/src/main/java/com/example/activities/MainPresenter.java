package com.example.activities;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

@EBean
public class MainPresenter {

    @RootContext
    MainActivity view;
}
