package com.raze.bookshelf.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.raze.bookshelf.model.entity.Book;

import java.util.List;

@Dao
public interface BookDAO {
    @Insert
    void insert(Book... books);

    @Update
    void update(Book... books);

    @Delete
    void delete(Book book);

    @Query("SELECT * FROM book")
    LiveData<List<Book>> getItems();

    @Query("SELECT * FROM book WHERE isbn = :isbn")
    Book getBookByIsbn(String isbn);

    @Query("DELETE FROM book")
    void deleteAllBooks();
}
