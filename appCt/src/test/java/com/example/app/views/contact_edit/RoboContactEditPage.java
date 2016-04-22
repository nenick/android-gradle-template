package com.example.app.views.contact_edit;

import com.example.app.robolectric.RoboButton;
import com.example.app.robolectric.RoboTextEdit;
import com.example.project.R;
import com.example.project.views.contact_edit.EditActivity;
import com.example.project.views.contact_edit.EditActivity_;

import org.robolectric.Robolectric;

public class RoboContactEditPage {
    EditActivity editActivity;

    public void startPage() {
        editActivity = Robolectric.setupActivity(EditActivity_.class);
    }

    public RoboTextEdit firstName() {
        return new RoboTextEdit(editActivity, R.id.first_name);
    }

    public RoboTextEdit lastName() {
        return new RoboTextEdit(editActivity, R.id.last_name);
    }

    public RoboTextEdit birthDate() {
        return new RoboTextEdit(editActivity, R.id.birth_date);
    }

    public RoboButton confirm() {
        return new RoboButton(editActivity, R.id.confirm);
    }
}
