package com.hover.starter.actions.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.hover.starter.actions.data.HoverAction;

import java.util.List;

@Dao
public interface HoverActionDao {
    @Query("SELECT * FROM actions")
    LiveData<List<HoverAction>> getAllActions();

    @Query("SELECT * FROM actions LIMIT 1")
    HoverAction getAnyAction();

    @Query("SELECT * FROM actions WHERE uid IN (:actionIds)")
    List<HoverAction> loadAllbyIds(int[] actionIds);

    @Query("SELECT * FROM actions WHERE uid = :uid LIMIT 1")
    HoverAction getAction(String uid);

    @Insert
    void insertAll(HoverAction... actions);

    @Insert
    void insert(HoverAction action);

    @Update
    void update(HoverAction action);


    @Delete
    void delete(HoverAction action);

    @Query("DELETE FROM actions")
    void deleteAll();
}