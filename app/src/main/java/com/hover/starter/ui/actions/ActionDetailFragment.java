package com.hover.starter.ui.actions;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hover.starter.R;
import com.hover.starter.data.HoverAction;

import java.util.List;

public class ActionDetailFragment extends Fragment {
    private final String TAG = "ActionDetailFragment";

    private ActionDetailViewModel mViewModel;
    private String mActionId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments().containsKey("actionId")) {
            mActionId = getArguments().getString("actionId");
        }
        Log.d(TAG, "mActionId: " +mActionId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.action_detail_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final TextView textView = getView().findViewById(R.id.message);
        mViewModel = ViewModelProviders.of(this,
                new ActionDetailViewModelFactory(getActivity().getApplication(), mActionId))
                .get(ActionDetailViewModel.class);

        mViewModel.getById().observe(this, new Observer<HoverAction>() {

            @Override
            public void onChanged(HoverAction action) {
                textView.setText(action.getActionName());
            }
        });


    }



}
