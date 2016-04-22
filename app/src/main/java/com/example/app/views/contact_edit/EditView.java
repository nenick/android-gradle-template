package com.example.app.views.contact_edit;

import android.widget.EditText;

import com.example.app.R;
import com.example.app.views.common.mvp.BaseView;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.ViewById;

@EBean
public class EditView implements BaseView {

    @ViewById(R.id.first_name)
    EditText firstName;

    @ViewById(R.id.last_name)
    EditText lastName;

    @ViewById(R.id.birth_date)
    EditText birthDate;

    public void setFirstName(String input) {
        firstName.setText(input);
    }

    public String getFirstName() {
        return firstName.getText().toString();
    }

    public void setLastName(String input) {
        lastName.setText(input);
    }

    public String getLastName() {
        return lastName.getText().toString();
    }

    public void setBirthDate(String input) {
        birthDate.setText(input);
    }

    public String getBirthDate() {
        return birthDate.getText().toString();
    }
}
