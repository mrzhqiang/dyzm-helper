package cn.zhang.qiang.hellgate.net;

import cn.zhang.qiang.hellgate.model.WeChatModel;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 微信平台接口
 * <p>
 * Created by mrZQ on 2017/5/7.
 */
public interface WeChat {
    // https://api.weixin.qq.com/sns/oauth2/access_token?appid= &secret= &code= &grant_type=
    @GET Observable<WeChatModel.Token> token(@Url String url,
                                             @Query("appid") String appid,
                                             @Query("secret") String secret,
                                             @Query("code") String code,
                                             @Query("grant_type") String grant_type);

    // https://api.weixin.qq.com/sns/userinfo?access_token= &openid=
    @GET Observable<WeChatModel.UserInfo> userInfo(@Url String url,
                                                   @Query("access_token") String access_token,
                                                   @Query("openid") String openid);
}
