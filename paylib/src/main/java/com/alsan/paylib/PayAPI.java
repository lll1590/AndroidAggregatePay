package com.alsan.paylib;

import android.app.Activity;

import com.alsan.paylib.ali.AliPayAPI;
import com.alsan.paylib.ali.AliPayReq;
import com.alsan.paylib.wx.WechatPayAPI;
import com.alsan.paylib.wx.WechatPayReq;

/**
 * 支付的API
 *
 * Created by mayubao on 2017/2/22.
 * Contact me 454092825@qq.com
 */
public class PayAPI {


    private static final Object mLock = new Object();
    private static PayAPI mInstance;

    public static PayAPI getInstance(){
        if(mInstance == null){
            synchronized (mLock){
                if(mInstance == null){
                    mInstance = new PayAPI();
                }
            }
        }
        return mInstance;
    }


    /**
     * 支付宝支付请求
     * @param aliPayRe
     */
    public void sendPayRequest(AliPayReq aliPayRe){
        AliPayAPI.getInstance().sendPayReq(aliPayRe);
    }


    /**
     * 微信支付请求
     * @param wechatPayReq
     */
    public void sendPayRequest(WechatPayReq wechatPayReq){
        WechatPayAPI.getInstance().sendPayReq(wechatPayReq);
    }

    /**
     * 测试支付宝支付
     * @param activity
     */
    public void testAliPay(Activity activity, String appId, String rsa2){
        AliPayAPI.getInstance().testAliPay(activity, appId, rsa2);
    }
}


