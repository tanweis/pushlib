package com.ytsk.pushlib.xg;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;
import com.ytsk.pushlib.PushUtil;
import com.ytsk.pushlib.TanMsg;

public class MessageReceiver extends XGPushBaseReceiver {
    private Intent intent = new Intent("com.qq.xgdemo.activity.UPDATE_LISTVIEW");
    public static final String LogTag = "TPushReceiver";


    private void show(Context context, String text) {
//        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }


    // 通知展示
    @Override
    public void onNotifactionShowedResult(Context context,
                                          XGPushShowedResult notifiShowedRlt) {

        if (context == null || notifiShowedRlt == null) {
            return;
        }

        String custom = notifiShowedRlt.getCustomContent();
        Log.d(LogTag, custom);
        TanMsg msg=TanMsg.newMsg()
                .type(TanMsg.PUSH_TYPE_NOTIFICATION)
                .title(notifiShowedRlt.getTitle())
                .content(notifiShowedRlt.getContent())
                .custom(notifiShowedRlt.getCustomContent());
        PushUtil.sendBroadcast(context,msg);
    }


    // 通知点击回调 actionType=1为该消息被清除，actionType=0为该消息被点击。此处不能做点击消息跳转，详细方法请参照官网的Android常见问题文档
    @Override
    public void onNotifactionClickedResult(Context context,
                                           XGPushClickedResult message) {
        Log.e("LC", "+++++++++++++++ 通知被点击 跳转到指定页面。");
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        if (context == null || message == null) {
            return;
        }
        String text = "";
        if (message.getActionType() == XGPushClickedResult.NOTIFACTION_CLICKED_TYPE) {
            // 通知在通知栏被点击啦。。。。。
            // APP自己处理点击的相关动作
            // 这个动作可以在activity的onResume也能监听，请看第3点相关内容
            text = "通知被打开 :" + message;
            String custom = message.getCustomContent();


        } else if (message.getActionType() == XGPushClickedResult.NOTIFACTION_DELETED_TYPE) {
            // 通知被清除啦。。。。
            // APP自己处理通知被清除后的相关动作
            text = "通知被清除 :" + message;

        }
    }

    //反注册的回调
    @Override
    public void onUnregisterResult(Context context, int errorCode) {
        if (context == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "反注册成功";
        } else {
            text = "反注册失败" + errorCode;
        }
        Log.d(LogTag, text);
        show(context, text);

    }

    //设置tag的回调
    @Override
    public void onSetTagResult(Context context, int errorCode, String tagName) {
        if (context == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "\"" + tagName + "\"设置成功";
        } else {
            text = "\"" + tagName + "\"设置失败,错误码：" + errorCode;
        }
        Log.d(LogTag, text);
        show(context, text);

    }

    //删除tag的回调
    @Override
    public void onDeleteTagResult(Context context, int errorCode, String tagName) {
        if (context == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "\"" + tagName + "\"删除成功";
        } else {
            text = "\"" + tagName + "\"删除失败,错误码：" + errorCode;
        }
        Log.d(LogTag, text);
        show(context, text);

    }


    //注册的回调
    @Override
    public void onRegisterResult(Context context, int errorCode,
                                 XGPushRegisterResult message) {
        // TODO Auto-generated method stub
        if (context == null || message == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = message + "注册成功";
            // 在这里拿token
            String token = message.getToken();
            PushUtil.sendBroadcast(context, token);
        } else {
            text = message + "注册失败错误码：" + errorCode;
        }
        Log.d(LogTag, text);
//        show(context, text);
    }

    // 消息透传的回调
    @Override
    public void onTextMessage(Context context, XGPushTextMessage message) {
        // TODO Auto-generated method stub
        if (context == null || message == null) {
            return;
        }
        String text = "收到消息:" + message.toString();
        // 获取自定义key-value
        TanMsg msg=TanMsg.newMsg()
                .type(TanMsg.PUSH_TYPE_PASSTHROUGHT)
                .title(message.getTitle())
                .content(message.getContent())
                .custom(message.getCustomContent());
        PushUtil.sendBroadcast(context,msg);
        // APP自主处理消息的过程...
        Log.d(LogTag, text);
        show(context, text);
    }

}
