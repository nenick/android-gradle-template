package com.example.activities;

import com.example.adapters.BookmarkAdapter;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(BookmarkAdapter.class)
public class BookmarkAdapterShadow {

    @Implementation
    public void afterInject() {
        // avoid rest calls
    }
}
