package com.semsari.fatemeh.firstapp.faribank;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoanPageActivity extends AppCompatActivity {

    TextView installmentNo;
    TextView installmentAmount;
    TextView installmentStatus;
    Button pay;
    AllUsers allUsers = Main.getAllUsers();
    User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loan_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle extras = getIntent().getExtras();
        String phoneNumber = null;
        String start = null;

        if(extras != null){
            phoneNumber = extras.getString("phoneNumber");
            start = extras.getString("start");
        }

        user = User.findUser(allUsers, phoneNumber);

        installmentNo = findViewById(R.id.number_of_installment);
        installmentAmount = findViewById(R.id.installmen_amount);
        installmentStatus = findViewById(R.id.installment_status);

        int position = findLoanIndex(start);

        installmentNo.setText(String.valueOf(user.getLoans().get(position).getPayedInstallments().size()+1));
        installmentAmount.setText(String.valueOf(user.getLoans().get(position).getAmount()/user.getLoans().get(position).getMonths()));
        if(!user.getLoans().get(position).checkInstallments()){
            installmentStatus.setText("overdue");
        } else {
            installmentStatus.setText("on time");
        }

        pay = findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getCreditCard().getCredit() < Double.parseDouble(installmentAmount.getText().toString())){
                    installmentAmount.setError("Your balance is insufficient");
                    return;
                }
                Toast.makeText(LoanPageActivity.this, "Done", Toast.LENGTH_SHORT).show();
                user.getCreditCard().setCredit(user.getCreditCard().getCredit() - Double.parseDouble(installmentAmount.getText().toString()));
                user.getLoans().get(position).addPayedInstallments(true);
                Intent intent = new Intent(LoanPageActivity.this, HomeActivity.class);
                intent.putExtra("phoneNumber", user.getPhoneNumber());
                startActivity(intent);
            }
        });


    }

    public int findLoanIndex(String start){
        int index = 0;
        for(Loan loan : user.getLoans()){
            if(String.valueOf(loan.getStartTime()).equalsIgnoreCase(start)){
                return index;
            }
        }
        return user.getLoans().size()-1;
    }
}