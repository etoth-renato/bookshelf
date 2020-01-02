package com.raze.bookshelf.model.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.raze.bookshelf.model.AppDatabase;
import com.raze.bookshelf.model.dao.BookDAO;
import com.raze.bookshelf.model.entity.Book;

import java.util.List;

public class BookRepository {

    private BookDAO bookDAO;
    private LiveData<List<Book>> allBook;

    public BookRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        bookDAO = database.bookDAO();
        allBook = bookDAO.getItems();
    }

    public void insert(Book book) {
        new InsertBookAsyncTask(bookDAO).execute(book);
    }

    public void update(Book book) {
        new UpdateBookAsyncTask(bookDAO).execute(book);
    }

    public void delete(Book book) {
        new DeleteBookAsyncTask(bookDAO).execute(book);
    }
    public void deleteAllBooks() {
        new DeleteAllBookAsyncTask(bookDAO).execute();
    }

    public LiveData<List<Book>> getAllBook() {
        return allBook;
    }

    private static class InsertBookAsyncTask extends AsyncTask<Book, Void, Void> {
        private BookDAO bookDAO;

        private InsertBookAsyncTask(BookDAO bookDAO) {
            this.bookDAO = bookDAO;
        }

        @Override
        protected Void doInBackground(Book... books) {
            bookDAO.insert(books[0]);
            return null;
        }
    }

    private static class UpdateBookAsyncTask extends AsyncTask<Book, Void, Void> {
        private BookDAO bookDAO;

        private UpdateBookAsyncTask(BookDAO bookDAO) {
            this.bookDAO = bookDAO;
        }

        @Override
        protected Void doInBackground(Book... books) {
            bookDAO.update(books[0]);
            return null;
        }
    }

    private static class DeleteBookAsyncTask extends AsyncTask<Book, Void, Void> {
        private BookDAO bookDAO;

        private DeleteBookAsyncTask(BookDAO bookDAO) {
            this.bookDAO = bookDAO;
        }

        @Override
        protected Void doInBackground(Book... books) {
            bookDAO.delete(books[0]);
            return null;
        }
    }

    private static class DeleteAllBookAsyncTask extends AsyncTask<Book, Void, Void> {
        private BookDAO bookDAO;

        private DeleteAllBookAsyncTask(BookDAO bookDAO) {
            this.bookDAO = bookDAO;
        }

        @Override
        protected Void doInBackground(Book... books) {
            bookDAO.deleteAllBooks();
            return null;
        }
    }
}
