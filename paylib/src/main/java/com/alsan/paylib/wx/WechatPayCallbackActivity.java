package com.alsan.paylib.wx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alsan.paylib.Constant;
import com.alsan.paylib.OnPayListener;
import com.alsan.paylib.R;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WechatPayCallbackActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = Constant.TAG;

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechat_pay_callback);

        api = WXAPIFactory.createWXAPI(this, WechatPayAPI.getInstance().getWxAppId());
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        Log.d(TAG,"onReq===>>>get baseReq.getType : " + req.getType());
    }

    @Override
    public void onResp(BaseResp resp) {
        //0-成功；1-错误；2-用户取消
        Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            OnPayListener listener = WechatPayAPI.getInstance().getOnWechatPayListener();
            if(listener != null){
                if(resp.errCode == BaseResp.ErrCode.ERR_OK){ //        0 成功	展示成功页面
                    listener.onPaySuccess(1, "支付成功");
                }else{//  -1	错误       -2	用户取消
                    listener.onPayFailure(1, "支付失败");
                }
            }
        }
        setResult(RESULT_OK);
        finish();
    }
}