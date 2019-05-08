package com.hover.starter.ui.actions;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hover.starter.data.HoverAction;
import com.hover.starter.data.HoverActionRepository;

public class ActionDetailViewModel extends AndroidViewModel {

    private HoverActionRepository mRepository;
    private LiveData<HoverAction> mGetById;

    public ActionDetailViewModel(Application application, String mActionId) {
        super(application);

        mRepository = new HoverActionRepository(application);
        mGetById = mRepository.getAction(mActionId);
    }

    LiveData<HoverAction> getById() {
        return mGetById;
    }
}
