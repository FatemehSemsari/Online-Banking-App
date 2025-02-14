package com.semsari.fatemeh.firstapp.faribank;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUpActivity extends AppCompatActivity {
    AllUsers allUsers = Main.getAllUsers();
    AllPeople allPeople = Main.getAllPoeple();
    Register register = new Register();
    TextView loninText;
    EditText name, familyName, idNumber, phoneNumber, password;

    Button btSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        name = findViewById(R.id.name);
        familyName = findViewById(R.id.familyName);
        idNumber = findViewById(R.id.id);
        phoneNumber = findViewById(R.id.phonenumber);
        password = findViewById(R.id.Password);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()){
                    name.setError("You have to name");
                } else {
                    name.setError(null);
                }
            }
        });

        familyName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()){
                    familyName.setError("You have to enter family name");
                } else {
                    familyName.setError(null);
                }
            }
        });

        idNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()){
                    idNumber.setError("You have to enter id");
                } else {
                    idNumber.setError(null);
                }
            }
        });

        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()){
                    phoneNumber.setError("You have to enter phone number");
                } else {
                    phoneNumber.setError(null);
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!register.passwordIsSafe(s.toString())){
                    password.setError("Week Password");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()){
                    password.setError("You have to enter password");
                } else {
                    password.setError(null);
                }
            }
        });

        btSignUp = findViewById(R.id.rgister_button);

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(register.phoneNumberIsAvailable(phoneNumber.getText().toString(), allUsers)){
                    phoneNumber.setError("This phone number is already exists");
                    Toast.makeText(SignUpActivity.this, "Sign Up Failed", Toast.LENGTH_LONG).show();
                    return;
                }
                if(register.idIsAvailable(idNumber.getText().toString(), allUsers)){
                    idNumber.setError("This ID is already exists");
                    Toast.makeText(SignUpActivity.this, "Sign Up Failed", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!register.passwordIsSafe(password.getText().toString())){
                    password.setError("Password is week");
                    Toast.makeText(SignUpActivity.this, "Sign Up Failed", Toast.LENGTH_LONG).show();
                    return;
                }
                User user = new User(name.getText().toString(), familyName.getText().toString(), phoneNumber.getText().toString(), idNumber.getText().toString(), password.getText().toString());
                register.register(user, allUsers);
                Toast.makeText(SignUpActivity.this, "Signed Up Successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
            }
        });

        loninText = findViewById(R.id.have_signed_up);

        loninText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
            }
        });



    }
}