package com.semsari.fatemeh.firstapp.faribank;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;


public class LogInActivity extends AppCompatActivity {

    AllUsers allUsers = Main.getAllUsers();
    AuthenticationQueue queue = Main.getQueue();
    AllPeople allPeople = Main.getAllPoeple();

    Entry entry = new Entry(allPeople);

    TextInputLayout tiPhoneNumber, tiPassWord;
    EditText etPhoneNumber, etPassWord;
    MaterialButton btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tiPhoneNumber = findViewById(R.id.phonenumber);
        tiPassWord = findViewById(R.id.password);
        etPhoneNumber = findViewById(R.id.textInputEditText_phone);
        etPassWord = findViewById(R.id.textInputEditText_password);
        btLogin = findViewById(R.id.log_in);

        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()){
                    tiPhoneNumber.setError("You have to enter phone number");
                } else {
                    tiPhoneNumber.setError(null);
                }
            }
        });

        etPassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()){
                    tiPassWord.setError("You have to enter password");
                } else {
                    tiPassWord.setError(null);
                }
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(entry.isBankUser(etPhoneNumber.getText().toString(), allUsers)){
                    if(entry.passwordIsCorrect(etPassWord.getText().toString(), etPhoneNumber.getText().toString(), allUsers)){
                        Toast.makeText(LogInActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                        intent.putExtra("phoneNumber", etPhoneNumber.getText().toString());
                        startActivity(intent);
                    } else {
                        Toast.makeText(LogInActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                        etPassWord.setError("Password is incorrect");
                    }
                } else {
                    Toast.makeText(LogInActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                    etPhoneNumber.setError("No such user in the bank");
                }
            }
        });

        TextView signUp = findViewById(R.id.signUpText);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
            }
        });
    }


}