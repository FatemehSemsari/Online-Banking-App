package com.semsari.fatemeh.firstapp.faribank;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddCapitalFundsActivity extends AppCompatActivity {
    AllUsers allUsers = Main.getAllUsers();
    Button savingFund;
    Button remainingFund;
    Button bonusFund;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_capital_funds);
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
        UsersFunds usersFunds = new UsersFunds(user);

        savingFund = findViewById(R.id.saving_fund);
        remainingFund = findViewById(R.id.remaining_fund);
        bonusFund = findViewById(R.id.bonus_fund);

        savingFund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fund savingFund = new SavingsFund(FundType.SAVINGS_FUND);
                if(usersFunds.checkAndAddSavingsFund(savingFund)){
                    Toast.makeText(AddCapitalFundsActivity.this, "Added", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddCapitalFundsActivity.this, "Fail", Toast.LENGTH_LONG).show();
                }
            }
        });

        remainingFund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fund remainingFund = new RemainingFunds(FundType.REMAINING_FUND);
                if(usersFunds.checkAndAddRemainingFund(remainingFund)){
                    Toast.makeText(AddCapitalFundsActivity.this, "Added", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddCapitalFundsActivity.this, "Fail", Toast.LENGTH_LONG).show();
                }
            }
        });

        back = findViewById(R.id.back2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCapitalFundsActivity.this, MainCapitalFunds.class);
                intent.putExtra("phoneNumber", extras.getString("phoneNumber"));
                startActivity(intent);
            }
        });

        bonusFund = findViewById(R.id.bonus_fund);
        bonusFund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(AddCapitalFundsActivity.this);
                dialog.setContentView(R.layout.activity_add_bonus_fund);
                EditText etamount = dialog.findViewById(R.id.amount);
                EditText etdays = dialog.findViewById(R.id.days);
                EditText ethours = dialog.findViewById(R.id.hours);
                Button addBonus = dialog.findViewById(R.id.add_bonus);

                addBonus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int days = 0, hours = 0;
                        double amount = 0;
                        days = Integer.parseInt(etdays.getText().toString());
                        amount = Double.parseDouble(etamount.getText().toString());
                        hours = Integer.parseInt(ethours.getText().toString());
                        Fund bonusFund = new BonusFund(FundType.BONUS_FUND);
                        bonusFund.setTime(days, hours);
                        if (user.getCreditCard().getCredit() < amount) {
                            Toast.makeText(AddCapitalFundsActivity.this, "Insufficient balance", Toast.LENGTH_LONG).show();
                            return;
                        }
                        bonusFund.setFundCredit(amount);
                        user.getCreditCard().setCredit(user.getCreditCard().getCredit() - amount);
                        if(!usersFunds.checkAndAddFundBonusFund(bonusFund)){
                            Toast.makeText(AddCapitalFundsActivity.this, "You already have this type of fund", Toast.LENGTH_LONG).show();
                            return;
                        }
                        Toast.makeText(AddCapitalFundsActivity.this, "Added", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
}