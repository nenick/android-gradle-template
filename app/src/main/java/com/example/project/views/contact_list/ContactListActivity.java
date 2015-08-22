package com.example.project.views.contact_list;

import com.example.project.R;
import com.example.project.views.common.mvp.BaseActivityPresenter;
import com.example.project.views.contact_details.DetailActivityIntent;
import com.example.project.views.contact_details.DetailFragment;
import com.example.project.views.contact_edit.EditActivityIntent;

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

    @Bean
    EditActivityIntent editActivityIntent;

    @Override
    public void onViewCreated() {
        contactListFragment.setShowContactListener(this);
    }

    @OptionsItem(R.id.action_add_contact)
    public void onCreateContact() {
        editActivityIntent.start();
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
