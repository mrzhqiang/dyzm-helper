package cn.zhang.qiang.hellgate.model;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import javax.inject.Inject;

import cn.qiang.zhang.logger.Log;
import cn.zhang.qiang.hellgate.HellGateApp;
import cn.zhang.qiang.hellgate.net.WeChat;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * 微信Model
 * <p>
 * Created by mrZQ on 2017/4/13.
 */
public final class WeChatModel {
    private static final String TAG = "WeChatModel";
    /* 微信开放平台接口常量 */
    private static final String API_URL = "https://api.weixin.qq.com";
    private static final String AUTH_URL = API_URL + "/sns/oauth2/access_token";
    private static final String USERINFO_URL = API_URL + "/sns/userinfo";
    private static final String APP_ID = "wx043d418da736d678";
    private static final String SECRET = "fa16ddc5ab055da9aa5db819cc9990d6";
    private static final String Q_AUTH = "authorization_code";

    private static IWXAPI wxApi;

    @Inject WeChat api;

    public WeChatModel() {
        HellGateApp.getComponent().inject(this);
    }

    /** 创建微信接口并将APP_ID注册到微信 */
    public void initWXApi(Context context) {
        if (wxApi == null) {
            wxApi = WXAPIFactory.createWXAPI(context, APP_ID);
            if (wxApi.registerApp(APP_ID)) {
                Log.i(TAG, "App register WeChat successful");
            }
        }
    }

    /** 发送登录请求到微信app */
    public boolean sendWeChatLogin() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "session" + (Math.random() * 1000);
        return wxApi.sendReq(req);
    }

    /** 处理微信登录后的反馈 */
    public void handleIntent(Intent intent, IWXAPIEventHandler handler) {
        checkNotNull(wxApi, "Please invoke initWXApi method.");
        wxApi.handleIntent(intent, handler);
    }

    /** 获取微信Token */
    public Subscription getToken(String code, final OnTokenListener listener) {
        return api.token(AUTH_URL, APP_ID, SECRET, code, Q_AUTH)
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<Token, Boolean>() {
                    @Override public Boolean call(Token token) {
                        // 过滤，如果返回openId为空，则回调失败
                        if (TextUtils.isEmpty(token.openid)) {
                            listener.onFailed(TextUtils.isEmpty(token.errcode) ? -1
                                                      : Integer.valueOf(token.errcode));
                            return false;
                        }
                        return true;
                    }
                })
                .subscribe(new Subscriber<Token>() {
                    @Override public void onCompleted() {}
                    @Override public void onError(Throwable e) {
                        // 可以根据HttpException的code进行返回，或直接返回message
                        listener.onFailed(-1);
                    }
                    @Override public void onNext(Token token) {
                        listener.onSuccessful(token);
                    }
                });
    }

    public Subscription getUserInfo(String token, String openid, final OnUserInfoListener listener) {
        return api.userInfo(USERINFO_URL, token, openid)
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<UserInfo, Boolean>() {
                    @Override public Boolean call(UserInfo userInfo) {
                        if (TextUtils.isEmpty(userInfo.openid)) {
                            listener.onFailed(TextUtils.isEmpty(userInfo.errcode) ? -1
                                                      : Integer.valueOf(userInfo.errcode));
                            return false;
                        }
                        return true;
                    }
                })
                .subscribe(new Subscriber<UserInfo>() {
                    @Override public void onCompleted() {}
                    @Override public void onError(Throwable e) {
                        listener.onFailed(-1);
                    }
                    @Override public void onNext(UserInfo userInfo) {
                        listener.onSuccessful(userInfo);
                    }
                });
    }

    public interface OnTokenListener {
        void onFailed(int code);
        void onSuccessful(Token token);
    }

    public interface OnUserInfoListener {
        void onFailed(int code);
        void onSuccessful(UserInfo userInfo);
    }

    public static class Message {
        public String errcode;
        public String errmsg;

        public String openid;
        public String unionid;
    }

    public static final class Token extends Message {
        public String access_token;
        public String expires_in;
        public String refresh_token;
        public String scope;
    }

    public static final class UserInfo extends Message {
//    public static final String MALE = "男";    1
//    public static final String FEMALE = "女";  2

        public String nickname;
        public int sex;
        public String province;
        public String city;
        public String country;
        public String headimgurl;
        public String[] privilege;
        public String language;
    }
}
