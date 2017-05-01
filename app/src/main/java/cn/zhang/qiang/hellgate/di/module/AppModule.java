package cn.zhang.qiang.hellgate.di.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 提供数据库和网络模块依赖
 * <p>
 * Created by mrZQ on 2017/5/1.
 */

@Module(includes = {
        DbModule.class,
        NetModule.class,
})
public final class AppModule {
    private final Application application;

    public AppModule(Application application) {this.application = application;}

    @Provides @Singleton Application provideApplication() {
        return application;
    }
}
