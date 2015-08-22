package com.example.project.views.contact_edit;

import com.example.project.R;
import com.example.project.views.common.mvp.BaseActivityPresenter;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentById;

@EActivity()
public class EditActivity extends BaseActivityPresenter {

    @FragmentById(R.id.fragment_detail)
    EditFragment editFragment;

    @Extra
    protected long contactId;

    @Override
    public void onViewCreated() {
        editFragment.onShowContact(contactId);
    }
}
