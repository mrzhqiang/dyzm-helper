package cn.zhang.qiang.hellgate.di.module;

import android.content.Context;

import cn.zhang.qiang.hellgate.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 提供ViewModel依赖
 * <p>
 * Created by mrZQ on 2017/5/1.
 */

@Module(includes = {
        SystemModule.class,
})
public class UIModule {
    private final Context context;

    public UIModule(Context context) {this.context = context;}

    @Provides @PerActivity Context context() {
        return context;
    }

//    @Provides @PerActivity LaunchViewModel provideLaunchVM(Context context) {
//        return new LaunchViewModel(context);
//    }

//    @Provides @PerActivity
//    AccountViewModel provideAccountVM() {
//        return new AccountViewModel();
//    }

}
