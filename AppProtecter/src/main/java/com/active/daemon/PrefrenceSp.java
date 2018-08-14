package com.active.daemon;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Administrator on 2018/8/13.
 */

public class PrefrenceSp {
    private static PrefrenceSp mInstance;
    private SharedPreferences sp;

    public PrefrenceSp(Context context) {
        if (sp == null) {
            sp = PreferenceManager.getDefaultSharedPreferences(context);
        }
    }
    public static PrefrenceSp getmInstance(Context context) {
        if (mInstance == null) {
            synchronized (PrefrenceSp.class) {
                mInstance = new PrefrenceSp(context);
            }
        }
        return mInstance;
    }

    public void setInt(String key, int value) {
        sp.edit().putInt(key, value).commit();
    }

    public int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public void setBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public void setString(String key, String value) {
        sp.edit().putString(key, value).commit();
    }

    public String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

}
