package cn.zhang.qiang.hellgate.db.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import cn.zhang.qiang.hellgate.db.Db;
import cn.zhang.qiang.hellgate.db.DbModel;
import cn.zhang.qiang.hellgate.db.DbTable;

/**
 * 服务器列表
 * <p>
 * 从 http://haowaba.com 获取，第一次自动拉取，后续交由用户刷新
 * <p>
 * Created by mrZQ on 2017/4/5.
 */

public final class ServiceList implements DbModel {

    private long id = -1;
    public String url;
    public String name;

    @Override
    public void setValuesFromCursor(Cursor cursor) {
        id = Db.getLong(cursor, TABLE.COL_ID);

        url = Db.getString(cursor, TABLE.COL_URL);
        name = Db.getString(cursor, TABLE.NAME);
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues out = new ContentValues();
        if (id > 0) {
            out.put(TABLE.COL_ID, id);
        }

        out.put(TABLE.COL_URL, url);
        out.put(TABLE.COL_NAME, name);
        return out;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public static class TABLE implements DbTable {
        public static final String NAME = "service_list";

        public static final String COL_ID = BaseColumns._ID;
        public static final String COL_URL = "_url";
        public static final String COL_NAME = "name";

        @Override
        public String getCreateSql() {
            return "CREATE TABLE " + NAME + "("
                    + COL_ID + " INTEGER NOT NULL PRIMARY KEY,"
                    + COL_URL + " TEXT NOT NULL,"
                    + COL_NAME + " TEXT NOT NULL"
                    + ");";
        }

        @Override
        public String getDropSql() {
            return "DROP TABLE IF EXISTS " + NAME + ";";
        }

        @Override
        public String[] getUpgrade() {
            return new String[]{
                    getCreateSql()
            };
        }
    }

}
