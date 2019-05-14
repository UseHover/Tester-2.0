package com.hover.starter.data;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.hover.starter.data.actions.HoverAction;
import com.hover.starter.data.actions.HoverActionDao;
import com.hover.starter.data.results.HoverResult;
import com.hover.starter.data.results.HoverResultDao;
import com.hover.starter.data.workers.GetHoverActionsWorker;

import java.util.List;


public class HoverRepository {

    private HoverActionDao mActionDao;
    private HoverResultDao mResultDao;
    private LiveData<List<HoverAction>> mAllActions;
    private WorkManager mWorkManager;


    public HoverRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mActionDao = db.actionDao();
        mResultDao = db.resultDao();
        mAllActions = mActionDao.getAllActions();
        mWorkManager = WorkManager.getInstance();
    }

    public LiveData<List<HoverAction>> getAllActions() {
        return mAllActions;
    }

    public HoverAction getAction(String actionId) {
        return mActionDao.getAction(actionId);
    }

    public HoverAction getAnyAction() {
        return mActionDao.getAnyAction();
    }

    public void loadActions() {
        mWorkManager.enqueue(OneTimeWorkRequest.from(GetHoverActionsWorker.class));
    }

    public LiveData<List<HoverResult>> getAllResultsByActionId(String actionId) {
        return mResultDao.getResultsByActionId(actionId);
    }

    public void insertResult(HoverResult result) {
        mResultDao.insert(result);
    }

    public LiveData<List<HoverResult>> getAllResults() {
        Log.d("HoverRepository", "getAllResults");
        return mResultDao.getAllResults();
    }
}


