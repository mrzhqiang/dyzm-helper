package cn.zhang.qiang.hellgate.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.qiang.zhang.logger.Log;
import cn.zhang.qiang.hellgate.db.models.Account;
import cn.zhang.qiang.hellgate.db.models.ServiceList;

/**
 * <p>
 * Created by mrZQ on 2017/4/5.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = "DbHelper";

    private static final DbTable[] DB_TABLES = new DbTable[]{
            new ServiceList.TABLE(),
            new Account.TABLE(),
    };

    public static final int VERSION = 1;
    public static final String DB_NAME = "hell_gate.db";

    private static DbHelper instance;

    public static synchronized DbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbHelper(context.getApplicationContext());
        }
        return instance;
    }

    private DbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "Creating database");
        for (DbTable table : DB_TABLES) {
            db.execSQL(table.getCreateSql());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "Upgrading schema from " + oldVersion + " to " + newVersion);

        for (int i = oldVersion; i < newVersion; i++) {
            Log.i(TAG, "Upgrading from " + i + " to " + (i + 1));
            String[] upgrades = DB_TABLES[i].getUpgrade();
            for (String upgrade : upgrades) {
                db.execSQL(upgrade);
            }
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "Downgrading schema from " + oldVersion + " to " + newVersion);

        for (int i = oldVersion; i > newVersion; i++) {
            Log.i(TAG, "Downgrading from " + i + " to " + (i - 1));
            db.execSQL(DB_TABLES[i].getDropSql());
        }
    }

}
