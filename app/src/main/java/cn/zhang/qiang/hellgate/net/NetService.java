package cn.zhang.qiang.hellgate.net;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * <p>
 * Created by mrZQ on 2017/4/5.
 */

public interface NetService {

    @GET("/")
    Call<String> host();

    @GET("cardh.php")
    Call<String> login(@Query("name") String username, @Query("pwd") String password,
                       @Query("url") String url, @Query("exuid") String exuid,
                       @Query("action") String action, @Query("_do") String _do,
                       @Query("time") String time);

}
