package com.hover.starter.data.actionVariables;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HoverActionVariableDao {
    @Query("SELECT * FROM action_variables WHERE action_id = :action_id")
    LiveData<List<HoverActionVariable>> getAllActionVariablesByActionId(String action_id);

    @Insert
    void insert(HoverActionVariable actionVariable);

    @Delete
    void delete(HoverActionVariable actionVariable);

}
