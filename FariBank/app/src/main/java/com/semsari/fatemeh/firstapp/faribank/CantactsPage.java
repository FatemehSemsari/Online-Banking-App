package com.semsari.fatemeh.firstapp.faribank;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class CantactsPage extends AppCompatActivity {

    AllUsers allUsers = Main.getAllUsers();
    FloatingActionButton addContactbtn;

    BottomNavigationView bottomNavigationView;
    String phoneNumber = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cantacts_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            phoneNumber = extras.getString("phoneNumber");
        }
        User user = User.findUser(allUsers, phoneNumber);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_contacts);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.bottom_home) {
                    Intent intent = new Intent(CantactsPage.this, HomeActivity.class);
                    intent.putExtra("phoneNumber", user.getPhoneNumber());
                    startActivity(intent);
                    for(User u : user.getContactsList()){
                        System.out.println(u.getName());
                    }
                    finish();
                    return true;
                } else if (id == R.id.bottom_profile) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                    return true;
                } else if (id == R.id.bottom_contacts) {
                    return true;
                } else if (id == R.id.bottom_requests) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                    return true;
                }
                return false;
            }
        });

        RecyclerView recyclerView = findViewById(R.id.contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter adapter = new MyAdapter(this, user.getContactsList(), user);
        recyclerView.setAdapter(adapter);

        addContactbtn = findViewById(R.id.add_contact);
        addContactbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(CantactsPage.this);
                dialog.setContentView(R.layout.activity_add_contacts);
                EditText newContactName = dialog.findViewById(R.id.new_contact_name);
                EditText newContactFamilyName = dialog.findViewById(R.id.new_contact_familyName);
                EditText newContactPhone = dialog.findViewById(R.id.new_contact_phonenumber);
                Button actionBtn = dialog.findViewById(R.id.add_contact_button);

                actionBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name, familyName, phoneNumber;
                        if(newContactName.getText().toString().isEmpty()){
                            newContactName.setError("you have to enter name");
                            return;
                        }
                        if(newContactFamilyName.getText().toString().isEmpty()){
                            newContactFamilyName.setError("you have to enter family name");
                            return;
                        }
                        if(newContactPhone.getText().toString().isEmpty()){
                            newContactPhone.setError("you have to enter phone number");
                            return;
                        }
                        name = newContactName.getText().toString();
                        familyName = newContactFamilyName.getText().toString();
                        phoneNumber = newContactPhone.getText().toString();
                        int addStatus = user.addContact(name, familyName, phoneNumber, allUsers);
                        if(addStatus == 1){
                            List<User> updatedList = user.getContactsList();
                            adapter.updateContactsList(updatedList);
                            adapter.notifyItemInserted(user.getContactsList().size()-1);
                            recyclerView.scrollToPosition(user.getContactsList().size()-1);
                            dialog.dismiss();
                        } else if(addStatus == 0){
                            adapter.notifyDataSetChanged();
                            Toast.makeText(CantactsPage.this, "This contact already exists", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            adapter.notifyDataSetChanged();
                            Toast.makeText(CantactsPage.this, "No such user in the bank", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });
    }

}