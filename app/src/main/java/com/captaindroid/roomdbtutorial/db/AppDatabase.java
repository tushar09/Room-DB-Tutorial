package com.captaindroid.roomdbtutorial.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.captaindroid.roomdbtutorial.db.dao.BookDao;
import com.captaindroid.roomdbtutorial.db.tables.Book;

@Database(entities = {Book.class}, exportSchema = true, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BookDao getBookDao();
}