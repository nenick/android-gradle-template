package com.example.project.views.contact_list;

import android.util.Log;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.business.contact_sync.SyncContactsFunction;
import com.example.project.views.common.AppIdlingResources;
import com.example.project.views.common.mvp.BaseActivityPresenter;
import com.example.project.views.contact_details.DetailActivityIntent;
import com.example.project.views.contact_details.DetailFragment;
import com.example.project.views.contact_edit.EditActivityIntent;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class ContactListActivity extends BaseActivityPresenter implements ShowContactListener {

    @FragmentById(R.id.fragment_list)
    ContactListFragment contactListFragment;

    @FragmentById(R.id.fragment_detail)
    DetailFragment detailFragment;

    @Bean
    DetailActivityIntent detailActivityIntent;

    @Bean
    EditActivityIntent editActivityIntent;

    @Bean
    SyncContactsFunction syncContactsFunction;

    @Bean
    AppIdlingResources appIdlingResources;

    @Override
    public void onViewCreated() {
        contactListFragment.setShowContactListener(this);
    }

    @OptionsItem(R.id.action_add_contact)
    public void onCreateContact() {
        editActivityIntent.start();
    }

    @OptionsItem(R.id.action_sync_contacts)
    @Background
    void onSyncContacts() {
        Log.e("sync", "sync start");
        appIdlingResources.increment();
        SyncContactsFunction.Result result = syncContactsFunction.apply();
        showSyncResult(result);
        appIdlingResources.decrement();
        Log.e("sync", "sync finish");
    }

    @UiThread(propagation = UiThread.Propagation.REUSE)
    void showSyncResult(SyncContactsFunction.Result result) {
        if(result.successful) {
            Log.e("sync", "sync success");
            Toast.makeText(this, "Sync done", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("sync", "sync failed");
            Toast.makeText(this, "Sync failed: " + result.errorReason, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onShowContact(long contactId) {
        if(detailFragment == null) {
            detailActivityIntent.start(contactId);
        } else {
            detailFragment.onShowContact(contactId);
        }
    }
}
