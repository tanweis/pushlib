package com.ytsk.pushlib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public abstract class TanPushReceiver extends BroadcastReceiver {

    protected String TAG = this.getClass().getSimpleName();

    @Override
    public final void onReceive(Context context, Intent intent) {
        if (context == null || intent == null)
            return;
        if (PushUtil.INTENT_LIB_ACTION_TOKEN.equals(intent.getAction())) {
            String token = intent.getStringExtra(PushUtil.INTENT_LIB_DATA);
            if (token == null) return;
            onToken(context, token);
        } else if (PushUtil.INTENT_LIB_ACTION_MSG.equals(intent.getAction())) {
            TanMsg msg = intent.getParcelableExtra(PushUtil.INTENT_LIB_DATA);
            if (msg == null) return;
            onMsg(context, msg);
        }
    }

    protected abstract void onToken(Context context, String token);

    protected abstract void onMsg(Context context, TanMsg msg);
}
