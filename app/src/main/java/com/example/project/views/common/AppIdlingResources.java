package com.example.project.views.common;

import android.support.test.espresso.contrib.CountingIdlingResource;

import org.androidannotations.annotations.EBean;

@EBean(scope = EBean.Scope.Singleton)
public class AppIdlingResources {

    CountingIdlingResource idlingResource = new CountingIdlingResource("AppIdlingResources");

    public CountingIdlingResource getIdlingResource() {
        return idlingResource;
    }

    public void increment() {
        idlingResource.increment();
    }

    public void decrement() {
        idlingResource.decrement();
    }
}
