package com.hover.starter.actions.ui.actiondetail;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.hover.starter.R;
import com.hover.starter.actions.data.HoverActionVariable;

import java.util.List;

public class HoverActionVariableListAdapter extends RecyclerView.Adapter<HoverActionVariableListAdapter.VariableViewHolder> {

    class VariableViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private final TextInputLayout mTextLayout;
        private final EditText mEdit;

        public HoverActionVariable actionVariable;

        private VariableViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mTextLayout = (TextInputLayout) mView.findViewById(R.id.variable_input);
            mEdit = (EditText) mView.findViewById(R.id.edit);
        }
    }

    private final LayoutInflater mInflater;
    private List<HoverActionVariable> mVariables;

    public HoverActionVariableListAdapter(
            Context context
    ) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public HoverActionVariableListAdapter.VariableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.variable_item, parent, false);
        return new HoverActionVariableListAdapter.VariableViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final HoverActionVariableListAdapter.VariableViewHolder holder, int position) {
        if (mVariables != null) {
            HoverActionVariable current = mVariables.get(position);
            holder.actionVariable = current;
            holder.mTextLayout.setHint(holder.actionVariable.name);
            holder.mEdit.setText(holder.actionVariable.value);
            holder.mEdit.addTextChangedListener(new TextWatcher() {
                @Override public void afterTextChanged(Editable s) { }
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                    holder.actionVariable.value = s.toString();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mVariables != null)
            return mVariables.size();
        else return 0;
    }

    void setActionVariables(List<HoverActionVariable> actionVariables) {
        mVariables = actionVariables;
        notifyDataSetChanged();
    }


}
