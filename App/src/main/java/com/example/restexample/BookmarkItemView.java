package com.example.restexample;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.json.Bookmark;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(android.R.layout.simple_list_item_2)
public class BookmarkItemView extends LinearLayout {

    @ViewById(android.R.id.text1)
    TextView textViewId;

    @ViewById(android.R.id.text2)
    TextView textViewName;

    public BookmarkItemView(Context context) {
        super(context);
    }


    public void bind(Bookmark product) {
        if (product != null) {
            textViewId.setText(String.valueOf(product.getId()));
            textViewName.setText(product.getName());
        }
    }
}
