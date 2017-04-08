package cn.zhang.qiang.hellgate.db.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.util.LongSparseArray;

import java.util.Arrays;
import java.util.List;

import cn.zhang.qiang.hellgate.db.Db;
import cn.zhang.qiang.hellgate.db.DbModel;
import cn.zhang.qiang.hellgate.db.DbTable;
import cn.zhang.qiang.hellgate.utils.TimeHelper;

/**
 * 账号 表
 * <p>
 * Created by mrZQ on 2017/4/5.
 */

public final class Accounts implements DbModel {

    private long id = -1;
    public String username;
    public String password;
    public String uid;
    public String remark;
    public long created;

    public Accounts() {
        this.created = System.currentTimeMillis();
    }

    public String getCreated() {
        return TimeHelper.showTime(created);
    }

    @Override
    public void setValuesFromCursor(Cursor cursor) {
        id = Db.getLong(cursor, Table.COL_ID);
        username = Db.decode(Db.getString(cursor, Table.COL_USERNAME));
        password = Db.decode(Db.getString(cursor, Table.COL_PASSWORD));
        uid = Db.getString(cursor, Table.COL_UID);
        remark = Db.getString(cursor, Table.COL_REMARK);
        created = Db.getLong(cursor, Table.COL_CREATE);
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues out = new ContentValues();

        if (id > 0) {
            out.put(Table.COL_ID, id);
        }
        out.put(Table.COL_USERNAME, Db.encode(username));
        out.put(Table.COL_PASSWORD, Db.encode(password));
        out.put(Table.COL_UID, uid);
        out.put(Table.COL_REMARK, remark);
        out.put(Table.COL_CREATE, created);

        return out;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public static class Table implements DbTable {
        public static final String NAME = "account";

        public static final String COL_ID = BaseColumns._ID;
        public static final String COL_USERNAME = "username";
        public static final String COL_PASSWORD = "password";
        public static final String COL_UID = "_uid";
        public static final String COL_REMARK = "remark";
        public static final String COL_CREATE = "created";

        public static final String CREATE_TABLE = "CREATE Table " + NAME + "("
                + COL_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + COL_USERNAME + " TEXT NOT NULL UNIQUE,"
                + COL_PASSWORD + " TEXT NOT NULL,"
                + COL_UID + " TEXT,"
                + COL_REMARK + "TEXT,"
                + COL_CREATE + " INTEGER NOT NULL"
                + ");";

        @Override
        public String[] getCreateSql() {
            return new String[]{
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
