package com.example.project.views.contact_list;

import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.project.R;
import com.example.project.views.common.mvp.BaseView;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.ViewById;

@EBean
public class ContactListView implements BaseView {

    @ViewById(R.id.listView)
    ListView contactList;

    public void showContacts(ListAdapter listAdapter) {
        contactList.setAdapter(listAdapter);
    }
}
