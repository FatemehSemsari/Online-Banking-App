package com.semsari.fatemeh.firstapp.faribank;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class RequestsActivity extends AppCompatActivity {

    private AllUsers allUsers = Main.getAllUsers();
    private Button send;
    private EditText requestMessage;
    private RecyclerView requestsList;
    private BottomNavigationView bottomNavigationView;

    String defaultMessage = "Our colleagues will contact you soon \nto solve the problem.\nThank you for your patience.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_requests);
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


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_requests);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.bottom_home) {
                    Intent intent = new Intent(RequestsActivity.this, HomeActivity.class);
                    intent.putExtra("phoneNumber", extras.getString("phoneNumber"));
                    startActivity(intent);
                    finish();
                    return true;
                } else if (id == R.id.bottom_profile) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                    return true;
                } else if (id == R.id.bottom_contacts) {
                    Intent intent = new Intent(RequestsActivity.this, CantactsPage.class);
                    intent.putExtra("phoneNumber", extras.getString("phoneNumber"));
                    startActivity(intent);
                    finish();
                    return true;
                } else if (id == R.id.bottom_requests) {
                    return true;
                }
                return false;
            }
        });


        requestsList = findViewById(R.id.requests);
        RequestsAdapter adapter = new RequestsAdapter(user.getRequestMessages());
        requestsList.setLayoutManager(new LinearLayoutManager(this));
        requestsList.setAdapter(adapter);

        requestMessage = findViewById(R.id.message_enterance);
        send = findViewById(R.id.sender);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = requestMessage.getText().toString();
                if(!message.isEmpty()){
                    UserRequests userRequests = new UserRequests(message, true);
                    user.setRequestMessage(userRequests);
                    adapter.notifyItemInserted(user.getRequestMessages().size() - 1);
//                    requestsList.scrollToPosition(user.getRequestMessages().size()-1);
                    requestMessage.setText("");

                    Thread thread = handleDefualtMessage(user, adapter);
                    thread.start();
                }
            }
        });
    }



    public Thread handleDefualtMessage (User user, RequestsAdapter adapter){
        return new Thread(() -> {
            try {
                Thread.sleep(30000);
                UserRequests userRequests = new UserRequests(defaultMessage, false);
                user.setRequestMessage(userRequests);
                new Handler(Looper.getMainLooper()).post(() -> {
                adapter.notifyItemInserted(user.getRequestMessages().size() - 1);
                requestsList.scrollToPosition(user.getRequestMessages().size()-1);
            });
            } catch (InterruptedException e) {
                System.out.println("\u001B[31mThread intrrupted!\u001B[0m");
                e.printStackTrace();
            }
        });
    }
}