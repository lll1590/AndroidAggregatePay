package com.alsan.paylib.ali;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.alsan.paylib.Constant;
import com.alsan.paylib.OnPayListener;
import com.alsan.paylib.ali.sdk.PayResult;

import java.util.Map;


/**
 * 支付宝支付请求2
 * <p>
 * 安全的的支付宝支付流程，用法
 * <p>
 * Created by mayubao on 2017/3/5.
 * Contact me 454092825@qq.com
 */
public class AliPayReq {

    /**
     * ali pay sdk flag
     */
    private static final int SDK_PAY_FLAG = 1;

    private Activity mActivity;

//    //未签名的订单信息
//    private String rawAliPayOrderInfo;
    //服务器签名成功的订单信息
    private String signedAliPayOrderInfo;
    //支付宝支付监听
    private OnPayListener mOnPayListener;

    private Handler mHandler;

    @SuppressLint("HandlerLeak")
    public AliPayReq() {
        super();
        mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SDK_PAY_FLAG: {
                        PayResult payResult = new PayResult((Map<String, String>) msg.obj);

                        // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                        String resultInfo = payResult.getResult();

                        String resultStatus = payResult.getResultStatus();
                        Log.d(Constant.TAG, "resultStatus：" + resultStatus + ", resultInfo:" + resultInfo);
                        // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                        if (TextUtils.equals(resultStatus, "9000")) {
                            if (mOnPayListener != null) {
                                mOnPayListener.onPaySuccess(2, resultInfo);
                            }
                        } else {
                            // 判断resultStatus 为非“9000”则代表可能支付失败
                            // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                            if (TextUtils.equals(resultStatus, "8000")) {
                                if (mOnPayListener != null) {
                                    mOnPayListener.onPayConfirmimg(2, resultInfo);
                                }
                            } else {
                                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                                if (mOnPayListener != null) {
                                    mOnPayListener.onPayFailure(2, resultInfo);
                                }
                            }
                        }
                        break;
                    }
                }
            }

        };
    }

    /**
     * rsa2加密方式
     */
    public void send(){
        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {

                PayTask alipay = new PayTask(mActivity);
                Map<String, String> result = alipay.payV2(signedAliPayOrderInfo, true);
                Log.d(Constant.TAG, result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }



    public static class Builder {
        //上下文
        private Activity activity;

        //未签名的订单信息
//        private String rawAliPayOrderInfo;
        //服务器签名成功的订单信息
        private String signedAliPayOrderInfo;
        //支付宝支付监听
        private OnPayListener mOnPayListener;

        public Builder() {
            super();
        }

        public Builder with(Activity activity) {
            this.activity = activity;
            return this;
        }


        /**
         * 设置未签名的订单信息
         *
         * @param rawAliPayOrderInfo
         * @return
         */
//        public Builder setRawAliPayOrderInfo(String rawAliPayOrderInfo) {
//            this.rawAliPayOrderInfo = rawAliPayOrderInfo;
//            return this;
//        }

        /**
         * 设置服务器签名成功的订单信息
         *
         * @param signedAliPayOrderInfo
         * @return
         */
        public Builder setSignedAliPayOrderInfo(String signedAliPayOrderInfo) {
            this.signedAliPayOrderInfo = signedAliPayOrderInfo;
            return this;
        }

        public Builder setOnPayListener(OnPayListener onPayListener) {
            this.mOnPayListener = onPayListener;
            return this;
        }

        public AliPayReq create() {
            AliPayReq aliPayReq = new AliPayReq();
            aliPayReq.mActivity = this.activity;
//            aliPayReq.rawAliPayOrderInfo = this.rawAliPayOrderInfo;
            aliPayReq.signedAliPayOrderInfo = this.signedAliPayOrderInfo;
            aliPayReq.mOnPayListener = this.mOnPayListener;
            return aliPayReq;
        }

    }
}
