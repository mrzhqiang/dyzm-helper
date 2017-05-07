package cn.zhang.qiang.hellgate.db.models;

import android.util.LongSparseArray;

import java.util.List;

import cn.zhang.qiang.hellgate.db.DbTable;
import cn.zhang.qiang.hellgate.utils.TimeHelper;

import static android.provider.BaseColumns._ID;

/**
 * 门户网站 表
 * <p>
 * Created by mrZQ on 2017/4/8.
 */

public final class Portal {

    public long id = -1;
    public String title;
    public String domain;
    public long created;

    public Portal() {
        this.created = System.currentTimeMillis();
    }

    public String getCreated() {
        return TimeHelper.showTime(created);
    }

    public static class Table implements DbTable {
        public static final String NAME = "portal";

        public static final String COL_TITLE = "title";
        public static final String COL_DOMAIN = "domain";
        public static final String COL_CREATED = "created";

        static final String CREATE_TABLE = "CREATE Table " + NAME + "("
                + _ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + COL_TITLE + " TEXT NOT NULL UNIQUE,"
                + COL_DOMAIN + " TEXT NOT NULL UNIQUE,"
                + COL_CREATED + " INTEGER NOT NULL DEFAULT 0"
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
//            ups.put(2, Arrays.asList("一些升级语句"));
            return ups;
        }

        @Override
        public LongSparseArray<List<String>> getDowngrade() {
            LongSparseArray<List<String>> dos = new LongSparseArray<>();
//            ups.put(1, Arrays.asList("一些降级语句，对应升级语句建立"));
            return dos;
        }
    }
}
