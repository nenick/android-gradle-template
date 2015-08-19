package com.example.project.view.contact_list;

import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.project.R;
import com.example.project.view.common.mvp.BaseView;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.ViewById;

@EBean
public class ContactListView implements BaseView {

    @ViewById(R.id.listView)
    ListView contactList;

    public void setContactList(ListAdapter listAdapter) {
        contactList.setAdapter(listAdapter);
    }
}
