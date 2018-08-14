package com.test.demo;

import android.app.ActivityManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.active.daemon.PrefrenceSp;
import com.active.daemon.constant.Constant;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * 设置是否开启守护service，比如说用户登录后才可以开启守护，作为一个开关
         */
        PrefrenceSp.getmInstance(this).setBoolean(Constant.IS_LOGIN,true);
    }


}
