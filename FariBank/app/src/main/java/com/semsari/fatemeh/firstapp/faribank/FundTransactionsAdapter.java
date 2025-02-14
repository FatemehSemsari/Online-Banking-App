package com.semsari.fatemeh.firstapp.faribank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FundTransactionsAdapter extends BaseAdapter {

    Context context;
    List<FundTransaction> transactionList = new ArrayList<>();
    LayoutInflater layoutInflater;

    public FundTransactionsAdapter(Context context, List<FundTransaction> transactionList) {
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
        convertView = layoutInflater.inflate(R.layout.fund_transactions_item, null);
        TextView typedeposit = (TextView) convertView.findViewById(R.id.type_deposit);
        TextView typeTransfer = (TextView) convertView.findViewById(R.id.type_withdraw);
        TextView transferAmount = (TextView) convertView.findViewById(R.id.fund_transaction_amount);
        if(transactionList.get(position).getType().equalsIgnoreCase("deposit")){
            typedeposit.setText("deposit");
            typeTransfer.setText("");
        } else {
            typeTransfer.setText("withdraw");
            typedeposit.setText("");
        }
        transferAmount.setText(String.valueOf(transactionList.get(position).getAmount()));
        return convertView;
    }
}
