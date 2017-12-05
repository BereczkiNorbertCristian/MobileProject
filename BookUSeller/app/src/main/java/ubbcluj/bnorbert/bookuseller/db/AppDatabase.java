package ubbcluj.bnorbert.bookuseller.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ubbcluj.bnorbert.bookuseller.model.Book;

/**
 * Created by bnorbert on 04.12.2017.
 */
@Database(entities = {Book.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    public abstract BookDao getBookDao();
}
