package com.example.restexample;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.R;

import com.example.json.Bookmark;
import com.example.json.BookmarkList;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.rest.RestService;

@EBean
public class BookmarkAdapter extends BaseAdapter {
    BookmarkList products = new BookmarkList();

    @RestService
    BookmarksClient bookmarksClient;

    @RootContext
    Context context;

    @AfterInject
    void afterInject() {
        String ip = context.getResources().getString(R.string.const_wiremock_ip);
        bookmarksClient.setRootUrl("http://" + ip + ":9999");
        new LoadAllProducts().execute();
    }

    @Override
    public int getCount() {
        return products.getBookmarks().size();
    }

    @Override
    public Bookmark getItem(int position) {
        return products.getBookmarks().get(position);
    }

    @Override
    public long getItemId(int position) {
        return products.getBookmarks().get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BookmarkItemView bookmarkItemView;
        if (convertView == null) {
            bookmarkItemView = BookmarkItemView_.build(context);
        } else {
            bookmarkItemView = (BookmarkItemView) convertView;
        }

        bookmarkItemView.bind(getItem(position));

        return bookmarkItemView;
    }

    class LoadAllProducts extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            products = bookmarksClient.getBookmarks();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            notifyDataSetChanged();
            super.onPostExecute(s);
        }
    }
}
