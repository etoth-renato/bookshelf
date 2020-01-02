package com.raze.bookshelf.view.author;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.raze.bookshelf.R;

public class AddEditAuthorActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.raze.bookshelf.author.EXTRA_ID";
    public static final String EXTRA_NAME = "com.raze.bookshelf.author.EXTRA_NAME";
    public static final String EXTRA_BORN_YEAR = "com.raze.bookshelf.author.EXTRA_BORN_YEAR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_author);
    }
}
