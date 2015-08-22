package com.example.project.espresso;

import android.widget.ListView;

public class EspListView {
    private int resourceId;

    public EspListView(int resourceId) {
        this.resourceId = resourceId;
    }

    public int count() {
        ListView listView = (ListView) CurrentActivity.get().findViewById(resourceId);
        return listView.getCount();
    }
}
