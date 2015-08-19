package com.example.project.view.start;

import com.example.project.view.common.mvp.BaseActivityPresenter;
import com.example.project.view.contact_list.ContactListActivityIntent;

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
