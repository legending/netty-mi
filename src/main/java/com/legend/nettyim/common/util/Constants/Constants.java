package com.legend.nettyim.common.util.Constants;



/**
 * Created by Administrator on 2017/3/10 0010.
 */
public class Constants {
    //  全局配置
    public final static int False = 0;
    public final static int True = 1;

    public final static String kCode_Success        = "0";           //  成功
    public final static String kCode_Fail           = "1";          //  失败
    public final static String kCode_SessionError   = "1000";       //  登陆超时

    public final static String blackMap="blackMap"; //黑名单
    public final static int  timeInterval=2;  //两次访问时间间隔最少为2秒,超过2秒则表示为恶意访问
    public final static int  illegalCount=3 ; //非法次数,超过这个次数,会拉入黑名单,非法有可能是访问过快,等等原因
    public final static String accessTimeMap="accessTimeMap";//上次访问时间记录

}
