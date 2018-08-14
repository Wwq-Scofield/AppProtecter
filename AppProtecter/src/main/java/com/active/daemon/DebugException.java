package com.active.daemon;

import android.util.Log;

/**
 * Created by Administrator on 2018/8/13.
 */

public class DebugException extends Exception {

    public DebugException(String message) {
        super(message);
        Log.d("wwq", message);
    }

    public DebugException(String message, Throwable cause) {
        super(message, cause);
    }
    @Override
    public void printStackTrace() {
        super.printStackTrace();
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for (StackTraceElement o : elements) {
            Log.e("wwq", "文件名:" + o.getClassName() + "函数名:" + o.getMethodName() + "调用函数" + o.getLineNumber());
        }
    }
}
