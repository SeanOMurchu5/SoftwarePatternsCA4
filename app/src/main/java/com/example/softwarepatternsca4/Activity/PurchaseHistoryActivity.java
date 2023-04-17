package com.example.softwarepatternsca4.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;

import com.example.softwarepatternsca4.Adapter.CartAdapter;
import com.example.softwarepatternsca4.Adapter.purchaseHistoryAdapter;
import com.example.softwarepatternsca4.Domain.Item;
import com.example.softwarepatternsca4.Interface.ChangeNumberItemsListener;
import com.example.softwarepatternsca4.Interface.OnDataReceiveCallback;
import com.example.softwarepatternsca4.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PurchaseHistoryActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    DatabaseReference fireDB;
    String userID;

    ArrayList<Transaction> dataset;
    RecyclerView purchaseHistoryRecyclerview;
    purchaseHistoryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);
        mAuth = FirebaseAuth.getInstance();
        purchaseHistoryRecyclerview = findViewById(R.id.purchaseHistoryRecyclerView);

        mUser = mAuth.getCurrentUser();
        assert mUser != null;
        userID = mUser.getUid();
        fireDB = FirebaseDatabase.getInstance().getReference("Transaction").child(userID);
        initView();
    }


    public void initView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        purchaseHistoryRecyclerview.setLayoutManager(linearLayoutManager);
        dataset = new ArrayList();

        getFromFirebase(new OnDataReceiveCallback(){
            @Override
            public void onDataReceived(ArrayList list) {
                Log.d("CREATION", "received data ");

                dataset = list;

                adapter = new purchaseHistoryAdapter(dataset);

                purchaseHistoryRecyclerview.setAdapter(adapter);
            }

            @Override
            public void onDataReceived(int n) {

            }

        });



    }

    private void getFromFirebase(OnDataReceiveCallback callback){
        Log.d("CREATION", "get from firebase ");
        ArrayList<Transaction> list = new ArrayList<>();
        fireDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Transaction transaction = snapshot.getValue(Transaction.class);
                Log.v(TAG, "inside purchase activity firebase fetch , transaction : "+transaction.getUniqueId());

                list.add(transaction);
                callback.onDataReceived(list);


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