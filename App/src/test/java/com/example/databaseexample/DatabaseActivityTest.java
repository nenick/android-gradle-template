package com.example.databaseexample;

import android.widget.Button;
import android.widget.TextView;

import com.example.R;
import com.example.test.support.UnitTestSpecification;

import org.fest.assertions.api.ANDROID;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class DatabaseActivityTest extends UnitTestSpecification {

    public static final String TEST_AGENDA = "MyAgenda";

    @Mock
    DatabasePresenter presenter;

    DatabaseActivity_ view = Robolectric.buildActivity(DatabaseActivity_.class).create().get();

    TextView input;
    Button add;
    Button delete;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        view.presenter = presenter;
        add = (Button) view.findViewById(R.id.add);
        input = (TextView) view.findViewById(R.id.editTextNote);
        delete = (Button) view.findViewById(R.id.delete);
    }

    @Test
    public void test_delegateAddAgenda() throws Exception {
        add.performClick();
        verify(presenter).onAddAgenda();
    }


    @Test
    public void test_delegateDeleteAgenda() throws Exception {
        delete.performClick();
        verify(presenter).onDeleteAgenda();
    }

    @Test
    public void test_getInputAgenda() {
        input.setText(TEST_AGENDA);
        assertThat(view.getInputAgenda()).isEqualTo(TEST_AGENDA);
    }

    @Test
    public void test_shouldResetAllInputFields() {
        input.setText(TEST_AGENDA);
        view.resetInputFields();
        ANDROID.assertThat(input).isEmpty();
    }
}
