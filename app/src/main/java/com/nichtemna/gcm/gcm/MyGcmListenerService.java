package com.nichtemna.gcm.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;
import com.nichtemna.gcm.GcmApplication;
import com.nichtemna.gcm.MainActivity;
import com.nichtemna.gcm.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lina on 31.03.16.
 */
public class MyGcmListenerService extends GcmListenerService {

    //    receive message from GCM
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        try {
            JSONObject jsonObject = new JSONObject(message);
            String title = jsonObject.getString("title");
            String timestamp = jsonObject.getString("timestamp");
            String text = jsonObject.getString("text");

            // if app is foreground - send broadcast for MainActivity to show popup
            // if app is not foreground - show notification
            if (GcmApplication.isAppForeground()) {
                sendBroadcast(MainActivity.getIntentForBroadcast(title, timestamp, text));
            } else {
                showNotification(title, timestamp, text);
            }
        } catch (JSONException pE) {
            pE.printStackTrace();
        }
    }

    private void showNotification(String pTitle, String pTimestamp, String message) {
        Intent intent = MainActivity.getIntentForNotification(this, pTitle, pTimestamp, message);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setSubText(pTimestamp)
                .setContentTitle(pTitle)
                .setContentTitle("GCM Message")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}