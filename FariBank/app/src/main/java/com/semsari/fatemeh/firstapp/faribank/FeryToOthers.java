package com.semsari.fatemeh.firstapp.faribank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FeryToOthers extends AppCompatActivity {


    AllPeople allPeople = Main.getAllPoeple();
    AllUsers allUsers = Main.getAllUsers();
    EditText orgCardNumber;
    EditText desCardNumber;
    EditText password;
    EditText amount;
    Button continueBtn;
    Entry entry = new Entry(allPeople);
    RecentTransfer recentTransfer = new RecentTransfer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fery_to_others);
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
        UserMoneyTransfer userMoneyTransfer = new UserMoneyTransfer(allUsers, recentTransfer, user,  entry.getOtherBanksUsers());
        orgCardNumber = findViewById(R.id.card_number);
        desCardNumber = findViewById(R.id.destination_card_number);
        password = findViewById(R.id.password);
        amount = findViewById(R.id.amount);
        continueBtn = findViewById(R.id.transfer1);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!user.getCreditCard().getCardPassword().equals(password.getText().toString())){
                    password.setError("Inccorect password");
                    return;
                }
                if(userMoneyTransfer.findPerson(desCardNumber.getText().toString()) == null){
                    desCardNumber.setError("No such person");
                    return;
                }
                if(!user.getCreditCard().getCardNumber().equals(orgCardNumber.getText().toString())){
                    orgCardNumber.setError("Inccorect cardNumber");
                    return;
                }
                if(Double.parseDouble(amount.getText().toString())<=0 || amount == null){
                    amount.setError("Invalid amount");
                    return;
                }
                if(Double.parseDouble(amount.getText().toString()) > user.getCreditCard().getCredit()){
                    amount.setError("Insufficient balance");
                    return;
                }
                if(userMoneyTransfer.checkAmount(300, 100000, Double.parseDouble(amount.getText().toString()))==-1){
                    amount.setError("You con't transfer more than limit");
                    return;
                }
                Person person = userMoneyTransfer.findPerson(desCardNumber.getText().toString());
                Intent intent = new Intent(FeryToOthers.this, TransferConfirmationActivity.class);
                intent.putExtra("userPhoneNumber", extras.getString("phoneNumber"));
                intent.putExtra("payee", person.getCreditCard().getCardNumber());
                intent.putExtra("amount", Double.parseDouble(amount.getText().toString())+300);
                intent.putExtra("isStable", false);
                startActivity(intent);
            }
        });
    }
}