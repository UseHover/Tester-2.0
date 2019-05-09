package com.hover.starter.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.hover.starter.network.NetworkOps;

import java.util.List;


public class HoverActionRepository {

    private AppDatabase db;
    private HoverActionDao mActionDao;
    private LiveData<List<HoverAction>> mAllActions;
    protected NetworkOps netOps;


    public HoverActionRepository(Application application) {
        db = AppDatabase.getInstance(application);
        mActionDao = db.actionDao();
        mAllActions = mActionDao.getAllActions();
        netOps = new NetworkOps(application);
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
        new GetHoverActionsAsync(db, netOps).execute();
    }
}


