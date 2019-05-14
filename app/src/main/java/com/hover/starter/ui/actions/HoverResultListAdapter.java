package com.hover.starter.ui.actions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hover.starter.R;
import com.hover.starter.data.results.HoverResult;

import java.util.List;

public class HoverResultListAdapter extends RecyclerView.Adapter<HoverResultListAdapter.ResultViewHolder> {

    class ResultViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private final TextView resultUssdMessagesTextView;
        private final TextView timestampTextView;
        private String resultId;

        private ResultViewHolder (View itemView) {
            super(itemView);
            mView = itemView;
            resultUssdMessagesTextView = itemView.findViewById(R.id.result_text);
            timestampTextView = itemView.findViewById(R.id.timestamp);
        }
    }

    private final LayoutInflater mInflater;
    private List<HoverResult> mResults;
    private final OnResultListItemClickListener onResultListItemClickListener;

    public HoverResultListAdapter(
            Context context,
            OnResultListItemClickListener onResultListItemClickListener
    ) {
        mInflater = LayoutInflater.from(context);
        this.onResultListItemClickListener = onResultListItemClickListener;
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.result_item, parent, false);
        return new ResultViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ResultViewHolder holder, int position) {
        if (mResults != null) {
            HoverResult current = mResults.get(position);
            holder.resultId = current.uuid;
            holder.resultUssdMessagesTextView.setText(current.ussdMessages);
            holder.timestampTextView.setText(String.valueOf(current.requestTimestamp));
        } else {
            holder.resultUssdMessagesTextView.setText("No results captured yet");
        }

        holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (onResultListItemClickListener != null){
                    onResultListItemClickListener.onResultListItemClick(holder.resultId);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mResults != null)
            return mResults.size();
        else return 0;
    }

    void setResults(List<HoverResult> results) {
        mResults = results;
        notifyDataSetChanged();
    }

    public interface OnResultListItemClickListener {
        void onResultListItemClick(String resultId);
    }
}
