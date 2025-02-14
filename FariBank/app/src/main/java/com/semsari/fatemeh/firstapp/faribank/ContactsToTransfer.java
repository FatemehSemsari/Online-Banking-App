package com.semsari.fatemeh.firstapp.faribank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ContactsToTransfer extends AppCompatActivity {

    AllUsers allUsers = Main.getAllUsers();

    ListView contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contacts_to_transfer);
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

        contacts = findViewById(R.id.user_contacts);

        ContactsToTransferAdapter adapter = new ContactsToTransferAdapter(this, R.layout.contacts_item_to_transfer, user.getContactsList());
        contacts.setAdapter(adapter);

        contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ContactsToTransfer.this, FeryToFeryContactsTransfer.class);
                intent.putExtra("phoneNumber", extras.getString("phoneNumber"));
                intent.putExtra("desCardNumber", user.getContactsList().get(position).getCreditCard().getCardNumber());
                startActivity(intent);
            }
        });


    }
}