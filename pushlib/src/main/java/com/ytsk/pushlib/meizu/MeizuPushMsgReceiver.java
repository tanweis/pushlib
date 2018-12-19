package com.ytsk.pushlib.meizu;

import android.content.Context;

import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import com.meizu.cloud.pushsdk.handler.MzPushMessage;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;
import com.ytsk.pushlib.PushUtil;
import com.ytsk.pushlib.TanMsg;

public class MeizuPushMsgReceiver extends MzPushMessageReceiver {

    @Override
    @Deprecated
    public void onRegister(Context context, String pushid) {
        //调用PushManager.register(context）方法后，会在此回调注册状态
        //应用在接受返回的pushid
//        RxBus.getDefault().post(pushid);
//        LoginConstants.setPushToken(pushid);
//        RxBusReplaySubject.getDefault().post(pushid);
        if (context == null || pushid == null) return;
        PushUtil.sendBroadcast(context, pushid);
    }

    @Override
    public void onMessage(Context context, String s) {
        if (context==null||s==null)
            return;
        //接收服务器推送的透传消息
        TanMsg msg=TanMsg.newMsg()
                .type(TanMsg.PUSH_TYPE_PASSTHROUGHT)
                .custom(s);
        PushUtil.sendBroadcast(context,msg);
    }

    @Override
    @Deprecated
    public void onUnRegister(Context context, boolean b) {
        //调用PushManager.unRegister(context）方法后，会在此回调反注册状态
    }

    //设置通知栏小图标
    @Override
    public void onUpdateNotificationBuilder(PushNotificationBuilder pushNotificationBuilder) {
        //重要,详情参考应用小图标自定设置
//        pushNotificationBuilder.setmStatusbarIcon(R.drawable.mz_push_notification_small_icon);
    }

    @Override
    public void onPushStatus(Context context, PushSwitchStatus pushSwitchStatus) {
        //检查通知栏和透传消息开关状态回调
    }

    @Override
    public void onRegisterStatus(Context context, RegisterStatus registerStatus) {
        //调用新版订阅PushManager.register(context,appId,appKey)回调
    }

    @Override
    public void onUnRegisterStatus(Context context, UnRegisterStatus unRegisterStatus) {
        //新版反订阅回调
    }

    @Override
    public void onSubTagsStatus(Context context, SubTagsStatus subTagsStatus) {
        //标签回调
    }

    @Override
    public void onSubAliasStatus(Context context, SubAliasStatus subAliasStatus) {
        //别名回调
    }

    @Override
    public void onNotificationClicked(Context context, MzPushMessage mzPushMessage) {
//        super.onNotificationClicked(context, mzPushMessage);
        String cus = mzPushMessage.getSelfDefineContentString();
        TanMsg msg=TanMsg.newMsg()
                .type(TanMsg.PUSH_TYPE_NOTIFICATION)
                .title(mzPushMessage.getTitle())
                .content(mzPushMessage.getContent())
                .custom(mzPushMessage.getSelfDefineContentString());
        PushUtil.sendBroadcast(context,msg);
//        try {
//            Long wayBillId=new JSONObject(cus).getLong("waybillId");
////            Intent intent=new Intent(context, OrderDetailActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.putExtra("id",wayBillId);
//            context.startActivity(intent);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

    }


    //    @Override
//    public void onNotificationArrived(Context context, String title, String content, String selfDefineContentString) {
//        //通知栏消息到达回调，flyme6基于android6.0以上不再回调
//    }
//
//    @Override
//    public void onNotificationClicked(Context context, String title, String content, String selfDefineContentString) {
//        //通知栏消息点击回调
//    }
//
//    @Override
//    public void onNotificationDeleted(Context context, String title, String content, String selfDefineContentString) {
//        //通知栏消息删除回调；flyme6基于android6.0以上不再回调
//    }

}
