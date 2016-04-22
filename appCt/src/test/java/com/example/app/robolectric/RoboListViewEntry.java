package com.example.app.robolectric;

import android.view.View;
import android.widget.ListView;

import static org.assertj.core.api.Assertions.assertThat;

public class RoboListViewEntry {

    private ListView listView;
    private View listViewEntry;

    public RoboListViewEntry(ListView listView, View listViewEntry) {
        this.listView = listView;
        this.listViewEntry = listViewEntry;
    }

    public void click() {
        assertThat(listView.performItemClick(null, listView.getPositionForView(listViewEntry), 0)).isTrue();
    }
}
