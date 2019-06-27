package com.hover.starter.actions.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface HoverTransactionDao {

    @Query("SELECT * FROM transactions ORDER BY request_timestamp DESC")
    LiveData<List<HoverTransaction>> getAllTransactions();

    @Query("SELECT * FROM transactions where action_id = :actionId ORDER BY request_timestamp DESC")
    LiveData<List<HoverTransaction>> getTransactionsByActionId(String actionId);

    @Query("SELECT * FROM transactions LIMIT 1")
    HoverTransaction getAnyTransaction();

    @Query("SELECT * FROM transactions where uuid = :uuid")
    HoverTransaction getTransaction(String uuid);

    @Insert
    void insertAll(HoverTransaction... transactions);

    @Insert
    void insert(HoverTransaction transaction);

    @Update
    void update(HoverTransaction transaction);

    @Delete
    void delete(HoverTransaction transaction);

    @Query("DELETE from transactions")
    void deleteAll();

}
