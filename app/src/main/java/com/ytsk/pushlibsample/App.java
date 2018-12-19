package com.ytsk.pushlibsample;

import android.app.Application;

import com.ytsk.pushlib.PushManager;

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
//        PushManager.MI_PUSH_APP_ID="2882303761517914422";
//        PushManager.MI_PUSH_APP_KEY="5941791473422";
//        PushManager.MEIZU_PUSH_APP_ID="117548";
//        PushManager.MEIZU_PUSH_APP_KEY="fcf080a3f0fe4ce8b6abe1f831329566";
        PushManager.initPush(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        PushManager.onTerminate();
    }
}
