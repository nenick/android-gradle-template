package com.example.project.view.contact_details;

import com.example.project.R;
import com.example.project.view.common.mvp.BaseActivityPresenter;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentById;

@EActivity()
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
