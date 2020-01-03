package com.ytsk.pushlib;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hms.aaid.HmsInstanceId;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.ytsk.pushlib.mi.MiMessageReceiver;

import java.util.List;

public class PushManager {

    private static String MI_PUSH_APP_ID;
    private static String MI_PUSH_APP_KEY;
    private static String MEIZU_PUSH_APP_ID;
    private static String MEIZU_PUSH_APP_KEY;

    public static void initPush(Context application) {
        if (application == null)
            throw new NullPointerException("application is null");
        MI_PUSH_APP_ID = PushUtil.readValMetaDataFromApplication(application, "MI_PUSH_APP_ID");
        MI_PUSH_APP_KEY = PushUtil.readValMetaDataFromApplication(application, "MI_PUSH_APP_KEY");
        MEIZU_PUSH_APP_ID = PushUtil.readValMetaDataFromApplication(application, "MEIZU_PUSH_APP_ID");
        MEIZU_PUSH_APP_KEY = PushUtil.readValMetaDataFromApplication(application, "MEIZU_PUSH_APP_KEY");
        if (MI_PUSH_APP_ID == null)
            throw new NullPointerException("mi push app id is null");
        if (MI_PUSH_APP_KEY == null)
            throw new NullPointerException("mi push app key is null");
        if (MEIZU_PUSH_APP_ID == null)
            throw new NullPointerException("meizu push app id is null");
        if (MEIZU_PUSH_APP_KEY == null)
            throw new NullPointerException("meizu push app key is null");
        if (PushUtil.sysType() == PushUtil.SYS_HUAWEI) {
//            HMSAgent.init(application);
            getToken(application);
        } else if (PushUtil.sysType() == PushUtil.SYS_XIAOMI) {
            // 注册push服务，注册成功后会向DemoMessageReceiver发送广播
            // 可以从DemoMessageReceiver的onCommandResult方法中MiPushCommandMessage对象参数中获取注册信息
            if (shouldInit(application)) {
                MiPushClient.registerPush(application, MI_PUSH_APP_ID, MI_PUSH_APP_KEY);
            }

            LoggerInterface newLogger = new LoggerInterface() {

                @Override
                public void setTag(String tag) {
                    // ignore
                }

                @Override
                public void log(String content, Throwable t) {
                    Log.d(MiMessageReceiver.TAG, content, t);
                }

                @Override
                public void log(String content) {
                    Log.d(MiMessageReceiver.TAG, content);
                }
            };
            Logger.setLogger(application, newLogger);

        } else if (PushUtil.sysType() == PushUtil.SYS_MEIZU) {
            if (MzSystemUtils.isMeizu(application)) {
                com.meizu.cloud.pushsdk.PushManager.register(application, MEIZU_PUSH_APP_ID, MEIZU_PUSH_APP_KEY);
            }
        } else {
            //信鸽push
            XGPushConfig.enableDebug(application, BuildConfig.DEBUG);
            XGPushManager.registerPush(application);
        }
    }

    private static String TAG = "huawei";

    /**
     * 获取token
     */
    private static void getToken(final Context context) {
        Log.i(TAG, "get token: begin");

        // get token
        new Thread() {
            @Override
            public void run() {
                try {
                    String appId = AGConnectServicesConfig.fromContext(context)
                            .getString("client/app_id");
                    String pushtoken = HmsInstanceId.getInstance(context)
                            .getToken(appId, "HCM");
                    if (!TextUtils.isEmpty(pushtoken)) {
                        Log.i(TAG, "get token:" + pushtoken);
                        PushUtil.sendBroadcast(context,pushtoken);
                    }
                } catch (Exception e) {
                    Log.i(TAG, "getToken failed, " + e);

                }
            }
        }.start();
    }


    public static void reInitMiPush(Context ctx) {
        if (ctx == null) return;
        MiPushClient.registerPush(ctx.getApplicationContext(), MI_PUSH_APP_ID, MI_PUSH_APP_KEY);
    }

    private static boolean shouldInit(Context context) {
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = context.getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

//    public static void onTerminate() {
//        if (PushUtil.sysType() == PushUtil.SYS_HUAWEI) {
//            HMSAgent.destroy();
//        }
//    }

}
