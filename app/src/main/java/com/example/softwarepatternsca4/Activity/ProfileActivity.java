package com.example.softwarepatternsca4.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.softwarepatternsca4.R;

public class ProfileActivity extends AppCompatActivity {

    TextView purchaseHistoryBtn, profileDetailsBtn, LoyaltyCardBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        purchaseHistoryBtn = findViewById(R.id.purchaseHistoryBtn);
        profileDetailsBtn = findViewById(R.id.profileDetailsBtn);
        LoyaltyCardBtn = findViewById(R.id.loyaltyCardBtn);

        purchaseHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(ProfileActivity.this,PurchaseHistoryActivity.class);
                    startActivity(intent);
            }
        });

        profileDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,ProfileDetailsActivity.class);
                startActivity(intent);
            }
        });

        LoyaltyCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,LoyaltyCardActivity.class);
                startActivity(intent);
            }
        });
    }
}