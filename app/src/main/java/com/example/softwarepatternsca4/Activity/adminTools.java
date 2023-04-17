package com.example.softwarepatternsca4.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.softwarepatternsca4.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class adminTools extends AppCompatActivity {
    TextView searchStockBtn, viewCustomersBtn, simulatePurchaseBtn;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String userID;
    DatabaseReference fireDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tools);

        searchStockBtn = findViewById(R.id.searchStockBtn);
        viewCustomersBtn = findViewById(R.id.viewCustomersBtn);
        simulatePurchaseBtn = findViewById(R.id.simulatePurchaseBtn);

        searchStockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminTools.this,searchStock.class);
                startActivity(intent);
            }
        });
        viewCustomersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminTools.this,searchUsers.class);
                startActivity(intent);
            }
        });
        simulatePurchaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminTools.this,simPurchase.class);
                startActivity(intent);
            }
        });
    }
}