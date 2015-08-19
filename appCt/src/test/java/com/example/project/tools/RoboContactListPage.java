package com.example.project.tools;

import com.example.project.R;
import com.example.project.view.contact_list.ContactListActivity;
import com.example.project.view.contact_list.ContactListActivity_;

import org.robolectric.Robolectric;

public class RoboContactListPage {

    ContactListActivity contactListActivity;

    public void startPage() {
        contactListActivity = Robolectric.setupActivity(ContactListActivity_.class);
    }

    public RoboListView list() {
        return new RoboListView(contactListActivity, R.id.listView);
    }
}
