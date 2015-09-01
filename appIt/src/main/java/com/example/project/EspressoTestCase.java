package com.example.project;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.WindowManager;

import com.example.project.database.provider.address.AddressSelection;
import com.example.project.database.provider.contact.ContactSelection;
import com.example.project.views.common.AppIdlingResources;
import com.example.project.views.common.AppIdlingResources_;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import java.lang.reflect.ParameterizedType;

@RunWith(AndroidJUnit4.class)
public abstract class EspressoTestCase<A extends Activity> {

    @Rule
    public ActivityTestRule<A> activityRule = new ActivityTestRule<>(getGenericActivityClass());

    @Before
    public void setupEspresso() {
        Espresso.registerIdlingResources(AppIdlingResources_.getInstance_(InstrumentationRegistry.getContext()).getIdlingResource());

        clearDatabase();
        avoidLockScreen();
    }

    private void clearDatabase() {
        new ContactSelection().delete(InstrumentationRegistry.getContext().getContentResolver());
        new AddressSelection().delete(InstrumentationRegistry.getContext().getContentResolver());
    }

    private void avoidLockScreen() {
        // sometimes tests failed on emulator, following approach should avoid it
        // http://stackoverflow.com/questions/22737476/false-positives-junit-framework-assertionfailederror-edittext-is-not-found
        // http://developer.android.com/reference/android/view/WindowManager.LayoutParams.html#FLAG_SHOW_WHEN_LOCKED
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                Activity activity = activityRule.getActivity();
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        });
    }

    private Class<A> getGenericActivityClass() {
        //noinspection unchecked
        return ((Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }
}
