package com.hover.starter.ui.actions;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.hover.starter.data.HoverAction;
import com.hover.starter.data.HoverActionRepository;

public class ActionDetailViewModel extends AndroidViewModel {

    private HoverActionRepository mRepository;
    private MutableLiveData<HoverAction> action;
    private String mActionId;

    public ActionDetailViewModel(Application application, String actionId) {
        super(application);

        mRepository = new HoverActionRepository(application);
        mActionId = actionId;
    }

    MutableLiveData<HoverAction> getById() {
        if (action == null)
            action = new MutableLiveData<>();

        load();

        return action;
    }

    void load(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HoverAction a = mRepository.getAction(mActionId);
                action.postValue(a);
            }
        }).start();
    }
}
