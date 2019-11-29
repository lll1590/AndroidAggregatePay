package com.alsan.paylib.ali;

import android.app.Activity;
import android.util.Log;

import com.alsan.paylib.Constant;
import com.alsan.paylib.OnPayListener;
import com.alsan.paylib.ali.sdk.OrderInfoUtil2_0;

import java.util.Map;

/**
 * 支付宝支付API
 * 
 * 使用:
 * 
 * AliPayAPI.getInstance().apply(config).sendPayReq(aliPayReq);
 *
 * Created by mayubao on 2017/3/5.
 * Contact me 454092825@qq.com
 */
public class AliPayAPI {
	
	/**
	 * 获取支付宝支付API
	 */
    private static final Object mLock = new Object();
    private static AliPayAPI mInstance;

    public static AliPayAPI getInstance(){
        if(mInstance == null){
            synchronized (mLock){
                if(mInstance == null){
                    mInstance = new AliPayAPI();
                }
            }
        }
        return mInstance;
    }
	

	/**
	 * 发送支付宝支付请求
	 * @param aliPayReq
	 */
    public void sendPayReq(AliPayReq aliPayReq){
    	aliPayReq.send();
    }

	/**
	 * 支付宝支付本地测试
	 * @param activity
	 * @param appId
	 * @param rsa2Private
	 */
    public void testAliPay(Activity activity, String appId, String rsa2Private){
		Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(appId, true);
		String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
		String sign = OrderInfoUtil2_0.getSign(params, rsa2Private, true);
		final String orderInfo = orderParam + "&" + sign;

    	AliPayReq aliPayReq = new AliPayReq.Builder().with(activity).setSignedAliPayOrderInfo(orderInfo).setOnPayListener(new OnPayListener() {
			@Override
			public void onPaySuccess(int platform, String resultInfo) {
				Log.d(Constant.TAG, "testAliPay onPaySuccess platform : " + platform + ", resultInfo:" + resultInfo);
			}

			@Override
			public void onPayFailure(int platform, String resultInfo) {
				Log.d(Constant.TAG, "testAliPay onPayFailure platform : " + platform + ", resultInfo:" + resultInfo);
			}

			@Override
			public void onPayConfirmimg(int platform, String resultInfo) {
				Log.d(Constant.TAG, "testAliPay onPayConfirmimg platform : " + platform + ", resultInfo:" + resultInfo);
			}
		}).create();
    	sendPayReq(aliPayReq);
	}
}
