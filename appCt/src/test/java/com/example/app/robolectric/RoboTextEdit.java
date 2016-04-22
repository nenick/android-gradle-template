package com.example.app.robolectric;

import android.app.Activity;
import android.widget.EditText;

public class RoboTextEdit {

    EditText editText;

    public RoboTextEdit(Activity activity, int resourceId) {
        editText = (EditText) activity.findViewById(resourceId);
    }

    public void insert(String text) {
        editText.setText(text);
    }
}
