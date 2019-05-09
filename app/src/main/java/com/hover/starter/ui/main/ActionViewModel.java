package com.hover.starter.ui.main;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hover.starter.data.HoverAction;
import com.hover.starter.data.HoverActionRepository;

import java.util.List;

public class ActionViewModel extends AndroidViewModel {

    private HoverActionRepository mRepository;
    private LiveData<List<HoverAction>> mAllActions;

    public ActionViewModel(Application application) {
        super(application);

        mRepository = new HoverActionRepository(application);
        mAllActions = mRepository.getAllActions();
    }

    LiveData<List<HoverAction>> getAllActions() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HoverAction action = mRepository.getAnyAction();
                if (action == null) {
                    mRepository.loadActions();
                }
            }
        }).start();

        return mAllActions;
    }

    void loadAllActions() { mRepository.loadActions();}

    public void insert(HoverAction action) { mRepository.insert(action);}
}
