package com.example.project.tools;

import android.view.View;
import android.widget.ListView;

public class RoboListViewEntry {

    private ListView listView;
    private View listViewEntry;

    public RoboListViewEntry(ListView listView, View listViewEntry) {
        this.listView = listView;
        this.listViewEntry = listViewEntry;
    }

    public void click() {
        listView.performItemClick(null, listView.getPositionForView(listViewEntry), 0);
    }
}
