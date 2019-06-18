package com.hover.starter;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.hover.starter.actions.data.HoverActionVariable;
import com.hover.starter.actions.data.HoverActionVariableDao;
import com.hover.starter.actions.data.HoverTransaction;
import com.hover.starter.actions.data.HoverAction;
import com.hover.starter.actions.data.HoverActionDao;
import com.hover.starter.actions.data.HoverTransactionDao;

@Database(entities = {HoverAction.class, HoverTransaction.class, HoverActionVariable.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract HoverActionDao actionDao();
    public abstract HoverTransactionDao transactionDao();
    public abstract HoverActionVariableDao actionTransactionDao();

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `transactions` (`id` INTEGER, "
                    + "`uuid` TEXT, `action_id` TEXT, `ussd_messages` TEXT, `response_message` TEXT,"
                    + "`request_timestamp` INTEGER, `update_timestamp` INTEGER, "
                    + "`status` TEXT, `status_meaning` TEXT, `status_description` TEXT, "
                    + "`transaction_extras` TEXT,  PRIMARY KEY(`id`))");
        }
    };

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "hover.db")
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
