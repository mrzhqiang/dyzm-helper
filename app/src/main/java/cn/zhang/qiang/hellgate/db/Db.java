package cn.zhang.qiang.hellgate.db;

import android.database.Cursor;
import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * <p>
 * Created by mrZQ on 2017/4/5.
 */

public final class Db {
    public static final int BOOLEAN_FALSE = 0;
    public static final int BOOLEAN_TRUE = 1;

    public static final Gson GSON = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .setPrettyPrinting()
            .create();

    public static String encode(String string) {
        if (!TextUtils.isEmpty(string)) {
            byte[] mByte = Base64.encode(string.getBytes(), Base64.DEFAULT);
            return new String(mByte);
        }
        return null;
    }

    public static String decode(String string) {
        if (!TextUtils.isEmpty(string)) {
            byte[] mByte = Base64.decode(string.getBytes(), Base64.DEFAULT);
            return new String(mByte);
        }
        return null;
    }

    public static String getString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static boolean getBoolean(Cursor cursor, String columnName) {
        return getLong(cursor, columnName) == BOOLEAN_TRUE;
    }

    public static long getLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    public static int getInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static double getDouble(Cursor cursor, String columnName) {
        return cursor.getDouble(cursor.getColumnIndex(columnName));
    }

    private Db() {
        throw new AssertionError("No instances.");
    }
}
