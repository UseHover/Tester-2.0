package com.hover.starter.actions.ui.actiondetail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ActionDetailViewModelFactory implements ViewModelProvider.Factory {

    private Application mApplication;
    private String mActionId;

    public ActionDetailViewModelFactory(Application application, String actionId) {
        mApplication = application;
        mActionId = actionId;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ActionDetailViewModel(mApplication, mActionId);
    }
}
