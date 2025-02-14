package com.semsari.fatemeh.firstapp.faribank;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    TextView balance;
    TextView userName;
    TextView accountNumber;
    Button copyBtn;
    ImageButton moneyTransfer;
    ImageButton capitalFunds;
    ImageButton loan;
    FloatingActionButton charge;
    AllUsers allUsers = Main.getAllUsers();
    BottomNavigationView bottomNavigationView;

    ListView transactions;

    User user = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
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

        user = User.findUser(allUsers, phoneNumber);
        balance = findViewById(R.id.account_balance);
        balance.setText(String.valueOf(user.getCreditCard().getCredit()));
        userName = findViewById(R.id.user_name);
        userName.setText(String.valueOf(user.getName()+" "+user.getFamilyName())+" "+user.getCreditCard().getCardPassword());
        accountNumber = findViewById(R.id.account_number);
        accountNumber.setText(String.valueOf(user.getCreditCard().getCardNumber()));
        copyBtn = findViewById(R.id.button);
        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Text", accountNumber.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(HomeActivity.this, "copied", Toast.LENGTH_LONG).show();
            }
        });
        charge = findViewById(R.id.charge_account);
        charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ChargeActivity.class);
                intent.putExtra("phoneNumber", extras.getString("phoneNumber"));
                startActivity(intent);
            }
        });
        moneyTransfer = findViewById(R.id.transfer);
        moneyTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MoneyTransferActivity.class);
                intent.putExtra("phoneNumber", extras.getString("phoneNumber"));
                startActivity(intent);
            }
        });
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.bottom_home) {
                    return true;
                } else if (id == R.id.bottom_profile) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                    return true;
                } else if (id == R.id.bottom_contacts) {
                    Intent intent = new Intent(HomeActivity.this, CantactsPage.class);
                    intent.putExtra("phoneNumber", extras.getString("phoneNumber"));
                    startActivity(intent);
                    for(Map.Entry<String[], User> u : user.getContacts().entrySet()){
                        System.out.println(u.getKey()[0].toString() + u.getKey()[1].toString());
                    }
                    finish();
                    return true;
                } else if (id == R.id.bottom_requests) {
                    Intent intent = new Intent(HomeActivity.this, RequestsActivity.class);
                    intent.putExtra("phoneNumber", extras.getString("phoneNumber"));
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });

        capitalFunds = findViewById(R.id.capital_funds);
        capitalFunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainCapitalFunds.class);
                intent.putExtra("phoneNumber", extras.getString("phoneNumber"));
                startActivity(intent);
            }
        });

        transactions = findViewById(R.id.transactions);
        TransactionsAdapter adapter = new TransactionsAdapter(getApplicationContext(), user.getTransactions());
        transactions.setAdapter(adapter);

        loan = findViewById(R.id.loan);
        loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LoanActivity.class);
                intent.putExtra("phoneNumber", extras.getString("phoneNumber"));
                startActivity(intent);
            }
        });
    }

}