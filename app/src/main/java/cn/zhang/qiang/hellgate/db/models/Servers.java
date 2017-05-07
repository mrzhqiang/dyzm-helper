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
 * 服务器/游戏区/游戏频道 表
 * <p>
 * Created by mrZQ on 2017/4/5.
 */

public class Servers {

    private long id = -1;
    public String alias;
    public String path;
    public long created;
    public long gameId;

    public String gameName;
    public String gameHost;
    public boolean isSupport;
    public String portalTitle;
    public String portalDomain;

    public Servers() {
        this.created = System.currentTimeMillis();
    }

    public String getCreated() {
        return TimeHelper.showTime(created);
    }

    public void setValuesFromCursor(Cursor cursor) {
        id = Db.getLong(cursor, _ID);
        alias = Db.getString(cursor, Table.COL_ALIAS);
        path = Db.getString(cursor, Table.COL_PATH);
        created = Db.getLong(cursor, Table.COL_CREATED);
        gameId = Db.getLong(cursor, Table.COL_GAME_ID);

        // 查询通常是多表联查
        gameName = Db.getString(cursor, Games.Table.COL_NAME);
        gameHost = Db.getString(cursor, Games.Table.COL_HOST);
        isSupport = Db.getBoolean(cursor, Games.Table.COL_IS_SUPPORT);
        portalTitle = Db.getString(cursor, Portal.Table.COL_TITLE);
        portalDomain = Db.getString(cursor, Portal.Table.COL_DOMAIN);
    }

    public ContentValues toContentValues() {
        ContentValues out = new ContentValues();

        if (id > 0) {
            out.put(_ID, id);
        }
        out.put(Table.COL_ALIAS, alias);
        out.put(Table.COL_PATH, path);
        out.put(Table.COL_CREATED, created);
        out.put(Table.COL_GAME_ID, gameId);

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
        public static final String NAME = "servers";

        public static final String COL_ALIAS = "alias";
        public static final String COL_PATH = "path";
        public static final String COL_CREATED = "created";
        public static final String COL_GAME_ID = "game_id";


        public static final String CREATE_TABLE = "CREATE Table " + NAME + "("
                + _ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + COL_PATH + " TEXT NOT NULL UNIQUE,"
                + COL_ALIAS + " TEXT NOT NULL UNIQUE,"
                + COL_CREATED + " INTEGER NOT NULL,"
                + COL_GAME_ID + " INTEGER NOT NULL REFERENCES "
                + Games.Table.NAME + "(" + _ID + ") ON DELETE CASCADE"
                + ");";// 父表是游戏列表，删除则应该级联删除游戏下的所有服务器/区/频道

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
