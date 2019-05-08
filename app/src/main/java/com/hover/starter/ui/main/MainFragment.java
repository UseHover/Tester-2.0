package com.hover.starter.ui.main;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hover.starter.MainActivity;
import com.hover.starter.PermissionsListener;
import com.hover.starter.R;
import com.hover.starter.data.HoverAction;

import java.util.List;

public class MainFragment extends Fragment {

    public PermissionsListener mListener;
    private ActionViewModel mActionViewModel;
    private HoverActionListAdapter adapter;

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
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.action_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new HoverActionListAdapter(getActivity(), (MainActivity) getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final View button = rootView.findViewById(R.id.update_config);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(),
                                getString(R.string.updating),
                                Toast.LENGTH_SHORT).show();
                        mActionViewModel.loadAllActions();
                    }
                }
        );

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionViewModel = ViewModelProviders.of(this).get(ActionViewModel.class);
        mActionViewModel.loadAllActions();
        mActionViewModel.getAllActions().observe(this, new Observer<List<HoverAction>>() {
            @Override
            public void onChanged(@Nullable final List<HoverAction> actions) {
                adapter.setActions(actions);
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedStateInstance) {
        if(!MainActivity.hasAllPermissions(getContext())) {
            mListener.requestAdvancedPermissions();
        }
    }

    public void updateConfig() {
        mActionViewModel.loadAllActions();
    }

}
