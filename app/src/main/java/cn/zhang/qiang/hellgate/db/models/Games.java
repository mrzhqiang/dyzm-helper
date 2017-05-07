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
 * 游戏 表
 * <p>
 * Created by mrZQ on 2017/4/8.
 */

public class Games {

    private long id = -1;
    public String name;
    public String host;
    public boolean isSupport = true;
    public long created;
    public long portalId;

    public String portalTitle;
    public String portalDomain;

    public Games() {
        this.created = System.currentTimeMillis();
    }

    public String getCreated() {
        return TimeHelper.showTime(created);
    }

    public void setValuesFromCursor(Cursor cursor) {
        id = Db.getLong(cursor, _ID);
        name = Db.getString(cursor, Table.COL_NAME);
        host = Db.getString(cursor, Table.COL_HOST);
        isSupport = Db.getBoolean(cursor, Table.COL_IS_SUPPORT);
        created = Db.getLong(cursor, Table.COL_CREATED);
        portalId = Db.getLong(cursor, Table.COL_PORTAL_ID);

        // 查询通常是多表联查
        portalTitle = Db.getString(cursor, Portal.Table.COL_TITLE);
        portalDomain = Db.getString(cursor, Portal.Table.COL_DOMAIN);
    }

    public ContentValues toContentValues() {
        ContentValues out = new ContentValues();

        if (id > 0) {
            out.put(_ID, id);
        }
        out.put(Table.COL_NAME, name);
        out.put(Table.COL_HOST, host);
        out.put(Table.COL_IS_SUPPORT, isSupport ? Db.BOOLEAN_TRUE : Db.BOOLEAN_FALSE);
        out.put(Table.COL_CREATED, created);
        out.put(Table.COL_PORTAL_ID, portalId);

        // 外键联查的数据不用搭理
        return out;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public static class Table implements DbTable {
        public static final String NAME = "games";

        public static final String COL_NAME = "name";
        public static final String COL_HOST = "host";
        public static final String COL_IS_SUPPORT = "is_support";
        public static final String COL_CREATED = "created";
        public static final String COL_PORTAL_ID = "portal_id";

        static final String CREATE_TABLE = "CREATE Table " + NAME + "("
                + _ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + COL_NAME + " TEXT NOT NULL UNIQUE,"
                + COL_HOST + " TEXT NOT NULL UNIQUE,"
                + COL_IS_SUPPORT + " INTEGER NOT NULL DEFAULT 1,"
                + COL_CREATED + " INTEGER NOT NULL,"
                + COL_PORTAL_ID + " INTEGER NOT NULL REFERENCES "
                + Portal.Table.NAME + "(" + _ID + ") ON DELETE CASCADE"
                + ");";// 父表是门户网站，删除则应该级联删除旗下的游戏列表

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
