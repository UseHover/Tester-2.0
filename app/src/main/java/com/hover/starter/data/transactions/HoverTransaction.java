package com.hover.starter.data.transactions;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.hover.starter.data.actions.HoverAction;


import java.util.HashMap;
import java.util.List;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "transactions", foreignKeys = @ForeignKey(entity = HoverAction.class, parentColumns = "uid", childColumns = "action_id", onDelete = CASCADE))
public class HoverTransaction {

    public HoverTransaction() {
    }

    public HoverTransaction(Intent data) {
        uuid = data.getStringExtra("uuid");
        actionId = data.getStringExtra("action_id");
        ussdMessages = processUssdMessages(data.getStringArrayExtra("ussd_messages"));
    }

    public HoverTransaction(Bundle data) {
        uuid = data.getString("uuid");
        actionId = data.getString("action_id");
        ussdMessages = processUssdMessages(data.getStringArray("ussd_messages"));
        responseMessage = data.getString("response_message");
        status = data.getString("status");
        statusMeaning = data.getString("status_meaning");
        statusDescription = data.getString("status_description");
        requestTimestamp = data.getLong("request_timestamp");
        updateTimestamp = data.getLong("update_timestamp");
        error = data.getString("error");
    }

    @ColumnInfo( name = "id")
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo( name = "uuid")
    public String uuid;

    @ColumnInfo( name = "action_id")
    public String actionId;

    @ColumnInfo( name = "ussd_messages")
    public String ussdMessages;

    @ColumnInfo( name = "response_message")
    public String responseMessage;

    @ColumnInfo( name = "request_timestamp")
    public long requestTimestamp;

    @ColumnInfo(name = "update_timestamp")
    public long updateTimestamp;

    @ColumnInfo( name = "status")
    public String status;

    @ColumnInfo( name = "status_meaning")
    public String statusMeaning;

    @ColumnInfo( name = "status_description")
    public  String statusDescription;

    @ColumnInfo( name = "transaction_extras")
    public String transactionExtras;

    @ColumnInfo(name = "error")
    public String error;

    private String processUssdMessages(String[] ussdMessagesArray) {
        if (ussdMessagesArray == null)
            return "";
        StringBuffer ussdMessages = new StringBuffer();
        for (int i = 0; i < ussdMessagesArray.length; i++) {
            ussdMessages.append(ussdMessagesArray[i]);
        }
        return ussdMessages.toString();
    }

}
