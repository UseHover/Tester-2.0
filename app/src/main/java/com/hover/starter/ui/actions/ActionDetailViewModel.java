package com.hover.starter.ui.actions;

import android.app.Application;
import android.content.Intent;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hover.starter.data.HoverRepository;
import com.hover.starter.data.actions.HoverAction;
import com.hover.starter.data.transactions.HoverTransaction;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ActionDetailViewModel extends AndroidViewModel {

    private HoverRepository mRepository;
    private MutableLiveData<HoverAction> action;
    private String mActionId;

    public ActionDetailViewModel(Application application, String actionId) {
        super(application);

        mRepository = new HoverRepository(application);
        mActionId = actionId;
    }

    MutableLiveData<HoverAction> getById() {
        if (action == null)
            action = new MutableLiveData<>();

        loadAction();

        return action;
    }

    LiveData<List<HoverTransaction>> getAllTransactionsByActionId() {
        return mRepository.getAllTransactionsByActionId(mActionId);
    }

    void loadAction(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HoverAction a = mRepository.getAction(mActionId);
                action.postValue(a);
            }
        }).start();
    }

    public void insertTransaction(Intent data) {
        final HoverTransaction transaction = new HoverTransaction(data);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mRepository.insertTransaction(transaction);
            }
        });
    }
}
