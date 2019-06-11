package com.hover.starter.data.actionVariables;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.hover.starter.data.actions.HoverAction;


@Entity(tableName = "action_variables")
public class HoverActionVariable {

    public HoverActionVariable(String actionId, String name) {
        this.actionId = actionId;
        this.name = name;
    }

    @ColumnInfo(name="id")
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    @ColumnInfo(name="action_id")
    public String actionId;

    @NonNull
    @ColumnInfo(name="name")
    public String name;

    @ColumnInfo(name="value")
    public String value;

}


