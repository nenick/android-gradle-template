package com.example.app.views.start;

import com.example.app.views.common.mvp.BaseActivityPresenter;
import com.example.app.views.contact_list.ContactListActivityIntent;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

@EActivity
public class StartActivity extends BaseActivityPresenter {

    @Bean
    ContactListActivityIntent contactListActivityIntent;

    @Override
    public void onViewResume() {
        contactListActivityIntent.start();
    }
}
