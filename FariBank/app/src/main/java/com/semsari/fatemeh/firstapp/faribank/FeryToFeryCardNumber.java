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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FeryToFeryCardNumber extends AppCompatActivity {

    AllPeople allPeople = Main.getAllPoeple();
    AllUsers allUsers = Main.getAllUsers();
    EditText orgCardNumber;
    EditText desCardNumber;
    EditText password;
    EditText amount;
    Button continueBtn;
    Entry entry = new Entry(allPeople);
    RecentTransfer recentTransfer = new RecentTransfer();

    List<String> cardNumbers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fery_to_fery_card_number);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cardNumbers = allUsers.getUser().stream().map(user -> user.getCreditCard().getCardNumber()).collect(Collectors.toList());
        Bundle extras = getIntent().getExtras();
        String phoneNumber = null;
        if (extras != null) {
            phoneNumber = extras.getString("phoneNumber");
        }
        User user = User.findUser(allUsers, phoneNumber);
        UserMoneyTransfer userMoneyTransfer = new UserMoneyTransfer(allUsers, recentTransfer, user, entry.getOtherBanksUsers());
        orgCardNumber = findViewById(R.id.card_number4);
        desCardNumber = findViewById(R.id.destination_card_number4);
        password = findViewById(R.id.password4);
        amount = findViewById(R.id.amount4);
        continueBtn = findViewById(R.id.transfer4);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!user.getCreditCard().getCardPassword().equals(password.getText().toString())) {
                    password.setError("Inccorect password");
                    return;
                }
                if (!cardNumbers.contains(desCardNumber.getText().toString())) {
                    desCardNumber.setError("No such user");
                    return;
                }
                if (!user.getCreditCard().getCardNumber().equals(orgCardNumber.getText().toString())) {
                    orgCardNumber.setError("Inccorect cardNumber");
                    return;
                }
                if (Double.parseDouble(amount.getText().toString()) <= 0 || amount == null) {
                    amount.setError("Invalid amount");
                    return;
                }
                if (Double.parseDouble(amount.getText().toString()) > user.getCreditCard().getCredit()) {
                    amount.setError("Insufficient balance");
                    return;
                }
                if (userMoneyTransfer.checkAmount(0, 8000000, Double.parseDouble(amount.getText().toString())) == -1) {
                    amount.setError("You con't transfer more than limit");
                    return;
                }

                Person person = userMoneyTransfer.findPerson(desCardNumber.getText().toString());
                if(person == null){
                    person = allUsers.getUser().stream().filter(user -> user.getCreditCard().getCardNumber().equals(desCardNumber.getText().toString())).findFirst().orElse(null);
                }

                Intent intent = new Intent(FeryToFeryCardNumber.this, TransferConfirmationActivity.class);
                intent.putExtra("userPhoneNumber", extras.getString("phoneNumber"));
                intent.putExtra("payee", person.getCreditCard().getCardNumber());
                intent.putExtra("amount", Double.parseDouble(amount.getText().toString()));
                intent.putExtra("isStable", false);
                startActivity(intent);
            }
        });
    }
}