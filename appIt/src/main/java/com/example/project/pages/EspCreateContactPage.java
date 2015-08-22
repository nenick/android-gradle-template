package com.example.project.pages;

import android.support.test.rule.ActivityTestRule;

import com.example.project.R;
import com.example.project.espresso.EspButton;
import com.example.project.espresso.EspTextEdit;

public class EspCreateContactPage {

    ActivityTestRule activityTestRule;

    public EspCreateContactPage(ActivityTestRule activityTestRule) {
        this.activityTestRule = activityTestRule;
    }

    public EspTextEdit firstName() {
        return new EspTextEdit(R.id.first_name);
    }

    public EspTextEdit lastName() {
        return new EspTextEdit(R.id.last_name);
    }

    public EspButton<EspContactListPage> confirm() {
        return new EspButton<EspContactListPage>(R.id.confirm) {
            @Override
            public EspContactListPage click() {
                super.click();
                return new EspContactListPage(activityTestRule);
            }
        };
    }

    public EspTextEdit birthDate() {
        return new EspTextEdit(R.id.birth_date);
    }
}
