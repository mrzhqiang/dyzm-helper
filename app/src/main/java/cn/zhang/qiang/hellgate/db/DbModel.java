package cn.zhang.qiang.hellgate.db;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * @author Aleksandar Gotev
 */
public interface DbModel {
    void setValuesFromCursor(Cursor cursor);
    ContentValues toContentValues();
    void setId(long id);
}
