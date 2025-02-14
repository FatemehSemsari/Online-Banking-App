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

public class MoneyTransferActivity extends AppCompatActivity {

    AllUsers allUsers = Main.getAllUsers();
    Button feryToOthers;
    Button paypol;
    Button stableTransfer;
    Button feryToFery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_money_transfer);
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
        feryToOthers = findViewById(R.id.card_by_card);
        feryToOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoneyTransferActivity.this, FeryToOthers.class);
                intent.putExtra("phoneNumber", extras.getString("phoneNumber"));
                startActivity(intent);
            }
        });
        paypol = findViewById(R.id.paypol);
        paypol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoneyTransferActivity.this, PaypolTransfer.class);
                intent.putExtra("phoneNumber", extras.getString("phoneNumber"));
                startActivity(intent);
            }
        });
        stableTransfer = findViewById(R.id.stable_transfer);
        stableTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoneyTransferActivity.this, StableTransfer.class);
                intent.putExtra("phoneNumber", extras.getString("phoneNumber"));
                startActivity(intent);
            }
        });
        feryToFery = findViewById(R.id.fery_to_fery);
        feryToFery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoneyTransferActivity.this, FeryToFeryOptions.class);
                intent.putExtra("phoneNumber", extras.getString("phoneNumber"));
                startActivity(intent);
            }
        });
    }
}