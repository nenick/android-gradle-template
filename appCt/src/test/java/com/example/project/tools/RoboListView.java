package com.example.project.tools;

import android.app.Activity;
import android.widget.ListView;

import org.robolectric.Shadows;

public class RoboListView {

    ListView listView;

    public RoboListView(Activity activity, int resourceId) {
        listView = (ListView) activity.findViewById(resourceId);
    }

    public int count() {
        Shadows.shadowOf(listView).populateItems();
        return listView.getCount();
    }

    public RoboListViewEntry entry(int pos) {
        Shadows.shadowOf(listView).populateItems();
        return new RoboListViewEntry(listView, listView.getChildAt(pos));
    }
}
