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

public class ChargeActivity extends AppCompatActivity {


    AllUsers allUsers = Main.getAllUsers();
    EditText amount;
    Button charge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_charge);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        amount = findViewById(R.id.charge);
        Bundle extras = getIntent().getExtras();
        String phoneNumber = null;

        if(extras != null){
            phoneNumber = extras.getString("phoneNumber");
        }

        User user = User.findUser(allUsers, phoneNumber);
        charge = findViewById(R.id.charge_button);
        charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.getCreditCard().setCredit(user.getCreditCard().getCredit()+Double.parseDouble(amount.getText().toString()));
                user.chargeTheAccount(Double.parseDouble(amount.getText().toString()));
                Toast.makeText(ChargeActivity.this, "Done", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ChargeActivity.this, HomeActivity.class);
                intent.putExtra("phoneNumber", extras.getString("phoneNumber"));
                startActivity(intent);
            }
        });

    }
}