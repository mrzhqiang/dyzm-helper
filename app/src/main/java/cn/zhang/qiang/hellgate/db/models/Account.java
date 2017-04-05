package cn.zhang.qiang.hellgate.db.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import cn.zhang.qiang.hellgate.db.Db;
import cn.zhang.qiang.hellgate.db.DbModel;
import cn.zhang.qiang.hellgate.db.DbTable;

/**
 * <p>
 * Created by mrZQ on 2017/4/5.
 */

public final class Account implements DbModel {

    private long id = -1;
    public String username;
    public String password;
    public long uid;
    public long created = System.currentTimeMillis();

    @Override
    public void setValuesFromCursor(Cursor cursor) {
        id = Db.getLong(cursor, TABLE.COL_ID);

        username = Db.decode(Db.getString(cursor, TABLE.COL_USERNAME));
        password = Db.decode(Db.getString(cursor, TABLE.COL_PASSWORD));
        uid = Db.getLong(cursor, TABLE.COL_UID);
        created = Db.getLong(cursor, TABLE.COL_CREATE);
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues out = new ContentValues();
        if (id > 0) {
            out.put(TABLE.COL_ID, id);
        }

        out.put(TABLE.COL_USERNAME, Db.encode(username));
        out.put(TABLE.COL_PASSWORD, Db.encode(password));
        out.put(TABLE.COL_UID, uid);

        out.put(TABLE.COL_CREATE, created);
        return out;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public static class TABLE implements DbTable {
        public static final String NAME = "account";

        public static final String COL_ID = BaseColumns._ID;
        public static final String COL_USERNAME = "username";
        public static final String COL_PASSWORD = "password";
        public static final String COL_UID = "_uid";
        public static final String COL_CREATE = "created";

        @Override
        public String getCreateSql() {
            return "CREATE TABLE " + NAME + "("
                    + COL_ID + " INTEGER NOT NULL PRIMARY KEY,"
                    + COL_USERNAME + " TEXT NOT NULL,"
                    + COL_PASSWORD + " TEXT NOT NULL,"
                    + COL_UID + " INTEGER,"
                    + COL_CREATE + " INTEGER"
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
