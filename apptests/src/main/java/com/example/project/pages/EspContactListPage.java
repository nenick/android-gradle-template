package com.example.project.pages;

import com.example.app.R;
import com.example.project.espresso.EspListView;
import com.example.project.espresso.EspMenuItem;

public class EspContactListPage {

    public EspMenuItem<EspEditContactPage> createContact() {
        return new EspMenuItem<EspEditContactPage>(R.id.action_add_contact) {
            @Override
            public EspEditContactPage click() {
                super.click();
                return new EspEditContactPage();
            }
        };
    }

    public EspMenuItem<EspContactListPage> syncContacts() {
        return new EspMenuItem<EspContactListPage>(R.id.action_sync_contacts) {
            @Override
            public EspContactListPage click() {
                super.click();
                return EspContactListPage.this;
            }
        };
    }

    public EspListView contactList() {
        return new EspListView(R.id.listView);
    }
}
