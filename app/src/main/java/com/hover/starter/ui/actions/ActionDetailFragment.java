package com.hover.starter.ui.actions;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hover.starter.ActionDetail;
import com.hover.starter.R;
import com.hover.starter.data.actions.HoverAction;
import com.hover.starter.data.results.HoverResult;

import java.util.List;

public class ActionDetailFragment extends Fragment {

    private ActionDetailViewModel mViewModel;
    private HoverResultListAdapter adapter;
    private String mActionId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments().containsKey("actionId")) {
            mActionId = getArguments().getString("actionId");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        View rootView = inflater.inflate(R.layout.action_detail_fragment, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.result_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new HoverResultListAdapter(getActivity(), (ActionDetail) getActivity());
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this,
                new ActionDetailViewModelFactory(getActivity().getApplication(), mActionId))
                .get(ActionDetailViewModel.class);

        mViewModel.getById().observe(this, new Observer<HoverAction>() {

            @Override
            public void onChanged(HoverAction action) {
                setActionDetails(action);
            }
        });
        mViewModel.getAllResultsByActionId().observe(this, new Observer<List<HoverResult>>() {
            @Override
            public void onChanged(List<HoverResult> hoverResults) {
                adapter.setResults(hoverResults);
            }
        });
    }

    private void setActionDetails(HoverAction action) {
        ((ActionDetail) getActivity()).setTitle(action.uid + ". " + action.actionName,
                action.actionNetwork);
    }

    public void onResultReceived(Intent data) {
        mViewModel.insertResult(data);
    }
}
