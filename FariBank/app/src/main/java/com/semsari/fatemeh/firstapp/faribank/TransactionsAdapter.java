package com.semsari.fatemeh.firstapp.faribank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TransactionsAdapter extends BaseAdapter {

    Context context;
    List<Transaction> transactionList = new ArrayList<>();
    LayoutInflater layoutInflater;

    public TransactionsAdapter(Context context, List<Transaction> transactionList) {
        this.context = context;
        this.transactionList = transactionList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return transactionList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.transactions_item, null);
        TextView typeCharge = (TextView) convertView.findViewById(R.id.type_charge);
        TextView typeTransfer = (TextView) convertView.findViewById(R.id.type_transfer);
        TextView issueTracking = (TextView) convertView.findViewById(R.id.issu_tracking);
        TextView transferAmount = (TextView) convertView.findViewById(R.id.transaction_amount);
        if(transactionList.get(position) instanceof Charge){
            typeCharge.setText("Charge");
            typeTransfer.setText("");
        } else {
            typeTransfer.setText("Transfer");
            typeCharge.setText("");
        }
        issueTracking.setText(transactionList.get(position).getIssueTracking());
        transferAmount.setText(String.valueOf(transactionList.get(position).getAmount()));
        return convertView;
    }
}
