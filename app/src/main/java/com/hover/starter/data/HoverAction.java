package com.hover.starter.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "actions")
public class HoverAction {
    @PrimaryKey
    public int uid;

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