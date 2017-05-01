/*
 * Copyright (c) 2017.  mrZQ
 *
 * icensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package cn.zhang.qiang.hellgate;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import cn.qiang.zhang.logger.Log;
import cn.zhang.qiang.hellgate.di.component.AppComponent;
import cn.zhang.qiang.hellgate.di.component.DaggerAppComponent;
import cn.zhang.qiang.hellgate.di.module.AppModule;
import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * App级别，因此可以在应用的任何地方（需要增加inject方法）注入相关依赖，通常是网络和数据库部分
 * <p>
 * Created by mrZQ on 2017/3/5.
 */

public final class HellGateApp extends Application {
    private static AppComponent component;

    @Inject Picasso picasso;
    @Inject OkHttpClient client;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.debug(BuildConfig.DEBUG);

        component = DaggerAppComponent.builder().appModule(new AppModule(this)).build();

        component.inject(this);
        Picasso.setSingletonInstance(picasso);

    }

    /** 取消对应Tag的请求，这是一个示例，后续可能要用到 */
    public boolean cancelTag(Object tag) {
        boolean isSuccessful = false;
        for (Call call : client.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
                isSuccessful = true;
            }
        }
        for (Call call : client.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
                isSuccessful = true;
            }
        }
        return isSuccessful;
    }

    /** 网络是否存在、是否已连接 */
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isAvailable() && ni.isConnected();
    }

    /** 提供全局依赖注入组件，通常的，它应该只出现在Model层 */
    public static AppComponent getComponent() {
        return component;
    }
}
