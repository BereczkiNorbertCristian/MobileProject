package ubbcluj.bnorbert.bookuseller.model;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by bnorbert on 04.12.2017.
 */

public class DateConverter {
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date.getTime();
    }
}
