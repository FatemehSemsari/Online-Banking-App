package com.semsari.fatemeh.firstapp.faribank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FeryToFeryOptions extends AppCompatActivity {

    AllUsers allUsers = Main.getAllUsers();

    Button enteringCardNumber;

    Button transferToContacts;
    Button recentTransfers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fery_to_fery_options);
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

        enteringCardNumber = findViewById(R.id.cardnumber_entering);
        enteringCardNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeryToFeryOptions.this, FeryToFeryCardNumber.class);
                intent.putExtra("phoneNumber", extras.getString("phoneNumber"));
                startActivity(intent);
            }
        });
        transferToContacts = findViewById(R.id.transfer_to_contact);
        transferToContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeryToFeryOptions.this, ContactsToTransfer.class);
                intent.putExtra("phoneNumber", extras.getString("phoneNumber"));
                startActivity(intent);
            }
        });
        recentTransfers = findViewById(R.id.recent_transfers);
        recentTransfers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeryToFeryOptions.this, RecentTransfersActivity.class);
                intent.putExtra("phoneNumber", extras.getString("phoneNumber"));
                startActivity(intent);
            }
        });
    }
}