package com.hover.starter.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hover.starter.data.results.HoverResult;
import com.hover.starter.data.actions.HoverAction;
import com.hover.starter.data.actions.HoverActionDao;
import com.hover.starter.data.results.HoverResultDao;

@Database(entities = {HoverAction.class, HoverResult.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract HoverActionDao actionDao();
    public abstract HoverResultDao resultDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "hover.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
