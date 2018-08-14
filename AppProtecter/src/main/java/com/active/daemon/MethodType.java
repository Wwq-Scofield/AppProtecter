package com.active.daemon;

/**
 * Created by Administrator on 2018/8/13.
 */

public class MethodType {
    /**
     * 目的:系统定时检测拉起目标服务
     * JobService
     */
    public static final int METHOD_JOB=0;
    /**
     * 目的: 是提高进程Service的oom_adj值，因为oom_adj越小，越不容易被系统杀死
     * 启动一个1px的acticvity，保持后台活跃，避免被杀
     */
    public static final int METHOD_1_PX=1;
    /**
     * 目的: 是提高进程Service的oom_adj值，因为oom_adj越小，越不容易被系统杀死
     * 这里可以通过su -> cat /proc/{pic}/oom_adj 查看
     *
     */
    public static final int METHOD_FORE=2;
    /**
     * 目的：底层jni实现拉起service
     * 双进程守护
     */
    public static final int METHOD_JNI=3;
}
