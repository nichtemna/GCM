package com.nichtemna.gcm;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by lina on 01.04.16.
 */
public class GcmApplication extends Application {
    private static LifecycleManager mManager = new LifecycleManager();
    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(mManager);
    }

    public static boolean isAppForeground() {
        return mManager.isActive();
    }
}
