package com.hover.starter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class TransactionReceiver extends BroadcastReceiver {
    public TransactionReceiver() { }

    @Override
    public void onReceive(Context context, Intent intent) {
        String uuid = intent.getStringExtra("uuid");
        Log.e("TransactionReceiver", uuid);
    }
}
