package com.example.softwarepatternsca4.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.softwarepatternsca4.Adapter.CartAdapter;
import com.example.softwarepatternsca4.Domain.Item;
import com.example.softwarepatternsca4.Interface.ChangeNumberItemsListener;
import com.example.softwarepatternsca4.Interface.OnDataReceiveCallback;
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

import java.util.ArrayList;

public class LoyaltyCardActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String userID;
    DatabaseReference fireDB;
    int numberOfStamps;
    ArrayList<Transaction> transactions;
    ImageView stamp1,stamp2,stamp3,stamp4,stamp5,stamp6,stamp7,stamp8;
    TextView cashInBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loyalty_card);
         numberOfStamps = 0;
        stamp1 = findViewById(R.id.stamp1);
        stamp2 = findViewById(R.id.stamp2);
        stamp3 = findViewById(R.id.stamp3);
        stamp4 = findViewById(R.id.stamp4);
        stamp5 = findViewById(R.id.stamp5);
        stamp6 = findViewById(R.id.stamp6);
        stamp7 = findViewById(R.id.stamp7);
        stamp8 = findViewById(R.id.stamp8);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        assert mUser != null;
        userID = mUser.getUid();
        cashInBtn = findViewById(R.id.cashInBtn);
        transactions= new ArrayList<>();


        fireDB = FirebaseDatabase.getInstance().getReference("Transaction").child(userID);

        getNumberOfStamps();

        cashInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 fireDB.addChildEventListener(new ChildEventListener() {
                     @Override
                     public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                          Transaction transaction = snapshot.getValue(Transaction.class);
                          for(int i = 0; i < transactions.size();i++){
                              if(transaction.getUniqueId().equalsIgnoreCase(transactions.get(i).getUniqueId())){
                                  transaction.setStamped(true);
                                  fireDB.child(transaction.getUniqueId()).setValue(transaction);
                                  numberOfStamps-=1;
                                  setStampsUI();
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
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getNumberOfStamps();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getNumberOfStamps();

    }

    public void getNumberOfStamps(){



        getFromFirebase(new OnDataReceiveCallback(){
            @Override
            public void onDataReceived(ArrayList list) {

            }

            @Override
            public void onDataReceived(int n) {
                Log.d("CREATION", "received data ");
                setStampsUI();

            }

        });

    }

    public void setStampsUI(){
        Log.v(TAG,"before stamps");

        switch (numberOfStamps){
            case 0:
                Log.v(TAG,"in stamps 0");

                break;
            case 1:
                Log.v(TAG,"in stamps 1");

                stamp1.setImageResource(R.drawable.checkmark2);
                break;
            case 2:
                Log.v(TAG,"in stamps 2");

                stamp1.setImageResource(R.drawable.checkmark2);
                stamp2.setImageResource(R.drawable.checkmark2);
                break;
            case 3:
                Log.v(TAG,"in stamps 3");

                stamp1.setImageResource(R.drawable.checkmark2);
                stamp2.setImageResource(R.drawable.checkmark2);
                stamp3.setImageResource(R.drawable.checkmark2);
                break;
            case 4:
                Log.v(TAG,"in stamps 4");

                stamp1.setImageResource(R.drawable.checkmark2);
                stamp2.setImageResource(R.drawable.checkmark2);
                stamp3.setImageResource(R.drawable.checkmark2);
                stamp4.setImageResource(R.drawable.checkmark2);
                break;
            case 5:
                Log.v(TAG,"in stamps 5");

                stamp1.setImageResource(R.drawable.checkmark2);
                stamp2.setImageResource(R.drawable.checkmark2);
                stamp3.setImageResource(R.drawable.checkmark2);
                stamp4.setImageResource(R.drawable.checkmark2);
                stamp5.setImageResource(R.drawable.checkmark2);
                break;
            case 6:
                Log.v(TAG,"in stamps 6");

                stamp1.setImageResource(R.drawable.checkmark2);
                stamp2.setImageResource(R.drawable.checkmark2);
                stamp3.setImageResource(R.drawable.checkmark2);
                stamp4.setImageResource(R.drawable.checkmark2);
                stamp5.setImageResource(R.drawable.checkmark2);
                stamp6.setImageResource(R.drawable.checkmark2);
                break;
            case 7:
                Log.v(TAG,"in stamps 7");

                stamp1.setImageResource(R.drawable.checkmark2);
                stamp2.setImageResource(R.drawable.checkmark2);
                stamp3.setImageResource(R.drawable.checkmark2);
                stamp4.setImageResource(R.drawable.checkmark2);
                stamp5.setImageResource(R.drawable.checkmark2);
                stamp6.setImageResource(R.drawable.checkmark2);
                stamp7.setImageResource(R.drawable.checkmark2);
                break;
            case 8:
                Log.v(TAG,"in stamps 8");

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

    private void getFromFirebase(OnDataReceiveCallback callback){
        Log.d("CREATION", "get from firebase ");

        fireDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Transaction transaction = snapshot.getValue(Transaction.class);
                Log.v(TAG, "snapshot key"+snapshot.getKey());
                Log.v(TAG, "transaction id "+transaction.getUniqueId());
                Log.v(TAG, "transaction userId "+transaction.getUserId());


                if(numberOfStamps < 8) {
                            if (!transaction.isStamped()) {
                                Log.v(TAG, "in no stamps");
                                Log.v(TAG, "num of stamps " + numberOfStamps);

                                numberOfStamps += 1;
                                callback.onDataReceived(numberOfStamps);
                                transactions.add(transaction);


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