package ubbcluj.bnorbert.bookuseller.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ubbcluj.bnorbert.bookuseller.model.Book;

/**
 * Created by bnorbert on 04.12.2017.
 */
@Dao
public interface BookDao {

    @Query("select * from Book")
    List<Book> findAll();

    @Insert
    void save(Book... books);

    @Update
    void update(Book... books);

    @Delete
    void delete(Book... books);
}
