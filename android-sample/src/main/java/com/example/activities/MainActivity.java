package com.example.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.R;
import com.example.adapters.AgendaAdapter;
import com.example.adapters.AgendaLoader;
import com.example.database.table.AgendaTable;
import com.example.model.Agenda;
import com.example.provider.AgendaProvider;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.text.DateFormat;
import java.util.Date;

@EActivity(R.layout.activity_main)
public class MainActivity extends ListActivity {

    @ViewById(R.id.editTextNote)
    EditText editText;

    private AgendaAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new AgendaAdapter(this, null, 0);
        setListAdapter(mAdapter);
        getLoaderManager().initLoader(0, null, new AgendaLoader(this, mAdapter));
    }

    @Click(R.id.add)
    public void addNote() {
        String noteText = editText.getText().toString();
        editText.setText("");

        final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        String comment = "Added on " + df.format(new Date());

        Agenda agenda = new Agenda();
        agenda.setName(noteText);
        agenda.setDate(comment);

        getContentResolver().insert(AgendaProvider.AGENDA_CONTENT_URI, agenda.getContentValues());
    }

    @Click(R.id.delete)
    public void deleteAgenda() {
        long itemId = mAdapter.getItemId(0);
        getContentResolver().delete(AgendaProvider.AGENDA_CONTENT_URI, AgendaTable._ID + " = ?", new String[]{String.valueOf(itemId)});
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

    }
}
