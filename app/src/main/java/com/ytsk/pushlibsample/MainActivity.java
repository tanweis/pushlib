package com.ytsk.pushlibsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.huawei.android.hms.agent.HMSAgent;
import com.huawei.android.hms.agent.common.handler.ConnectHandler;
import com.huawei.android.hms.agent.push.handler.GetTokenHandler;
import com.ytsk.pushlib.PushUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (PushUtil.sysType()==PushUtil.SYS_HUAWEI) {
            HMSAgent.connect(this, new ConnectHandler() {
                @Override
                public void onConnect(int rst) {
                    HMSAgent.Push.getToken(new GetTokenHandler() {
                        @Override
                        public void onResult(int rst) {
                            Toast.makeText(MainActivity.this, " token rst:" + rst, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }
    }
}
