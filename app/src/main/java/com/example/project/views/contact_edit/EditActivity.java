package com.example.project.views.contact_edit;

import com.example.project.R;
import com.example.project.views.common.mvp.BaseActivityPresenter;
import com.example.project.views.common.testwrapper.ViewFinisher;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentById;

@EActivity(R.layout.activity_edit)
public class EditActivity extends BaseActivityPresenter implements EditConfirmedListener {

    @FragmentById(R.id.fragment_edit)
    EditFragment editFragment;

    @Bean
    ViewFinisher viewFinisher;

    @Extra
    protected long contactId;

    @Override
    public void onViewCreated() {
        editFragment.setEditConfirmedListener(this);
        editFragment.onShowContact(contactId);
    }

    @Override
    public void onEditConfirmed() {
        viewFinisher.finish();
    }
}
