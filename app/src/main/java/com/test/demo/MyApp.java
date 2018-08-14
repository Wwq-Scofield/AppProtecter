package com.test.demo;

import android.app.Application;

import com.active.daemon.DebugException;
import com.active.daemon.MethodType;
import com.active.daemon.ProtectManager;

/**
 * Created by Administrator on 2018/8/14.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            new ProtectManager.Configuration()
                    .isOpen(true)
                    .setContext(this)
                    .setClassNames(new String[]{"com.test.demo.TargetService"})
                    .setDuration(100)
                    .setMethodType(MethodType.METHOD_JNI)
                    .config()
                    .startDameon();
        } catch (DebugException e) {
            e.printStackTrace();
        }
    }
}
