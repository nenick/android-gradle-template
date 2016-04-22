package com.example.app.views.contact_details;

import com.example.app.views.common.mvp.BaseActivityPresenter;
import com.example.app.R;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentById;

@EActivity(R.layout.activity_detail)
public class DetailActivity extends BaseActivityPresenter {

    @FragmentById(R.id.fragment_detail)
    DetailFragment detailFragment;

    @Extra
    protected long contactId;

    @Override
    public void onViewCreated() {
        detailFragment.onShowContact(contactId);
    }
}
