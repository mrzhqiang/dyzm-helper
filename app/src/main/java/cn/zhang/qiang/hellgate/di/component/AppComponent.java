package cn.zhang.qiang.hellgate.di.component;

import javax.inject.Singleton;

import cn.zhang.qiang.hellgate.HellGateApp;
import cn.zhang.qiang.hellgate.di.module.AppModule;
import cn.zhang.qiang.hellgate.model.WeChatModel;
import dagger.Component;

/**
 * App级别，因此可以在应用的任何地方（需要增加inject方法）注入相关依赖，通常是网络和数据库部分
 * <p>
 * Created by mrZQ on 2017/5/1.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(HellGateApp app);

    void inject(WeChatModel weChatModel);

    // 其他注入方法...这里基本上只注入到Model层
}
