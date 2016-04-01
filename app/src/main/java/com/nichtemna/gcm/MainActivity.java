package com.nichtemna.gcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.nichtemna.gcm.gcm.RegistrationIntentService;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_STARTED_FROM_PUSH = "EXTRA_STARTED_FROM_PUSH";
    public static final String TITLE = "title";
    public static final String TIMESTAMP = "timestamp";
    public static final String TEXT = "text";
    public static String BROADCAST_ACTION = "com.nichtemna.gcm.shod_dialog";
    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getMessageDialogFromIntent(intent);
        }
    };


    public static Intent getMessageIntent(String pTitle, String pTimestamp, String pText) {
        Intent intent = new Intent(BROADCAST_ACTION);
        intent.putExtra(EXTRA_STARTED_FROM_PUSH, true);
        intent.putExtra(TITLE, pTitle);
        intent.putExtra(TIMESTAMP, pTimestamp);
        intent.putExtra(TEXT, pText);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //todo add
        initGCM();
        if (getIntent().hasExtra(EXTRA_STARTED_FROM_PUSH) && getIntent().getBooleanExtra(EXTRA_STARTED_FROM_PUSH, false)) {
            getMessageDialogFromIntent(getIntent());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(messageReceiver, new IntentFilter(BROADCAST_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(messageReceiver);
    }

    private void getMessageDialogFromIntent(Intent pIntent) {
        String title = pIntent.getStringExtra(TITLE);
        String text = pIntent.getStringExtra(TEXT);
        showDialog(title, text);
    }

    private void showDialog(String title, String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(text);
        builder.show();
    }

    private void initGCM() {
        if (Utils.checkPlayServices(this)) {
            Intent mIntent = new Intent(this, RegistrationIntentService.class);
            startService(mIntent);
        }
    }
}
