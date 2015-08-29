package com.example.project.views.common.mvp;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Base presenter for activities.
 */
@EActivity
public abstract class BaseActivityPresenter extends AppCompatActivity implements BasePresenter {

    /** Guard to avoid multiple calls to {@link BasePresenter#onViewCreated} */
    private boolean firstTimeOnAfterViews = true;

    // wrapper for the life cycles

    @AfterViews
    protected void baseOnAfterViews() {
        if (firstTimeOnAfterViews) {
            firstTimeOnAfterViews = false;
            onViewCreated();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        onViewResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        onViewPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onViewDestroy();
    }

    // don't force child classes to implement the life cycle methods

    @Override
    public void onViewCreated() {
    }

    @Override
    public void onViewResume() {
    }

    @Override
    public void onViewPause() {
    }

    @Override
    public void onViewDestroy() {
    }
}
