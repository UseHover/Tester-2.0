package com.hover.starter.data.results;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface HoverResultDao {

    @Query("SELECT * FROM results")
    LiveData<List<HoverResult>> getAllResults();

    @Query("SELECT * FROM results where action_id = :actionId")
    LiveData<List<HoverResult>> getResultsByActionId(String actionId);

    @Query("SELECT * FROM results LIMIT 1")
    HoverResult getAnyResult();

    @Query("SELECT * FROM results where uuid = :uuid")
    HoverResult getResult(String uuid);

    @Insert
    void insertAll(HoverResult... results);

    @Insert
    void insert(HoverResult result);

    @Update
    void update(HoverResult result);

    @Delete
    void delete(HoverResult result);

    @Query("DELETE from results")
    void deleteAll();

}
