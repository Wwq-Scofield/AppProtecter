package com.active.daemon.onepxactivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2018/8/13.
 */

public class ScreenActivityManager {
    private static final String TAG = "wwq";
    private Context mContext;
    private static ScreenActivityManager mSreenManager;
    // 使用弱引用，防止内存泄漏
    private WeakReference<Activity> mActivityRef;

    private ScreenActivityManager(Context mContext) {
        this.mContext = mContext.getApplicationContext();
    }

    // 单例模式
    public static ScreenActivityManager getScreenManagerInstance(Context context) {
        if (mSreenManager == null) {
            mSreenManager = new ScreenActivityManager(context);
        }
        return mSreenManager;
    }

    // 获得SinglePixelActivity的引用
    public void setSingleActivity(Activity mActivity) {
        mActivityRef = new WeakReference<>(mActivity);
    }

    // 启动SinglePixelActivity
    public void startActivity() {
        Log.d(TAG, "准备启动SinglePixelActivity...");
        Intent intent = new Intent(mContext, SinglePixelActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    // 结束SinglePixelActivity
    public void finishActivity() {
        Log.d(TAG, "准备结束SinglePixelActivity...");
        if (mActivityRef != null) {
            Activity mActivity = mActivityRef.get();
            if (mActivity != null) {
                mActivity.finish();
            }
        }
    }
}
