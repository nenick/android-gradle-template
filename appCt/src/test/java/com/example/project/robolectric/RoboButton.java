package com.example.project.robolectric;

import android.app.Activity;
import android.widget.Button;

import static org.assertj.core.api.Assertions.assertThat;

public class RoboButton {

    Button button;

    public RoboButton(Activity activity, int resourceId) {
        button = (Button) activity.findViewById(resourceId);
    }

    public void click() {
        assertThat(button.performClick()).isTrue();
    }
}
