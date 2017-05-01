package cn.zhang.qiang.hellgate.di.module;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import cn.qiang.zhang.logger.Log;
import cn.zhang.qiang.hellgate.db.DbHelper;
import dagger.Module;
import dagger.Provides;
import rx.schedulers.Schedulers;

/**
 * 提供数据库相关依赖
 * <p>
 * Created by mrZQ on 2017/5/1.
 */

@Module
public final class DbModule {
    @Provides @Singleton SQLiteOpenHelper provideOpenHelper(Application application) {
        return new DbHelper(application);
    }

    @Provides @Singleton SqlBrite provideSqlBrite() {
        return new SqlBrite.Builder()
                .logger(new SqlBrite.Logger() {
                    @Override
                    public void log(String message) {
                        Log.d("DB", message);
                    }
                }).build();
    }

    @Provides @Singleton BriteDatabase provideDatabase(SqlBrite sqlBrite, SQLiteOpenHelper helper) {
        BriteDatabase db = sqlBrite.wrapDatabaseHelper(helper, Schedulers.io());
        db.setLoggingEnabled(true);
        return db;
    }

    // todo 也可以提供ContentProvider，但不公开的数据，目前没必要
}
