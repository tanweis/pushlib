package com.ytsk.pushlib.huawei;

import android.util.Log;

import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;
import com.ytsk.pushlib.PushUtil;
import com.ytsk.pushlib.TanMsg;

/**
 * @author tan.
 * @date 2020-01-03.
 */
public class HuWeiService extends HmsMessageService {

    @Override
    public void onNewToken(String s) {
        PushUtil.sendBroadcast(this,s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage==null||remoteMessage.getNotification()==null) {
            return;
        }
        String data=remoteMessage.getData();
        Log.i("huawei",data);
        TanMsg msg=TanMsg.newMsg()
                .type(TanMsg.PUSH_TYPE_PASSTHROUGHT)
                .title(remoteMessage.getNotification().getTitle())
                .content(remoteMessage.getNotification().getBody())
                .custom(remoteMessage.getData());
        PushUtil.sendBroadcast(this,msg);

    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();

    }
}
