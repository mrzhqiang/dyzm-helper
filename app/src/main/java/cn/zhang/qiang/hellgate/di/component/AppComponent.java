package cn.zhang.qiang.hellgate.di.component;

import javax.inject.Singleton;

import cn.zhang.qiang.hellgate.HellGateApp;
import cn.zhang.qiang.hellgate.di.module.AppModule;
import dagger.Component;

/**
 * <p>
 * Created by mrZQ on 2017/5/1.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(HellGateApp app);

    // 其他注入方法...这里基本上只注入到Model层
}
