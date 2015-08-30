package com.example.project.views.contac_list;

import android.content.Intent;

import com.example.project.R;
import com.example.project.robolectric.RoboListView;
import com.example.project.views.contact_list.ContactListActivity;
import com.example.project.views.contact_list.ContactListActivity_;

import org.robolectric.Robolectric;
import org.robolectric.Shadows;

import static org.assertj.core.api.Assertions.assertThat;

public class RoboContactListPage {

    ContactListActivity contactListActivity;

    public void startPage() {
        contactListActivity = Robolectric.setupActivity(ContactListActivity_.class);
    }

    public RoboListView list() {
        return new RoboListView(contactListActivity, R.id.listView);
    }

    public Menu createContact() {
        return new Menu(R.id.action_add_contact);
    }

    public Menu syncContacts() {
        return new Menu(R.id.action_sync_contacts);
    }

    public Intent nextStartedActivity() {
        return Shadows.shadowOf(contactListActivity).getNextStartedActivity();
    }

    public class Menu {
        private int resourceId;

        public Menu(int resourceId) {
            this.resourceId = resourceId;
        }

        public void click() {
            assertThat(Shadows.shadowOf(contactListActivity).clickMenuItem(resourceId)).isTrue();
        }
    }
}