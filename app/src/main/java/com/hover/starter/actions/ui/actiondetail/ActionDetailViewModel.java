package com.hover.starter.actions.ui.actiondetail;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hover.starter.HoverRepository;
import com.hover.starter.actions.data.HoverActionVariable;
import com.hover.starter.actions.data.HoverAction;
import com.hover.starter.actions.data.HoverTransaction;

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

    LiveData<List<HoverActionVariable>> getAllActionVariablesByActionId() {
        return mRepository.getAllActionVariablesByActionId(mActionId);
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

    void insertTransaction(Intent data) {
        final HoverTransaction transaction = new HoverTransaction(data);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mRepository.insertTransaction(transaction);
            }
        });
    }

    void insertTransaction(Bundle data) {

        final HoverTransaction transaction = new HoverTransaction(data);
        Log.d("ActionDetailViewModel", "action_id: " + transaction.actionId);
        Log.d("ActionDetailViewModel", "status: " + transaction.status);
        Log.d("ActionDetailViewModel", "responseMessage: " + transaction.responseMessage);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                HoverTransaction existingTransaction = mRepository.getTransaction(transaction.uuid);
                if (existingTransaction != null) {
                    transaction.id = existingTransaction.id;
                    mRepository.updateTransaction(transaction);
                } else {
                    mRepository.insertTransaction(transaction);
                }
            }
        });
    }
}
