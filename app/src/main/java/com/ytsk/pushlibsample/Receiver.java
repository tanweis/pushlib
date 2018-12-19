package com.ytsk.pushlibsample;

import android.content.Context;
import android.widget.Toast;

import com.ytsk.pushlib.TanMsg;
import com.ytsk.pushlib.TanPushReceiver;

public class Receiver extends TanPushReceiver {


    @Override
    protected void onToken(Context context, String token) {
        Toast.makeText(context,"token: "+token,Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onMsg(Context context, TanMsg msg) {
        Toast.makeText(context,msg.toString(),Toast.LENGTH_SHORT).show();
    }


}
