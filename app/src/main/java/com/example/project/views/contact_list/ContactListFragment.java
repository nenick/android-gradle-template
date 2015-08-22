package com.example.project.views.contact_list;

import com.example.project.R;
import com.example.project.database.contact.ContactCursor;
import com.example.project.views.common.mvp.BaseFragmentPresenter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;

@EFragment(R.layout.fragment_list)
public class ContactListFragment extends BaseFragmentPresenter {

    @Bean
    ContactListView view;

    @Bean
    ContactAdapterLoader contactsLoader;

    private ShowContactListener showContactListener;

    @Override
    public void onViewCreated() {
        view.showContacts(contactsLoader.getCursorAdapter());
        contactsLoader.start();
    }

    @ItemClick(R.id.listView)
    public void onContactClick(ContactCursor item) {
        showContactListener.onShowContact(item.getId());
    }

    public void setShowContactListener(ShowContactListener showContactListener) {
        this.showContactListener = showContactListener;
    }
}
