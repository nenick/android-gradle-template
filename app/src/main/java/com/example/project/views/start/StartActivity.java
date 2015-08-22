package com.example.project.views.start;

import com.example.project.views.common.mvp.BaseActivityPresenter;
import com.example.project.views.contact_list.ContactListActivityIntent;

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
