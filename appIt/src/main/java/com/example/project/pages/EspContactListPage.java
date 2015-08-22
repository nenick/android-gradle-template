package com.example.project.pages;

import android.support.test.rule.ActivityTestRule;

import com.example.project.R;
import com.example.project.espresso.EspListView;
import com.example.project.espresso.EspMenuItem;

public class EspContactListPage {

    ActivityTestRule activityTestRule;

    public EspContactListPage(ActivityTestRule activityTestRule) {
        this.activityTestRule = activityTestRule;
    }

    public EspMenuItem<EspCreateContactPage> createContact() {
        return new EspMenuItem<EspCreateContactPage>(R.id.action_add_contact) {
            @Override
            public EspCreateContactPage click() {
                super.click();
                return new EspCreateContactPage(activityTestRule);
            }
        };
    }

    public EspListView contactList() {
        return new EspListView(activityTestRule, R.id.listView);
    }
}
