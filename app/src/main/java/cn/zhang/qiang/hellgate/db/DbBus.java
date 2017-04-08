package cn.zhang.qiang.hellgate.db;

import android.content.Context;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import cn.qiang.zhang.logger.Log;
import rx.schedulers.Schedulers;

/**
 * <p>
 * Created by mrZQ on 2017/4/5.
 */

public final class DbBus {
    private static final String TAG = "DbBus";

    private static BriteDatabase db;

    public static BriteDatabase getDb() {
        if (db == null) {
            throw new RuntimeException("please invoke DbBus.initDb() first.");
        }
        return db;
    }

    public static void initDb(Context context) {
        if (db == null) {
            db = provideDatabase(provideSqlBrite(), DbHelper.getInstance(context));
        }
    }

    public static void close() {
        if (db != null) {
            db.close();
        }
    }

    private static SqlBrite provideSqlBrite() {
        return new SqlBrite.Builder().logger(new SqlBrite.Logger() {
            @Override
            public void log(String message) {
                Log.d(TAG, message);
            }
        }).build();
    }

    private static BriteDatabase provideDatabase(SqlBrite sqlBrite, DbHelper dbHelper) {
        BriteDatabase db = sqlBrite.wrapDatabaseHelper(dbHelper, Schedulers.io());
        db.setLoggingEnabled(true);
        return db;
    }

    private DbBus() {
        throw new AssertionError("No instances.");
    }
}
