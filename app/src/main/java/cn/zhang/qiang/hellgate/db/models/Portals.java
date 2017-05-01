package cn.zhang.qiang.hellgate.db.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.LongSparseArray;

import java.util.Arrays;
import java.util.List;

import cn.zhang.qiang.hellgate.db.Db;
import cn.zhang.qiang.hellgate.db.DbTable;
import cn.zhang.qiang.hellgate.utils.TimeHelper;

import static android.provider.BaseColumns._ID;

/**
 * 门户网站 表
 * <p>
 * Created by mrZQ on 2017/4/8.
 */

public final class Portals {

    private long id = -1;
    public String title;
    public String domain;
    public long created;

    public Portals() {
        this.created = System.currentTimeMillis();
    }

    public String getCreated() {
        return TimeHelper.showTime(created);
    }

    public void setValuesFromCursor(Cursor cursor) {
        id = Db.getLong(cursor, _ID);
        title = Db.getString(cursor, Table.COL_TITLE);
        domain = Db.getString(cursor, Table.COL_DOMAIN);
        created = Db.getLong(cursor, Table.COL_CREATED);
    }

    public ContentValues toContentValues() {
        ContentValues out = new ContentValues();

        if (id > 0) {
            out.put(_ID, id);
        }
        out.put(Table.COL_TITLE, title);
        out.put(Table.COL_DOMAIN, domain);
        out.put(Table.COL_CREATED, created);

        return out;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public static class Table implements DbTable {
        public static final String NAME = "portals";

        public static final String COL_TITLE = "title";
        public static final String COL_DOMAIN = "domain";
        public static final String COL_CREATED = "created";

        static final String CREATE_TABLE = "CREATE Table " + NAME + "("
                + _ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + COL_TITLE + " TEXT NOT NULL UNIQUE,"
                + COL_DOMAIN + " TEXT NOT NULL UNIQUE,"
                + COL_CREATED + " INTEGER NOT NULL"
                + ");";

        @Override
        public String[] getCreateSql() {
            return new String[] {
                    CREATE_TABLE
            };
        }

        @Override
        public LongSparseArray<List<String>> getUpgrade() {
            LongSparseArray<List<String>> ups = new LongSparseArray<>();
            ups.put(1, Arrays.asList(getCreateSql()));
            return ups;
        }

        @Override
        public LongSparseArray<List<String>> getDowngrade() {
            return null;
        }
    }

}
