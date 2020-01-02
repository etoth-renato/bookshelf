package com.raze.bookshelf.model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.raze.bookshelf.model.dao.BookDAO;
import com.raze.bookshelf.model.entity.Book;

@Database(entities = {Book.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract BookDAO bookDAO();

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room
                    .databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class, "AppDatabase")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


    private static AppDatabase.Callback roomCallback = new AppDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PupulateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class PupulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private BookDAO bookDAO;

        private PupulateDbAsyncTask(AppDatabase db) {
            bookDAO = db.bookDAO();
        }

        protected Void doInBackground(Void... voids) {
            bookDAO.insert(new Book("65481887451264", "Title1", "Author 1"));
            bookDAO.insert(new Book("99648248874112", "Title2", "Author 2"));
            bookDAO.insert(new Book("56185483214458", "Title3", "Author 3"));
            bookDAO.insert(new Book("56185483214458", "Title4", "Author 1"));
            bookDAO.insert(new Book("56345483214458", "Title5", "Author 3"));
            bookDAO.insert(new Book("56185483214458", "Title6", "Author 3"));
            bookDAO.insert(new Book("56185483214458", "Title7", "Author 2"));
            bookDAO.insert(new Book("56185483214458", "Title8", "Author 3"));
            bookDAO.insert(new Book("56185483214458", "Title9", "Author 3"));
            bookDAO.insert(new Book("56185483214458", "Title10", "Author 1"));
            bookDAO.insert(new Book("56185483214458", "Title11", "Author 3"));
            bookDAO.insert(new Book("56185483214458", "Title12", "Author 2"));
            bookDAO.insert(new Book("56185483214458", "Title13", "Author 3"));

            return null;
        }
    }

}
