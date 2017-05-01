package cn.zhang.qiang.hellgate.di.module;

import android.app.Application;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import cn.zhang.qiang.hellgate.BuildConfig;
import dagger.Module;
import dagger.Provides;
import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

/**
 * 提供OkHttp依赖
 * <p>
 * Created by mrZQ on 2017/5/1.
 */

@Module
public final class OkHttpModule {
    private static final String CACHE_DIR = "ok_http_cache";
    private static final int CACHE_MAX_SIZE = 50 * 1024 * 1024; // 50Mb

    /** 401无认证的补救措施 */
    @Provides @Singleton Authenticator provideAuthenticator() {
        return new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) throws IOException {
                Log.wtf("OkHttpModule", "401认证失败，跳转到补救权限，但没有实现这个方法");
                // todo 目前没有认证的问题
//                String host = route.address().url().host();

                // 跳过其他服务器的请求
//                if (!ApiV1.API_URL.contains(host)) { return null; }
                // 跳过已经有的认证
//                String auth=response.request().header("Authorization");
//                if (!TextUtils.isEmpty(response.request().header("Authorization"))) { return null; }
                // 401，这里只做Client权限认证，因为Token无法保证绝对有效
//                String credential = Credentials.basic(Constant.APIKEY, "null");
//                return response.request().newBuilder().header("Authorization", credential).build();
                return null;
            }
        };
    }

    @Provides @Singleton Cache provideCache(Application application) {
        return new Cache(new File(application.getCacheDir(), CACHE_DIR), CACHE_MAX_SIZE);
    }

    @Provides @Singleton HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor(/* custom logger */);
        logger.setLevel(BuildConfig.DEBUG ? BODY : NONE);
        return logger;
    }

    @Provides @Singleton OkHttpClient provideOkHttpClient(Authenticator authenticator, Cache cache,
                                                          HttpLoggingInterceptor interceptor) {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .authenticator(authenticator)
                .cache(cache)
                .addInterceptor(interceptor)
                .build();
    }

    @Provides OkHttpClient.Builder provideCustomOkHttpClient(OkHttpClient client) {
        return client.newBuilder();
    }

}
