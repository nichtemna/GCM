package com.nichtemna.gcm;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by lina on 01.04.16.
 */
public class LifecycleManager implements Application.ActivityLifecycleCallbacks{
    private static boolean isActive;

    public static boolean isActive() {
        return isActive;
    }
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
        isActive = true;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        isActive = false;
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
