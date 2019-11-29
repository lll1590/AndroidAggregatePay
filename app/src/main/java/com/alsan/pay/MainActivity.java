package com.alsan.pay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.alsan.paylib.Constant;
import com.alsan.paylib.OnPayListener;
import com.alsan.paylib.PayAPI;
import com.alsan.paylib.ali.AliPayReq;
import com.alsan.paylib.wx.WechatPayReq;

public class MainActivity extends AppCompatActivity {


    /**
     * 微信AppId
     */
    public static final String WX_APP_ID = "xxxxx";
    /**
     * 微信商户号
     */
    public static final String WX_PARTNERID = "xxxxx";

    /**
     * 支付宝AppId
     */
    public static final String ALI_APP_ID = "xxxx";
    /**
     * 支付宝私钥
     */
    public static final String ALI_RSA2_PRIVATE = "xxxxxx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.ali_pay).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AliPayReq aliPayReq = new AliPayReq.Builder()
                        .with(MainActivity.this)
                        .setSignedAliPayOrderInfo("xxxx")
                        .setOnPayListener(new OnPayListener() {
                            @Override
                            public void onPaySuccess(int platform, String resultInfo) {
                                Log.d(Constant.TAG, "MainActivity onPaySuccess platform : " + platform + ", resultInfo:" + resultInfo);
                            }

                            @Override
                            public void onPayFailure(int platform, String resultInfo) {
                                Log.d(Constant.TAG, "MainActivity onPayFailure platform : " + platform + ", resultInfo:" + resultInfo);
                            }

                            @Override
                            public void onPayConfirmimg(int platform, String resultInfo) {
                                Log.d(Constant.TAG, "MainActivity onPayConfirmimg platform : " + platform + ", resultInfo:" + resultInfo);
                            }
                }).create();
                PayAPI.getInstance().sendPayRequest(aliPayReq);
            }
        });

        findViewById(R.id.ali_pay_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayAPI.getInstance().testAliPay(MainActivity.this, ALI_APP_ID, ALI_RSA2_PRIVATE);
            }
        });

        findViewById(R.id.wx_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WechatPayReq wechatPayReq = new WechatPayReq.Builder()
                        .with(MainActivity.this) //activity实例
                        .setAppId(WX_APP_ID) //微信支付AppID
                        .setPartnerId(WX_PARTNERID)//微信支付商户号
                        .setPrepayId("xxxx")//预支付码
//								.setPackageValue(wechatPayReq.get)//"Sign=WXPay"
                        .setNonceStr("xxxx")
                        .setTimeStamp("xx")//时间戳
                        .setSign("xxxxx")//签名
                        .setOnWechatPayListener(new OnPayListener() {
                            @Override
                            public void onPaySuccess(int platform, String resultInfo) {
                                Log.d(Constant.TAG, "testWechatPay onPaySuccess platform : " + platform + ", resultInfo:" + resultInfo);
                            }

                            @Override
                            public void onPayFailure(int platform, String resultInfo) {
                                Log.d(Constant.TAG, "testWechatPay onPayFailure platform : " + platform + ", resultInfo:" + resultInfo);
                            }

                            @Override
                            public void onPayConfirmimg(int platform, String resultInfo) {
                                Log.d(Constant.TAG, "testWechatPay onPayConfirmimg platform : " + platform + ", resultInfo:" + resultInfo);
                            }
                        })
                        .create();

                PayAPI.getInstance().sendPayRequest(wechatPayReq);
            }
        });
    }
}
