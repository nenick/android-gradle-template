package com.example.restexample;

import com.example.json.Bookmark;
import com.example.json.BookmarkList;

import org.androidannotations.annotations.rest.Delete;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Put;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

@Rest(converters = { MappingJacksonHttpMessageConverter.class })
public interface BookmarksClient {

    @Get("/bookmarks")
    BookmarkList getBookmarks();

    @Get("/bookmarks/{id}")
    Bookmark getBookmark(long id);

    @Get("/bookmarks/{match}")
    BookmarkList getBookmarks(String match);

    @Post("/bookmarks")
    void addBookmark(Bookmark bookmark);

    @Put("/bookmarks")
    void updateBookmark(Bookmark bookmark);

    @Delete("/bookmarks/{id}")
    Bookmark deleteBookmarkById(long id);

    void setRootUrl(String rootUrl);
}