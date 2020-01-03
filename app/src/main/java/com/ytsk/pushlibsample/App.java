package com.ytsk.pushlibsample;

import android.app.Application;
import android.content.Context;

import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.agconnect.config.LazyInputStream;

import java.io.IOException;
import java.io.InputStream;

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        AGConnectServicesConfig config = AGConnectServicesConfig.fromContext(base);
        config.overlayWith(new LazyInputStream(base){
            public InputStream get(Context context) {
                try {
                    return context.getAssets().open("agconnect-services.json");
                } catch (IOException e) {
                    return null;
                }
            }
        });
    }
}
