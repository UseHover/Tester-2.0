package com.hover.starter.ui.actions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hover.starter.R;
import com.hover.starter.data.transactions.HoverTransaction;

import java.util.List;

public class HoverTransactionListAdapter extends RecyclerView.Adapter<HoverTransactionListAdapter.TransactionViewHolder> {

    class TransactionViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private final TextView ussdMessagesTextView;
        private final TextView timestampTextView;
        private String transactionId;

        private TransactionViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            ussdMessagesTextView = itemView.findViewById(R.id.transaction_text);
            timestampTextView = itemView.findViewById(R.id.timestamp);
        }
    }

    private final LayoutInflater mInflater;
    private List<HoverTransaction> mTransactions;
    private final OnTransactionListItemClickListener onTransactionListItemClickListener;

    public HoverTransactionListAdapter(
            Context context,
            OnTransactionListItemClickListener onTransactionListItemClickListener
    ) {
        mInflater = LayoutInflater.from(context);
        this.onTransactionListItemClickListener = onTransactionListItemClickListener;
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.transaction_item, parent, false);
        return new TransactionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TransactionViewHolder holder, int position) {
        if (mTransactions != null) {
            HoverTransaction current = mTransactions.get(position);
            holder.transactionId = current.uuid;
            holder.ussdMessagesTextView.setText(current.ussdMessages);
            holder.timestampTextView.setText(String.valueOf(current.requestTimestamp));
        } else {
            holder.ussdMessagesTextView.setText("No transactions captured yet");
        }

        holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (onTransactionListItemClickListener != null){
                    onTransactionListItemClickListener.onTransactionListItemClick(holder.transactionId);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mTransactions != null)
            return mTransactions.size();
        else return 0;
    }

    void setTransactions(List<HoverTransaction> transactions) {
        mTransactions = transactions;
        notifyDataSetChanged();
    }

    public interface OnTransactionListItemClickListener {
        void onTransactionListItemClick(String transactionId);
    }
}
