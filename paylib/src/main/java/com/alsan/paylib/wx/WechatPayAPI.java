package com.alsan.paylib.wx;

import com.alsan.paylib.OnPayListener;

/**
 * 微信支付API
 * <p>
 * 使用:
 * <p>
 * WechatPayAPI.getInstance().sendPayReq(wechatPayReq);
 * <p>
 * Created by mayubao on 2017/3/5.
 * Contact me 454092825@qq.com
 */
public class WechatPayAPI {

    /**
     * 获取微信支付API
     */
    private static final Object mLock = new Object();
    private static WechatPayAPI mInstance;
    private String wxAppId;
    //微信支付监听
    private OnPayListener mOnWechatPayListener;

    public static WechatPayAPI getInstance() {
        if (mInstance == null) {
            synchronized (mLock) {
                if (mInstance == null) {
                    mInstance = new WechatPayAPI();
                }
            }
        }
        return mInstance;
    }

    public String getWxAppId(){
        return wxAppId;
    }

    public OnPayListener getOnWechatPayListener() {
        return mOnWechatPayListener;
    }

    /**
     * 发送微信支付请求
     *
     * @param wechatPayReq
     */
    public void sendPayReq(WechatPayReq wechatPayReq) {
        wxAppId = wechatPayReq.getAppId();
        mOnWechatPayListener = wechatPayReq.getOnWechatPayListener();
        wechatPayReq.send();
    }

}
