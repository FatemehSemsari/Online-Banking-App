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

import java.util.stream.Collectors;

public class ManageLoansActivity extends AppCompatActivity {

    AllUsers allUsers = Main.getAllUsers();
    User user = null;
    ListView acceptedLoans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_loans);
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

        acceptedLoans = (ListView) findViewById(R.id.accepted_loans_list);
        ManageLoansAdapter adapter = new ManageLoansAdapter(getApplicationContext(), user.getLoans().stream().filter(l -> l.getStatus().equalsIgnoreCase("accepted")).collect(Collectors.toList()));
        acceptedLoans.setAdapter(adapter);

        acceptedLoans.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ManageLoansActivity.this, LoanPageActivity.class);
                intent.putExtra("phoneNumber", user.getPhoneNumber());
                intent.putExtra("start", user.getLoans().stream().filter(l -> l.getStatus().equalsIgnoreCase("accepted")).collect(Collectors.toList()).get(position).getStartTime().toString());
                startActivity(intent);
            }
        });
    }
}