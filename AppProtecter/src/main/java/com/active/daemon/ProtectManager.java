package com.active.daemon;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.active.daemon.constant.Constant;
import com.active.daemon.foreService.DaemonService;
import com.active.daemon.jobservice.JobWorkService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/13.
 */

public class ProtectManager {
    private String TAG = "wwq";
    private List<String> classNames;
    private int duration;//JobService 循环间隔
    private int methodType;//开启哪种守护方式
    private boolean isOpen;//是否开启
    private Context mContext;

    public ProtectManager(Configuration configuration) throws DebugException {
        if (configuration == null) {
            throw new DebugException("configuration is can't null");
        }
        this.mContext = configuration.mContext;
        this.classNames = configuration.classNames;
        this.duration = configuration.duration;
        this.methodType = configuration.methodType;
        this.isOpen = configuration.isOpen;
        PrefrenceSp.getmInstance(mContext).setInt(Constant.JOB_DURATION, this.duration);
        PrefrenceSp.getmInstance(mContext).getBoolean(Constant.IS_OPEN_METHOD_1_PX, isOpen);
        if (classNames != null && classNames.size() > 0) {
            PrefrenceSp.getmInstance(mContext).setString(Constant.CLASS_NAME, new Gson().toJson(classNames));
        }
    }

    /**
     * 目前是否开启只有锁屏时的activity可以设置，其他都是开启的
     */
    public static class Configuration {
        private List<String> classNames;
        private int duration;//JobService 循环间隔
        private int methodType;//开启哪种守护方式
        private boolean isOpen;//是否开启
        private Context mContext;
        public Configuration() {
        }

        public Configuration setClassNames(String... names) {
            List<String> mList = new ArrayList<>();
            if(names!=null&&names.length>0){
                for (String name : names) {
                    mList.add(name);
                }
            }
            this.classNames = mList;
            return this;
        }

        public Configuration setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public Configuration setMethodType(int type) {
            this.methodType = type;
            return this;
        }

        public Configuration isOpen(boolean isOpen) {
            this.isOpen = isOpen;
            return this;
        }

        public Configuration setContext(Context context) {
            this.mContext = context;
            return this;
        }

        public ProtectManager config() throws DebugException {
            return new ProtectManager(this);
        }

    }

    /**
     * 开启守护服务
     */
    public void startDameon() {
        if (isOpen) {
            switch (methodType) {
                // TODO: 2018/8/13 这里说明下： 前台服务里注册了解锁屏幕的广播，
                // TODO: 如果前台服务不开启，则没有1px的acitivty
                case MethodType.METHOD_1_PX:
                    PrefrenceSp.getmInstance(mContext).setBoolean(Constant.IS_OPEN_METHOD_1_PX, isOpen);
                case MethodType.METHOD_FORE:
                    // TODO: 2018/8/13 不做什么
                case MethodType.METHOD_JNI:
                    // TODO: 2018/8/13 目前还没好的想法，5.0以上基本无效
                case MethodType.METHOD_JOB:
                    // TODO: 2018/8/13 5.0以上大部分是可以的，目前了解到的是华为屏蔽了,测试手机为nexus5 Ace
                    break;
            }
            if (this.classNames != null && this.classNames.size() > 0) {
                Log.d(TAG, "开启守护服务");
                mContext.startService(new Intent(mContext.getApplicationContext(), DaemonService.class));
                mContext.startService(new Intent(mContext, JobWorkService.class));
            }
        }
    }
}
