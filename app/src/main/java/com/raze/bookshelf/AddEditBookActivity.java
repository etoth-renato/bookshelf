package com.raze.bookshelf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditBookActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.raze.bookshelf.EXTRA_ID";
    public static final String EXTRA_ISBN = "com.raze.bookshelf.EXTRA_ISBN";
    public static final String EXTRA_TITLE = "com.raze.bookshelf.EXTRA_TITLE";
    public static final String EXTRA_AUTHOR = "com.raze.bookshelf.EXTRA_AUTHOR";

    private EditText editTextISBN;
    private EditText editTextTitle;
    private EditText editTextAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        editTextISBN = findViewById(R.id.edit_text_isbn);
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextAuthor = findViewById(R.id.edit_text_author);

        getSupportActionBar().setHomeAsUpIndicator(createCancelIcon());

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Book");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextISBN.setText(intent.getStringExtra(EXTRA_ISBN));
            editTextAuthor.setText(intent.getStringExtra(EXTRA_AUTHOR));
        } else {
            setTitle("Add Book");
        }
    }

    private Drawable createCancelIcon() {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), android.R.drawable.ic_menu_close_clear_cancel, null);
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(getApplicationContext(), R.color.primaryTextColor));
        return drawable;
    }

    private void saveBook() {
        String title = editTextTitle.getText().toString();
        String author = editTextAuthor.getText().toString();
        String isbn = editTextISBN.getText().toString();

        if (isbn.trim().isEmpty() || title.trim().isEmpty() || author.trim().isEmpty()) {
            Toast.makeText(this, "Please insert an isbn, title and an author", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_AUTHOR, author);
        data.putExtra(EXTRA_ISBN, isbn);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_book:
                saveBook();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
