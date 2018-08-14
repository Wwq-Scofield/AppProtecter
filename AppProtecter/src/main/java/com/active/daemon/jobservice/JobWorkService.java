package com.active.daemon.jobservice;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.active.daemon.PrefrenceSp;
import com.active.daemon.constant.Constant;

import java.util.List;

/**
 */
@SuppressLint("NewApi")
public class JobWorkService extends JobService {
    private boolean isStarted = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("wwq", "jobService启动");
        scheduleJob(getJobInfo());
        startTargetService();
        return START_NOT_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i("wwq", "执行了onStartJob方法");
        if (startTargetService()) return false;
        return true;
    }
    private boolean startTargetService() {
        String classNameString = PrefrenceSp.getmInstance(this).getString(Constant.CLASS_NAME, "");
        Log.d("wwq", "classNameString= " + classNameString);
        if (!TextUtils.isEmpty(classNameString)) {
            List<String> classList = new Gson().fromJson(classNameString, List.class);
            if (classList == null || classList.size() <= 0) {
                return true;
            }
            boolean isLogin = PrefrenceSp.getmInstance(this).getBoolean(Constant.IS_LOGIN, false);
            if (!isLogin) {
                return true;
            }
            int count = classList.size();
            for (int index = 0; index < count; index++) {
                String className = classList.get(index);
                boolean serviceWork = isServiceWork(this, className);
                if (!serviceWork) {
                    Log.d("wwq", "启动service: "+className);
                    Intent intent = new Intent();
                    intent.setClassName(this.getPackageName(), className);
                    this.startService(intent);
                }else{
                    Log.d("wwq", "service 已经启动: ");
                }
            }
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i("wwq", "执行了onStopJob方法");
        scheduleJob(getJobInfo());
        return true;
    }    //将任务作业发送到作业调度中去

    public void scheduleJob(JobInfo t) {
        Log.i("wwq", "调度job");
        JobScheduler tm =
                (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.schedule(t);
    }

    public JobInfo getJobInfo() {
        JobInfo.Builder builder = new JobInfo.Builder(getCurrentProcessPid(), new ComponentName(this, JobWorkService.class));
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setPersisted(true);
        builder.setRequiresCharging(false);
        builder.setRequiresDeviceIdle(false);
        builder.setPeriodic(PrefrenceSp.getmInstance(this).getInt(Constant.JOB_DURATION, 100));//间隔默认、100毫秒
        return builder.build();
    }

    // 判断服务是否正在运行
    public boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(100);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
    private int getCurrentProcessPid() {
        return android.os.Process.myPid();

    }
}