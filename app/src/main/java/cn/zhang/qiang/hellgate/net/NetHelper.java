package cn.zhang.qiang.hellgate.net;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * <p>
 * Created by mrZQ on 2017/4/5.
 */

public final class NetHelper {
    public static final String BASE_URL = "http://haowanba.com/";

    private NetService service;

    private static class Holder {
        private static NetHelper N = new NetHelper();
    }

    private NetHelper() {
        // Create a very simple Retrofit adapter which points the SudaMockService API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        service = retrofit.create(NetService.class);
    }

    public static void host(Callback<String> callback) {
        Holder.N.service.host().enqueue(callback);
    }

    public static void login(String username, String password, Callback<String> callback) {
//        http://haowanba.com/cardh.php?name=cnm12355&pwd=123456&url=0&exuid=&action=passport&_do=&time=20170405111836
        Holder.N.service.login(username, password, "0", "", "passport", "", "20170408233200").enqueue(callback);
    }
}
