package com.example.project.view.contact_list;

import com.example.project.R;
import com.example.project.view.common.mvp.BaseFragmentPresenter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.fragment_list)
public class ContactListFragment extends BaseFragmentPresenter {

    @Bean
    ContactListView view;

    @Bean
    ContactAdapterLoader contactsLoader;

    private ShowContactListener showContactListener;

    @Override
    public void onViewResume() {
        view.showContacts(contactsLoader.getCursorAdapter());
        contactsLoader.start();
    }
}
