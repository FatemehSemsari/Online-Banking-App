package com.semsari.fatemeh.firstapp.faribank;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.semsari.fatemeh.firstapp.faribank.util.Calendar;

public class ManageFundActivity extends AppCompatActivity {

    AllUsers allUsers = Main.getAllUsers();
    TextView fundBalance;
    Button deposite;
    Button withdraw;
    Double amount = 0.0;

    ListView fundTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_fund);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Bundle extras = getIntent().getExtras();
        String phoneNumber = null;

        if(extras != null){
            phoneNumber = extras.getString("phoneNumber");
        }

        User user = User.findUser(allUsers, phoneNumber);
        int position = extras.getInt("position");

        fundBalance = findViewById(R.id.fund_balance);
        double balance = user.getFunds().get(position).getFundCredit();
        fundBalance.setText(String.valueOf(balance));

        Fund fund = user.getFunds().get(position);
        UsersFunds usersFunds = new UsersFunds(user);

        deposite = findViewById(R.id.deposit);
        deposite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean depositStatus = usersFunds.transferMoneyToFund(fund);
                if(!depositStatus){
                    if (fund.getFundType().equals(FundType.BONUS_FUND)) {
                        if (fund.checkFund(new Calendar().now())) {
                            Toast.makeText(ManageFundActivity.this, "This fund has expired.", Toast.LENGTH_SHORT).show();
                            Toast.makeText(ManageFundActivity.this, "Returning your capital to the main account...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ManageFundActivity.this, ManageAllFunds.class);
                            intent.putExtra("phoneNumber", extras.getString("phoneNumber"));
                            startActivity(intent);
                        }
                    }
                    Toast.makeText(ManageFundActivity.this, "You can't transfer or withdrow an amount from this bonus fund before the specified time!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Dialog dialog = new Dialog(ManageFundActivity.this);
                dialog.setContentView(R.layout.get_amount);
                EditText etamount = dialog.findViewById(R.id.fund_amount);
                Button deposit = dialog.findViewById(R.id.fund_action);
                deposit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        amount = Double.parseDouble(etamount.getText().toString());
                        if (user.getCreditCard().getCredit() < amount) {
                            Toast.makeText(ManageFundActivity.this, "Your account balance is insufficient", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        user.getCreditCard().setCredit(user.getCreditCard().getCredit() - amount);
                        fund.setFundCredit(fund.getFundCredit() + amount);
                        FundTransaction fundTransaction = new FundTransaction();
                        fundTransaction.setAmount(amount);
                        fundTransaction.setType("Deposit");
                        fund.addTransaction(fundTransaction);
                        recreate();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        withdraw = findViewById(R.id.withdraw);
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean withdrawStatus = usersFunds.transferMoneyToFund(fund);
                if(!withdrawStatus){
                    if (fund.getFundType().equals(FundType.BONUS_FUND)) {
                        if (fund.checkFund(new Calendar().now())) {
                            Toast.makeText(ManageFundActivity.this, "This fund has expired.", Toast.LENGTH_SHORT).show();
                            Toast.makeText(ManageFundActivity.this, "Returning your capital to the main account...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ManageFundActivity.this, ManageAllFunds.class);
                            intent.putExtra("phoneNumber", extras.getString("phoneNumber"));
                            startActivity(intent);
                        }
                    }
                    Toast.makeText(ManageFundActivity.this, "You can't transfer or withdrow an amount from this bonus fund before the specified time!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Dialog dialog = new Dialog(ManageFundActivity.this);
                dialog.setContentView(R.layout.get_amount);
                EditText etamount = dialog.findViewById(R.id.fund_amount);
                Button withdraw = dialog.findViewById(R.id.fund_action);
                withdraw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        amount = Double.parseDouble(etamount.getText().toString());
                        if (fund.getFundCredit() < amount) {
                            Toast.makeText(ManageFundActivity.this, "Fund balance is insufficient", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        user.getCreditCard().setCredit(user.getCreditCard().getCredit() + amount);
                        fund.setFundCredit(fund.getFundCredit() - amount);
                        FundTransaction fundTransaction = new FundTransaction();
                        fundTransaction.setAmount(amount);
                        fundTransaction.setType("Withdraw");
                        fund.addTransaction(fundTransaction);
                        recreate();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        fundTransaction = findViewById(R.id.fund_transactions);
        FundTransactionsAdapter adapter = new FundTransactionsAdapter(getApplicationContext(), fund.getTransactions());
        fundTransaction.setAdapter(adapter);
    }
}

