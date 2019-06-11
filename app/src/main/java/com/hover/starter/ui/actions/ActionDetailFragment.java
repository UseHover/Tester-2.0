package com.hover.starter.ui.actions;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.hover.sdk.api.HoverParameters;
import com.hover.starter.ActionDetail;
import com.hover.starter.R;
import com.hover.starter.data.actionVariables.HoverActionVariable;
import com.hover.starter.data.actions.HoverAction;
import com.hover.starter.data.transactions.HoverTransaction;

import java.util.List;

public class ActionDetailFragment extends Fragment {

    private ActionDetailViewModel mViewModel;
    private HoverTransactionListAdapter transactionAdapter;
    private HoverActionVariableListAdapter actionVariableListAdapter;
    private RecyclerView actionVariableRecyclerView;
    public String mActionId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        assert getArguments() != null;
        if (getArguments().containsKey("action_id")) {
            mActionId = getArguments().getString("action_id");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        View rootView = inflater.inflate(R.layout.action_detail_fragment, container, false);
        RecyclerView transactionRecyclerView = rootView.findViewById(R.id.transaction_list);
        transactionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        transactionAdapter = new HoverTransactionListAdapter(getActivity(), (ActionDetail) getActivity());
        transactionRecyclerView.setAdapter(transactionAdapter);

        actionVariableRecyclerView = rootView.findViewById(R.id.variable_list);
        actionVariableRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        actionVariableListAdapter = new HoverActionVariableListAdapter(getActivity());
        actionVariableRecyclerView.setAdapter(actionVariableListAdapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("ActionDetailFragment", "has uuid:" + getArguments().containsKey("uuid"));


        mViewModel = ViewModelProviders.of(this,
                new ActionDetailViewModelFactory(getActivity().getApplication(), mActionId))
                .get(ActionDetailViewModel.class);

        if (getArguments().containsKey("uuid")) mViewModel.insertTransaction(getArguments());
        mViewModel.getById().observe(this, new Observer<HoverAction>() {

            @Override
            public void onChanged(HoverAction action) {
                setActionDetails(action);
            }
        });
        mViewModel.getAllTransactionsByActionId().observe(this, new Observer<List<HoverTransaction>>() {
            @Override
            public void onChanged(List<HoverTransaction> hoverResults) {
                transactionAdapter.setTransactions(hoverResults);
            }
        });
        mViewModel.getAllActionVariablesByActionId().observe(this, new Observer<List<HoverActionVariable>>() {

            @Override
            public void onChanged(List<HoverActionVariable> hoverActionVariables) {
                actionVariableListAdapter.setActionVariables(hoverActionVariables);
            }
        });
    }

    private void setActionDetails(HoverAction action) {
        ((ActionDetail) getActivity()).setTitle(action.uid + ". " + action.actionName,
                action.actionNetwork);
    }

    public void onResultReceived(Intent data) {
        Bundle b = data.getExtras();
        Log.d("ActionDetailFragment","has action_id "+ b.containsKey("action_id"));
        mViewModel.insertTransaction(data);
    }

    public Intent initializeHoverParametersBuilder() throws NullPointerException {
        HoverParameters.Builder hoverParametersBuilder = new HoverParameters.Builder(getActivity())
                .request(mActionId)
                .style(R.style.SDKTheme);

        for (int i = 0; i < actionVariableListAdapter.getItemCount(); i++) {
            HoverActionVariable va = ((HoverActionVariableListAdapter.VariableViewHolder) actionVariableRecyclerView.findViewHolderForAdapterPosition(i)).actionVariable;

            if (va.value == null || va.value.isEmpty())
                throw new NullPointerException("You must provide a value for " + va.value);
            hoverParametersBuilder.extra(va.name, va.value);

        }
        return hoverParametersBuilder.buildIntent();
    }
}
