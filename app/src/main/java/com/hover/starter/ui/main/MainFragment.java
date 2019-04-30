package com.hover.starter.ui.main;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hover.starter.MainActivity;
import com.hover.starter.PermissionsListener;
import com.hover.starter.R;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    public PermissionsListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (PermissionsListener) context;
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(View view, Bundle savedStateInstance) {
        if(!MainActivity.hasAllPermissions(getContext())) {
            mListener.requestAdvancedPermissions();
        }
    }

}
