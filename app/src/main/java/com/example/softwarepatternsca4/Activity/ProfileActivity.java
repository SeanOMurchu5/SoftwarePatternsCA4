package com.example.softwarepatternsca4.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.softwarepatternsca4.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    TextView purchaseHistoryBtn, profileDetailsBtn, LoyaltyCardBtn, AdminToolsBtn;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String userID;
    DatabaseReference fireDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        assert mUser != null;
        userID = mUser.getUid();
        fireDB = FirebaseDatabase.getInstance().getReference("users");

        purchaseHistoryBtn = findViewById(R.id.purchaseHistoryBtn);
        profileDetailsBtn = findViewById(R.id.profileDetailsBtn);
        LoyaltyCardBtn = findViewById(R.id.loyaltyCardBtn);
        AdminToolsBtn = findViewById(R.id.adminBtn);


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

        AdminToolsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,adminTools.class);
                startActivity(intent);
            }
        });
    }

    public void getUserType(){
        fireDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    User user = dataSnapshot.getValue(User.class);
                    if(user.getUserId().equalsIgnoreCase(userID)){
                        if(user.getAdmin()){
                            AdminToolsBtn.setVisibility(View.VISIBLE);
                        }
                    }

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}