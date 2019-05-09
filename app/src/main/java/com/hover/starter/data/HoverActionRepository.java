package com.hover.starter.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.hover.starter.data.workers.GetHoverActionsWorker;

import java.util.List;


public class HoverActionRepository {

    private HoverActionDao mActionDao;
    private LiveData<List<HoverAction>> mAllActions;
    private WorkManager mWorkManager;


    public HoverActionRepository(Application application) {
        mActionDao = AppDatabase.getInstance(application).actionDao();
        mAllActions = mActionDao.getAllActions();
        mWorkManager = WorkManager.getInstance();
    }

    public LiveData<List<HoverAction>> getAllActions() {
        return mAllActions;
    }

    public HoverAction getAction(String mActionId) {
        return mActionDao.getAction(mActionId);
    }

    public void insert(HoverAction action) {
        new insertAsyncTask(mActionDao).execute(action);
    }

    public HoverAction getAnyAction() {
        return mActionDao.getAnyAction();
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

    public void loadActions() {
        mWorkManager.enqueue(OneTimeWorkRequest.from(GetHoverActionsWorker.class));
    }
}


