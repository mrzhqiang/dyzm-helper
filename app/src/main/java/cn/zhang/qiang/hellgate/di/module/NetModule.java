package cn.zhang.qiang.hellgate.di.module;

import android.app.Application;
import android.graphics.Bitmap;
import android.net.Uri;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import javax.inject.Singleton;

import cn.qiang.zhang.logger.Log;
import cn.zhang.qiang.hellgate.BuildConfig;
import cn.zhang.qiang.hellgate.net.NetHelper;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.schedulers.Schedulers;

/**
 * 提供网络相关依赖
 * <p>
 * Created by mrZQ on 2017/5/1.
 */

@Module(includes = {
        OkHttpModule.class,
})
public final class NetModule {
    @Provides @Singleton OkHttp3Downloader provideOkHttpDownloader(OkHttpClient client) {
        return new OkHttp3Downloader(client);
    }

    @Provides @Singleton
    Picasso providePicasso(Application application, OkHttp3Downloader downloader) {
        return new Picasso.Builder(application)
                .downloader(downloader)
                .defaultBitmapConfig(Bitmap.Config.ARGB_8888)
                .indicatorsEnabled(BuildConfig.DEBUG)
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        Log.d("Picasso", String.format(Locale.getDefault(),
                                                       "Failed to load image: %s", uri), exception);
                    }
                })
                .build();
    }

    @Provides @Singleton Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(NetHelper.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create()) // String
                .addConverterFactory(GsonConverterFactory.create()) // Json
//                .addCallAdapterFactory(ErrorHandlingCallAdapterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(client)
                .build();
    }

}
