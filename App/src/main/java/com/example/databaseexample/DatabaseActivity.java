package com.example.databaseexample;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.R;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_database)
public class DatabaseActivity extends ListActivity {

    @ViewById(R.id.editTextNote)
    EditText editText;

    @Bean
    DatabasePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.viewOnCreate(savedInstanceState);
    }

    @Click(R.id.add)
    public void onAddAgenda() {
        presenter.onAddAgenda();
    }

    public String getInputAgenda() {
        return editText.getText().toString();
    }

    public void resetInputFields() {
        editText.setText("");
    }

    @Click(R.id.delete)
    public void onDeleteAgenda() {
        presenter.onDeleteAgenda();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

    }
}
