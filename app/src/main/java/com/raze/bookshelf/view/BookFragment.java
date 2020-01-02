package com.raze.bookshelf.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.raze.bookshelf.AddEditBookActivity;
import com.raze.bookshelf.R;
import com.raze.bookshelf.model.entity.Book;
import com.raze.bookshelf.view.adapter.BookAdapter;
import com.raze.bookshelf.view.model.BookViewModel;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class BookFragment extends Fragment {

    public static final int ADD_BOOK_REQUEST = 1;
    public static final int EDIT_BOOK_REQUEST = 2;

    private BookViewModel bookViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_book, container, false);

        FloatingActionButton buttonAddBook = view.findViewById(R.id.floating_new_book_btn);
        buttonAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddEditBookActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.book_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final BookAdapter adapter = new BookAdapter();
        recyclerView.setAdapter(adapter);

        bookViewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        bookViewModel.getAllBooks().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(@Nullable List<Book> books) {
                adapter.setBooks(books);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                bookViewModel.delete(adapter.getBookAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Book deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                Intent intent = new Intent(getActivity(), AddEditBookActivity.class);
                intent.putExtra(AddEditBookActivity.EXTRA_ID, book.getId());
                intent.putExtra(AddEditBookActivity.EXTRA_TITLE, book.getTitle());
                intent.putExtra(AddEditBookActivity.EXTRA_ISBN, book.getIsbn());
                intent.putExtra(AddEditBookActivity.EXTRA_AUTHOR, book.getAuthor());
                startActivityForResult(intent, EDIT_BOOK_REQUEST);
            }
        });
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_BOOK_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditBookActivity.EXTRA_TITLE);
            String author = data.getStringExtra(AddEditBookActivity.EXTRA_AUTHOR);
            String isbn = data.getStringExtra(AddEditBookActivity.EXTRA_ISBN);

            Book book = new Book(isbn, title, author);
            bookViewModel.insert(book);

            Toast.makeText(getActivity(), "Book saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_BOOK_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditBookActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(getActivity(), "Book can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditBookActivity.EXTRA_TITLE);
            String author = data.getStringExtra(AddEditBookActivity.EXTRA_AUTHOR);
            String isbn = data.getStringExtra(AddEditBookActivity.EXTRA_ISBN);

            Book book = new Book(isbn, title, author);
            book.setId(id);
            bookViewModel.update(book);

            Toast.makeText(getActivity(), "Book updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Book not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_book_btn:
                bookViewModel.deleteAll();
                Toast.makeText(getActivity(), "All books deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }
}
