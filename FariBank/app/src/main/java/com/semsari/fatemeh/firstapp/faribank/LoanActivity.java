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

public class LoanActivity extends AppCompatActivity {

    Button loanRequest;
    AllUsers allUsers = Main.getAllUsers();
    User user = null;
    Button allLoansList;
    Button manageLoans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loan);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loanRequest = findViewById(R.id.loan_request);
        Bundle extras = getIntent().getExtras();
        String phoneNumber = null;

        if(extras != null){
            phoneNumber = extras.getString("phoneNumber");
        }

        user = User.findUser(allUsers, phoneNumber);

        loanRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoanActivity.this, ApplyForLoanActivity.class);
                intent.putExtra("phoneNumber", user.getPhoneNumber());
                startActivity(intent);
            }
        });

        allLoansList = findViewById(R.id.loans_list);
        allLoansList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoanActivity.this, AllLoansListActivity.class);
                intent.putExtra("phoneNumber", user.getPhoneNumber());
                startActivity(intent);
            }
        });

        manageLoans = findViewById(R.id.manage_loans);
        manageLoans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoanActivity.this, ManageLoansActivity.class);
                intent.putExtra("phoneNumber", user.getPhoneNumber());
                startActivity(intent);
            }
        });
    }
}