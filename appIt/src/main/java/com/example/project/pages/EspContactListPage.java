package com.example.project.pages;

import com.example.project.R;
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

    public EspListView contactList() {
        return new EspListView(R.id.listView);
    }
}
