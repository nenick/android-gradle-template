package com.example.restexample;

import android.app.ListActivity;
import android.widget.EditText;

import com.example.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

@EActivity(R.layout.activity_rest)
public class RestActivity extends ListActivity {

    @RestService
    BookmarksClient restClient;

    @ViewById(R.id.editTextNote)
    EditText search;

    @Bean
    BookmarkAdapter adapter;

    @AfterViews
    void initBookmarkList() {
        setListAdapter(adapter);
    }
/*
    @Click(R.id.add)
    void updateBookmarksClicked() {
        searchAsync(search.getText().toString());
    }

    @Background
    void searchAsync(String searchString) {
        BookmarkList bookmarks = restClient.getBookmarks(searchString);
        updateBookmarks(bookmarks);
    }

    @UiThread
    void updateBookmarks(BookmarkList bookmarks) {
        List<Bookmark> bookmarks1 = bookmarks.subList(0, bookmarks.size());
        adapter.addAll(bookmarks1);
    }*/
}
