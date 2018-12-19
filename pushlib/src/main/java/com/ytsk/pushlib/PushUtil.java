package com.ytsk.pushlib;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class PushUtil {


    public static final int SYS_HUAWEI = 1;
    public static final int SYS_XIAOMI = 2;
    public static final int SYS_MEIZU = 3;
    public static final int SYS_OTHER = 0;

    @IntDef({SYS_HUAWEI, SYS_XIAOMI, SYS_MEIZU, SYS_OTHER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SysVer {
    }

    //0:other 1:huawei 2:xiaoMi 3:Meizu  5ios
    @SysVer
    public static int sysType() {
        if (Build.MANUFACTURER.contains("HUAWEI"))
            return SYS_HUAWEI;
        else if (Build.MANUFACTURER.contains("Xiaomi"))
            return SYS_XIAOMI;
        else if (Build.MANUFACTURER.contains("meizu"))
            return SYS_MEIZU;
        else return SYS_OTHER;
    }


    /**
     * 读取application 节点  meta-data 信息 string
     */
    private static String readStringMetaDataFromApplication(Context context, String key) {
        if (context == null || key == null)
            return null;
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读取application 节点  meta-data 信息 前缀val=
     */
    public static String readValMetaDataFromApplication(Context context, String key) {
        if (context == null || key == null)
            return null;
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            String value= appInfo.metaData.getString(key);
            if (value!=null&&value.contains("val=")){
                return value.substring(4);
            }
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    // intent info
    ///////////////////////////////////////////////////////////////////////////
    public static final String INTENT_LIB_ACTION_MSG = "com.ytsk.pushlib.message";
    public static final String INTENT_LIB_ACTION_TOKEN = "com.ytsk.pushlib.token";

    public static final String INTENT_LIB_DATA = "data";


    public static void sendBroadcast(@NonNull Context context, @NonNull TanMsg msg) {
        Intent intent = new Intent(INTENT_LIB_ACTION_MSG);
        intent.putExtra("data", msg);
//        intent.addFlags(0x01000000);
        String packageName = readStringMetaDataFromApplication(context, "PACKAGE_NAME");
        if (packageName != null)
            intent.setPackage(packageName);
        context.sendBroadcast(intent);
    }

    public static void sendBroadcast(@NonNull Context context, @NonNull String token) {
        Intent intent = new Intent(INTENT_LIB_ACTION_TOKEN);
        intent.putExtra("data", token);
//        intent.addFlags(0x01000000);
        String packageName = readStringMetaDataFromApplication(context, "PACKAGE_NAME");
        if (packageName != null)
            intent.setPackage(packageName);
        context.sendBroadcast(intent);
    }


}
