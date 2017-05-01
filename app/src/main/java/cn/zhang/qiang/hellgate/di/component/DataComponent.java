package cn.zhang.qiang.hellgate.di.component;

import cn.zhang.qiang.hellgate.di.PerData;
import cn.zhang.qiang.hellgate.di.module.DataModule;
import dagger.Component;

/**
 * 数据连接器，注入到ViewModel层
 * <p>
 * Created by mrZQ on 2017/5/1.
 */

@PerData
@Component(modules = DataModule.class)
public interface DataComponent {
//    void inject(LaunchViewModel viewModel);
}
