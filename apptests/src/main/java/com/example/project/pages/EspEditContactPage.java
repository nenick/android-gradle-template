package com.example.project.pages;

import com.example.app.R;
import com.example.project.espresso.EspButton;
import com.example.project.espresso.EspTextEdit;

public class EspEditContactPage {

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
                return new EspContactListPage();
            }
        };
    }

    public EspTextEdit birthDate() {
        return new EspTextEdit(R.id.birth_date);
    }
}
