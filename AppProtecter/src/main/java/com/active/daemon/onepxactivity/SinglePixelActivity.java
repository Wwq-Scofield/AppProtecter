package com.active.daemon.onepxactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Administrator on 2018/8/13.
 */

public class SinglePixelActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.x=0;
        attributes.y=0;
        attributes.height=1;
        attributes.width=1;
        window.setGravity(Gravity.LEFT|Gravity.TOP);
        window.setAttributes(attributes);

        ScreenActivityManager.getScreenManagerInstance(this).setSingleActivity(this);

    }
}
