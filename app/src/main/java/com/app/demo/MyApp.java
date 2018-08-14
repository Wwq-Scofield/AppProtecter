package com.app.demo;

import android.app.Application;

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
                    .setClassNames(new String[]{"com.xdja.xypolice.base.core.CoreService"})
                    .setDuration(60)
                    .setMethodType(MethodType.METHOD_JNI)
                    .config()
                    .startDameon();
        } catch (DebugException e) {
            e.printStackTrace();
        }
    }
}
