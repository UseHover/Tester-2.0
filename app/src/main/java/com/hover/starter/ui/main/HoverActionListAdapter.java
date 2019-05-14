package com.hover.starter.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hover.starter.R;
import com.hover.starter.data.actions.HoverAction;

import java.util.List;

public class HoverActionListAdapter
        extends RecyclerView.Adapter<HoverActionListAdapter.ActionViewHolder> {

    private final OnActionListItemClickListener onActionListItemClickListener;

    class ActionViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private final TextView actionItemView;
        private String actionId;

        private ActionViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            actionItemView = itemView.findViewById(R.id.action_name);
        }
    }

    private final LayoutInflater mInflater;
    private List<HoverAction> mActions;

    public HoverActionListAdapter(Context context,
                                  OnActionListItemClickListener onActionListItemClickListener) {
        mInflater = LayoutInflater.from(context);
        this.onActionListItemClickListener = onActionListItemClickListener;
    }

    @Override
    public ActionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.action_item, parent, false);
        return new ActionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ActionViewHolder holder, int position) {
        if (mActions != null) {
            HoverAction current = mActions.get(position);
            holder.actionId = current.uid;
            holder.actionItemView.setText(current.getActionName());
        } else {
            holder.actionItemView.setText("No actions fetched yet");
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onActionListItemClickListener != null)
                    onActionListItemClickListener.onActionListItemClick(holder.actionId);
            }
        });
    }

    void setActions(List<HoverAction> actions){
        mActions = actions;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if (mActions != null)
            return mActions.size();
        else return 0;
    }

    public interface OnActionListItemClickListener {
        void onActionListItemClick(String actionId);
    }
}
