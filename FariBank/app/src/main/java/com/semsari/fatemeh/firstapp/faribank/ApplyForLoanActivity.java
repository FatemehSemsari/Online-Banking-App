package com.semsari.fatemeh.firstapp.faribank;

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

import com.semsari.fatemeh.firstapp.faribank.util.Calendar;
import com.semsari.fatemeh.firstapp.faribank.R;

import java.util.List;

public class ApplyForLoanActivity extends AppCompatActivity {

    EditText loanAmount;
    EditText loanType;
    EditText repayment;
    AllUsers allUsers = Main.getAllUsers();
    Button submit;

    User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_apply_for_loan);
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
        loanAmount = findViewById(R.id.loan_amount);
        loanType = findViewById(R.id.loan_type);
        repayment = findViewById(R.id.loan_repayment);
        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loan loan = new Loan(Double.parseDouble(loanAmount.getText().toString()), Integer.parseInt(repayment.getText().toString()), "checking", loanType.getText().toString(), Calendar.now(), user);
                user.addLoan(loan);
                Toast.makeText(ApplyForLoanActivity.this, "loan added", Toast.LENGTH_SHORT).show();
                Thread thread = handleChekingUserLoans(user.getLoans() , loan);
                thread.start();
            }
        });

        Button back = findViewById(R.id.back5);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ApplyForLoanActivity.this, LoanActivity.class);
                intent.putExtra("phoneNumber", user.getPhoneNumber());
                startActivity(intent);
            }
        });
    }

    public Thread handleChekingUserLoans(List<Loan> loans, Loan requested){
        return new Thread(() ->{
           try {
               Thread.sleep(40000);
               if(!loans.isEmpty()) {
                   for (Loan loan : loans) {
                       if (!loan.checkInstallments()) {
                           requested.setStatus("rejected");
                           break;
                       } else {
                           requested.setStatus("accepted");
                       }
                   }
               } else {
                   requested.setStatus("accepted");
               }
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           }
        });
    }
}