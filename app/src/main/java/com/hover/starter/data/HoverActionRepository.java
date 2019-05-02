package com.hover.starter.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class HoverActionRepository {

    private HoverActionDao mActionDao;
    private LiveData<List<HoverAction>> mAllActions;

    public HoverActionRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mActionDao = db.actionDao();
        mAllActions = mActionDao.getAllActions();
    }

    public LiveData<List<HoverAction>> getAllActions() {
        return mAllActions;
    }

    public void insert(HoverAction action) {
        new insertAsyncTask(mActionDao).execute(action);
    }

    private static class insertAsyncTask extends AsyncTask<HoverAction, Void, Void> {
        private HoverActionDao mAsyncTaskDao;

        insertAsyncTask(HoverActionDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final HoverAction... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}


