package com.hover.starter.data.actions;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;


@Entity(tableName = "actions")
public class HoverAction {

    public HoverAction(JSONObject jsonObject) {
        try {
            this.uid = jsonObject.getString("id");
            this.actionName = jsonObject.getString("name");
            this.actionNetwork = jsonObject.getString("network_name");
            this.actionSim = jsonObject.getString("root_code");
        } catch (JSONException e) {
            Log.d("exception", e.getMessage());
        }
    }

    public HoverAction(String uid, String actionName, String actionNetwork, String actionSim) {
        this.uid = uid;
        this.actionName = actionName;
        this.actionNetwork = actionNetwork;
        this.actionSim = actionSim;
    }

    @PrimaryKey
    @NonNull
    public String uid;

    @NonNull
    @ColumnInfo(name = "action_name")
    public String actionName;

    @NonNull
    @ColumnInfo(name = "action_network")
    public String actionNetwork;

    @NonNull
    @ColumnInfo(name = "action_sim")
    public String actionSim;

    @ColumnInfo(name = "action_variables")
    public String actionVariables;

    @ColumnInfo(name = "action_pin")
    public String actionPin;

    public String getActionName() { return this.actionName; }
}