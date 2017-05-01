package cn.zhang.qiang.hellgate.di.component;

import cn.zhang.qiang.hellgate.di.PerActivity;
import cn.zhang.qiang.hellgate.di.module.UIModule;
import dagger.Component;

/**
 * ViewModel连接器，注入到Activity或Fragment，注意：对应VM需要标记为@PerActivity
 * <p>
 * Created by mrZQ on 2017/5/1.
 */

@PerActivity
@Component(modules= UIModule.class)
public interface UIComponent {

//    void inject(LaunchActivity activity);
}
