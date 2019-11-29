package com.alsan.paylib;

/**
 * 支付结果回调
 */
public interface OnPayListener {
    /**
     * 成功回调
     * @param platform 平台1-微信；2-支付宝
     * @param resultInfo
     */
    void onPaySuccess(int platform, String resultInfo);
    /**
     * 失败回调
     * @param platform 平台1-微信；2-支付宝
     * @param resultInfo
     */
    void onPayFailure(int platform, String resultInfo);
    /**
     * 处理中回调
     * @param platform 平台1-微信；2-支付宝
     * @param resultInfo
     */
    void onPayConfirmimg(int platform, String resultInfo);
}
