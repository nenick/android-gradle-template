package com.example.project.view.common.mvp;

/**
 * Base presenter.
 *
 * The wrapped life cycles avoid the necessary of super method calls and let you easy write unit tests.
 */
public interface BasePresenter {

    /**
     * Called after the view is full created and injected.
     *
     * At this time you should start to initialise the view content. This will only be called
     * once in the life of the view.
     *
     * This gets be called at end very end of onCreate when Android Annotations reports the
     * @AfterViews event. But it is guarded so you will get this event only once, original the event
     * happen after each view change.
     *
     * You can still use the @AfterViews annotation and it will work with the original behavior.
     */
    void onViewCreated();

    /**
     * Called if the view is shown and the user may interact with it.
     *
     * May be called multiple times but only in combination with {@link #onViewPause()}.
     */
    void onViewResume();

    /**
     * Called when the view is going into background and the user can't anymore interact with it.
     *
     * This is the place to save persistent content.
     *
     * This is the same like onPause. Take also note of {@link android.app.Activity#onPause()}.
     * May be called multiple times but only in combination with {@link #onViewResume()}.
     */
    void onViewPause();

    /**
     * Called if the view gets destroyed.
     *
     * Do final clean up by releasing/stopping not more necessary processes.
     */
    void onViewDestroy();
}
