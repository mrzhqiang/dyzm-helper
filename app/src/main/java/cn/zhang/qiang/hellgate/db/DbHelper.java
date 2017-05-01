package cn.zhang.qiang.hellgate.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.List;

import cn.qiang.zhang.logger.Log;
import cn.zhang.qiang.hellgate.db.models.Account;
import cn.zhang.qiang.hellgate.db.models.Games;
import cn.zhang.qiang.hellgate.db.models.Portals;
import cn.zhang.qiang.hellgate.db.models.Servers;

/**
 * 数据库辅助
 * <p>
 * Created by mrZQ on 2017/4/5.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = "DbHelper";
    private static final String DB_NAME = "hellgate.db";
    private static final int VERSION = 1;

    /** 这里包括所有表的创建语句，还有数据库版本的升、降级语句 */
    private static final DbTable[] DB_TABLES = new DbTable[]{
            new Portals.Table(),
            new Games.Table(),
            new Servers.Table(),
            new Account.Table(),
    };

    public DbHelper(Context context) {
        super(context, DB_NAME, null /* factory */, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if(!db.isReadOnly()) { // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }

        Log.i(TAG, "Creating database");
        for (DbTable table : DB_TABLES) {
            for (String createSql : table.getCreateSql()) {
                if (!TextUtils.isEmpty(createSql)) { db.execSQL(createSql); }
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "Upgrading schema from " + oldVersion + " to " + newVersion);

        for (int i = oldVersion; i < newVersion; i++) {
            Log.i(TAG, "Upgrading from " + i + " to " + (i + 1));
            for (DbTable table : DB_TABLES) {
                List<String> upgradeList = table.getUpgrade().get(i);
                if (upgradeList != null) {
                    for (String upSql : upgradeList) {
                        if (!TextUtils.isEmpty(upSql)) { db.execSQL(upSql); }
                    }
                }
            }
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "Downgrading schema from " + oldVersion + " to " + newVersion);

        for (int i = oldVersion; i > newVersion; i--) {
            Log.i(TAG, "Downgrading from " + i + " to " + (i - 1));
            for (DbTable table : DB_TABLES) {
                List<String> downgradeList = table.getDowngrade().get(i);
                if (downgradeList != null) {
                    for (String downSql : downgradeList) {
                        if (!TextUtils.isEmpty(downSql)) { db.execSQL(downSql); }
                    }
                }
            }
        }
    }

}
