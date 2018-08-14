package com.test.demo;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

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

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.d("wwq","onActivityCreated = "+activity.getClass().getName());
           }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.d("wwq","onActivityDestroyed = "+activity.getClass().getName());
            }
        });
    }
}
