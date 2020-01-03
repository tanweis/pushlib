package com.ytsk.pushlibsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ytsk.pushlib.PushManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PushManager.initPush(this);
    }
}
