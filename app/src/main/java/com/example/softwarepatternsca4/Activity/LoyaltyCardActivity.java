package com.example.softwarepatternsca4.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.softwarepatternsca4.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoyaltyCardActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String userID;
    DatabaseReference fireDB;
    int numberOfStamps;

    ImageView stamp1,stamp2,stamp3,stamp4,stamp5,stamp6,stamp7,stamp8;
    TextView cashInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loyalty_card);
         numberOfStamps = 0;

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        assert mUser != null;
        userID = mUser.getUid();


        fireDB = FirebaseDatabase.getInstance().getReference("Transaction").child(userID);

        setStampsUI();

        cashInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    public int getNumberOfStamps(){

        fireDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Transaction transaction = dataSnapshot.getValue(Transaction.class);
                    if(numberOfStamps < 8) {
                        if (!transaction.isStamped()) {
                            numberOfStamps += 1;
                            transaction.setStamped(true);
                            fireDB.setValue(transaction).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
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

        return numberOfStamps;
    }

    public void setStampsUI(){
        stamp1 = findViewById(R.id.stamp1);
        stamp2 = findViewById(R.id.stamp2);
        stamp3 = findViewById(R.id.stamp3);
        stamp4 = findViewById(R.id.stamp4);
        stamp5 = findViewById(R.id.stamp5);
        stamp6 = findViewById(R.id.stamp6);
        stamp7 = findViewById(R.id.stamp7);
        stamp8 = findViewById(R.id.stamp8);
        cashInBtn = findViewById(R.id.cashInBtn);

        switch (getNumberOfStamps()){
            case 0:

                break;
            case 1:
                stamp1.setImageResource(R.drawable.checkmark2);
                break;
            case 2:
                stamp1.setImageResource(R.drawable.checkmark2);
                stamp2.setImageResource(R.drawable.checkmark2);
                break;
            case 3:
                stamp1.setImageResource(R.drawable.checkmark2);
                stamp2.setImageResource(R.drawable.checkmark2);
                stamp3.setImageResource(R.drawable.checkmark2);
                break;
            case 4:
                stamp1.setImageResource(R.drawable.checkmark2);
                stamp2.setImageResource(R.drawable.checkmark2);
                stamp3.setImageResource(R.drawable.checkmark2);
                stamp4.setImageResource(R.drawable.checkmark2);
                break;
            case 5:
                stamp1.setImageResource(R.drawable.checkmark2);
                stamp2.setImageResource(R.drawable.checkmark2);
                stamp3.setImageResource(R.drawable.checkmark2);
                stamp4.setImageResource(R.drawable.checkmark2);
                stamp5.setImageResource(R.drawable.checkmark2);
                break;
            case 6:
                stamp1.setImageResource(R.drawable.checkmark2);
                stamp2.setImageResource(R.drawable.checkmark2);
                stamp3.setImageResource(R.drawable.checkmark2);
                stamp4.setImageResource(R.drawable.checkmark2);
                stamp5.setImageResource(R.drawable.checkmark2);
                stamp6.setImageResource(R.drawable.checkmark2);
                break;
            case 7:
                stamp1.setImageResource(R.drawable.checkmark2);
                stamp2.setImageResource(R.drawable.checkmark2);
                stamp3.setImageResource(R.drawable.checkmark2);
                stamp4.setImageResource(R.drawable.checkmark2);
                stamp5.setImageResource(R.drawable.checkmark2);
                stamp6.setImageResource(R.drawable.checkmark2);
                stamp7.setImageResource(R.drawable.checkmark2);
                break;
            case 8:
                stamp1.setImageResource(R.drawable.checkmark2);
                stamp2.setImageResource(R.drawable.checkmark2);
                stamp3.setImageResource(R.drawable.checkmark2);
                stamp4.setImageResource(R.drawable.checkmark2);
                stamp5.setImageResource(R.drawable.checkmark2);
                stamp6.setImageResource(R.drawable.checkmark2);
                stamp7.setImageResource(R.drawable.checkmark2);
                stamp8.setImageResource(R.drawable.checkmark2);
                cashInBtn.setVisibility(View.VISIBLE);







        }


    }


}