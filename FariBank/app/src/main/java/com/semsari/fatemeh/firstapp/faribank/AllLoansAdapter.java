package com.semsari.fatemeh.firstapp.faribank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AllLoansAdapter extends BaseAdapter {

    Context context;
    List<Loan> loans = new ArrayList<>();
    LayoutInflater inflater;

    public AllLoansAdapter(Context context, List<Loan> loans){
        this.loans = loans;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return loans.size();
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
        convertView = inflater.inflate(R.layout.all_loans_item, null);
        TextView loanType = (TextView) convertView.findViewById(R.id.loan_type);
        TextView loanStatus = (TextView) convertView.findViewById(R.id.status);
        loanType.setText(loans.get(position).getType());
        loanStatus.setText(loans.get(position).getStatus());
        return convertView;
    }
}
