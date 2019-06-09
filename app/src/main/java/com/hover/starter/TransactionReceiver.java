package com.hover.starter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hover.starter.data.HoverRepository;

import java.util.Date;
import java.util.HashMap;


public class TransactionReceiver extends BroadcastReceiver {
    public TransactionReceiver() { }

    @Override
    public void onReceive(Context context, Intent intent) {
        String uuid = intent.getStringExtra("uuid");
        String actionId = intent.getStringExtra("action_id");
        String[] ussdMessages = intent.getStringArrayExtra("ussd_messages");
        String responseMessage = intent.getStringExtra("response_message");
        String status = intent.getStringExtra("status");
        String statusMeaning = intent.getStringExtra("status_meaning");
        String statusDescription = intent.getStringExtra("status_description");
        long requestTimestamp = intent.getLongExtra("request_timestamp", 0);
        long updateTimestamp = intent.getLongExtra("update_timestamp", 0);

        Log.d("TransactionReceiver", "TRANSACTION DETAILS");
        Log.d("TransactionReceiver", "uuid: "+ uuid);
        Log.d("TransactionReceiver", "actionId: "+actionId);
        Log.d("TransactionReceiver", "responseMessage: "+responseMessage);
        Log.d("TransactionReceiver", "status: "+status);
        Log.d("TransactionReceiver", "statusMeaning: "+ statusMeaning);
        Log.d("TransactionReceiver", "statusDescription: "+statusDescription);
        Log.d("TransactionReceiver", "requestTimestamp: "+ requestTimestamp);
        Log.d("TransactionReceiver", "updateTimestamp: " + updateTimestamp);

        intent = new Intent(intent);
        intent.setClass(context, ActionDetail.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
