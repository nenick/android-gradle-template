package com.example.project.view.contact_list;

import com.example.project.R;
import com.example.project.view.common.mvp.BaseActivityPresenter;
import com.example.project.view.contact_details.DetailActivityIntent;
import com.example.project.view.contact_details.DetailFragment;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class ContactListActivity extends BaseActivityPresenter implements ShowContactListener {

    @FragmentById(R.id.fragment_list)
    ContactListFragment contactListFragment;

    @FragmentById(R.id.fragment_detail)
    DetailFragment detailFragment;

    @Bean
    DetailActivityIntent detailActivityIntent;

    @OptionsItem(R.id.action_settings)
    public void onSettingsMenu() {

    }

    @Override
    public void onShowContact(long contactId) {
        if(detailFragment == null) {
            detailActivityIntent.start(contactId);
        } else {
            detailFragment.onShowContact(contactId);
        }
    }
}
