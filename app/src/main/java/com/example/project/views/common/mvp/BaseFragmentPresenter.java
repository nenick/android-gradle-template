package com.example.project.views.common.mvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.androidannotations.annotations.EFragment;

/**
 * Base presenter for fragments.
 */
@EFragment
public abstract class BaseFragmentPresenter extends Fragment implements BasePresenter {

    // wrapper for the life cycles

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onViewCreated();
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
