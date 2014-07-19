package com.example.restexample;

import com.example.restexample.BookmarkAdapter;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(BookmarkAdapter.class)
public class BookmarkAdapterShadow {

    @Implementation
    public void afterInject() {
        // avoid rest calls
    }
}
