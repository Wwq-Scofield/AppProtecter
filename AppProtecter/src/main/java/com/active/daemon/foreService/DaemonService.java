package com.active.daemon.foreService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.active.daemon.PrefrenceSp;
import com.active.daemon.constant.Constant;
import com.active.daemon.jobservice.JobWorkService;
import com.active.daemon.onepxactivity.ScreenActivityManager;

public class DaemonService extends Service {
    private static final String TAG = "wwq";
    public static final int NOTICE_ID = 100;
    private ScreenReceiver receiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "DaemonService---->onCreate被调用，启动前台service");
        //如果API大于18，需要弹出一个可见通知  
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Notification.Builder builder = new Notification.Builder(this);
            builder.setContentTitle("KeepAppAlive");
            builder.setContentText("DaemonService is runing...");
            startForeground(NOTICE_ID, builder.build());
            // 如果觉得常驻通知栏体验不好  
            // 可以通过启动CancelNoticeService，将通知移除，oom_adj值不变  
            Intent intent = new Intent(this, CancelNoticeService.class);
            startService(intent);
        } else {
            startForeground(NOTICE_ID, new Notification());
        }
        boolean isOpen = PrefrenceSp.getmInstance(this).getBoolean(Constant.IS_OPEN_METHOD_1_PX, true);
        if (isOpen) {
            if (receiver == null) {
                receiver = new ScreenReceiver();
                IntentFilter filter=new IntentFilter();
                filter.addAction(Intent.ACTION_SCREEN_ON);
                filter.addAction(Intent.ACTION_SCREEN_OFF);
                registerReceiver(receiver, filter);
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 如果Service被终止  
        // 当资源允许情况下，重启service  
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 如果Service被杀死，干掉通知  
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            NotificationManager mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mManager.cancel(NOTICE_ID);
        }
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        Log.d(TAG, "DaemonService---->onDestroy，前台service被杀死");
        // 重启自己  
        Intent intent = new Intent(getApplicationContext(), DaemonService.class);
        startService(intent);
        startService(new Intent(getApplicationContext(), JobWorkService.class));
    }


    public class ScreenReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                Log.i("wwq", "熄屏，启动activity");
                ScreenActivityManager.getScreenManagerInstance(context).startActivity();
            }else if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
                ScreenActivityManager.getScreenManagerInstance(context).finishActivity();
            }
        }
    }

}