package com.captaindroid.roomdbtutorial.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.captaindroid.roomdbtutorial.db.tables.Book;

import java.util.List;

@Dao
public interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBook(Book book);

    @Update
    int updateBook(Book book);

    @Query("Select * from Book")
    List<Book> getAllBooks();
}
