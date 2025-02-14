package com.semsari.fatemeh.firstapp.faribank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TransferConfirmationActivity extends AppCompatActivity {

    AllPeople allPeople = Main.getAllPoeple();
    AllUsers allUsers = Main.getAllUsers();
    Entry entry = new Entry(allPeople);
    RecentTransfer recentTransfer = new RecentTransfer();
    TextView desCardNo;
    TextView orgCardNo;
    TextView payee;
    TextView transferedAmount;
    Double amount = 0.0;
    Button transfer;
    Button cancel;
    boolean isStable;
    Person person = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transfer_confirmation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        desCardNo = findViewById(R.id.desCNo);
        orgCardNo = findViewById(R.id.OrgCNo);
        payee = findViewById(R.id.payeeName);
        transferedAmount = findViewById(R.id.amnt);
        transfer = findViewById(R.id.main_transfer);
        cancel = findViewById(R.id.cancel);

        Bundle extras = getIntent().getExtras();
        String userPhoneNumber = null;
        String payeeCardNumber = null;
        if (extras != null) {
            userPhoneNumber = extras.getString("userPhoneNumber");
            payeeCardNumber = extras.getString("payee");
            isStable = extras.getBoolean("isStable");
            amount = extras.getDouble("amount");
        }

        desCardNo.setText(payeeCardNumber);
        User user = User.findUser(allUsers, userPhoneNumber);
        orgCardNo.setText(user.getCreditCard().getCardNumber());
        UserMoneyTransfer userMoneyTransfer = new UserMoneyTransfer(allUsers, recentTransfer, user, entry.getOtherBanksUsers());
        person = userMoneyTransfer.findPerson(payeeCardNumber);
        if(person == null){
            person = user.getContactsList().stream().filter(c -> c.getCreditCard().getCardNumber().equals(extras.getString("payee"))).findFirst().orElse(null);
        }
        if(person == null){
            person = allUsers.getUser().stream().filter(u -> u.getCreditCard().getCardNumber().equals(extras.getString("payee"))).findFirst().orElse(null);
        }
        payee.setText(person.getName() + " " + person.getFamilyName());
        transferedAmount.setText(amount.toString());

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransferConfirmationActivity.this, HomeActivity.class);
                intent.putExtra("phoneNumber", extras.getString("userPhoneNumber"));
                startActivity(intent);
            }
        });

        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStable){
                    Thread thread = userMoneyTransfer.handleStableTransfer(amount, person);
                    thread.start();
                    Intent intent = new Intent(TransferConfirmationActivity.this, HomeActivity.class);
                    intent.putExtra("phoneNumber", extras.getString("userPhoneNumber"));
                    startActivity(intent);
                    return;
                }
                userMoneyTransfer.transfer(amount, person, false);
                Intent intent = new Intent(TransferConfirmationActivity.this, HomeActivity.class);
                intent.putExtra("phoneNumber", extras.getString("userPhoneNumber"));
                startActivity(intent);
            }
        });
    }
}