package com.semsari.fatemeh.firstapp.faribank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ManageLoansAdapter extends BaseAdapter {

    List<Loan> acceptedLoans = new ArrayList<>();
    Context context;
    LayoutInflater inflater;

    public ManageLoansAdapter(Context context, List<Loan>acceptedLoans){
        this.context = context;
        this.acceptedLoans = acceptedLoans;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return acceptedLoans.size();
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
        convertView = inflater.inflate(R.layout.accepted_loans_item, null);
        TextView loanType = convertView.findViewById(R.id.loan_type);
        TextView amount = convertView.findViewById(R.id.amount);
        TextView installments = convertView.findViewById(R.id.installments);
        TextView start = convertView.findViewById(R.id.start);

        loanType.setText(acceptedLoans.get(position).getType());
        amount.setText(String.valueOf(acceptedLoans.get(position).getAmount()));
        installments.setText(String.valueOf(acceptedLoans.get(position).getMonths()));
        start.setText(String.valueOf(acceptedLoans.get(position).getStartTime()));

        return convertView;
    }
}
