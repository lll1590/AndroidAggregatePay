### Android聚合支付工具库
### 目前封装了微信支付和支付宝支付两种方式，一键调用，无需任何配置

使用方式：
1、在项目根目录的build.gradle文件添加
    buildscript {
        repositories {
            maven { url "https://jitpack.io" }
        }
    }

2、在对应Module的build.gradle文件添加依赖
    implementation 'com.github.lll1590:AndroidAggregatePay:v1.0.0'

3、支付宝支付：
    AliPayReq aliPayReq = new AliPayReq.Builder()
                    .with(this)//Activity上下文
                    .setSignedAliPayOrderInfo(alipayInfo)//支付宝支付信息，包含签名
                    .setOnPayListener(this)//支付监听
                    .create();
    PayAPI.getInstance().sendPayRequest(aliPayReq);

4、微信支付：
    WechatPayReq wechatPayReq = new WechatPayReq.Builder()
                    .with(this)//Activity上下文
                    .setAppId(Constant.WX_APP_ID)//微信AppId
                    .setPartnerId(Constant.WX_PARTNERID)//微信支付商户号
                    .setNonceStr(pay.getNoncestr())//微信支付随机字符串
                    .setPrepayId(pay.getPrepayid())//微信预支付Id
                    .setSign(pay.getSign())//微信支付签名
                    .setTimeStamp(pay.getTimestamp())//微信支付时间戳
                    .setOnWechatPayListener(this)//微信支付监听
                    .create();
    PayAPI.getInstance().sendPayRequest(wechatPayReq);