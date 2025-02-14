package com.semsari.fatemeh.firstapp.faribank;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AllLoansListActivity extends AppCompatActivity {

    AllUsers allUsers = Main.getAllUsers();
    User user = null;
    ListView allLoans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_loans_list);
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
        allLoans = (ListView) findViewById(R.id.all_loans_list);
        AllLoansAdapter adapter = new AllLoansAdapter(getApplicationContext(), user.getLoans());
        allLoans.setAdapter(adapter);
    }
}