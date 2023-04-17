package com.example.softwarepatternsca4.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.softwarepatternsca4.Adapter.AttributesAdapter;
import com.example.softwarepatternsca4.Adapter.ItemAdapter;
import com.example.softwarepatternsca4.Adapter.UserAdapter;
import com.example.softwarepatternsca4.Domain.Item;
import com.example.softwarepatternsca4.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class simPurchase extends AppCompatActivity {
    private RecyclerView recyclerViewProductList;
    ItemAdapter adapter;
    ArrayList<Item> itemList;
    DatabaseReference fireDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sim_purchase);

        itemList = new ArrayList<Item>();
        recyclerViewProductList = findViewById(R.id.simpurchaseRecycleView);
        fireDB = FirebaseDatabase.getInstance().getReference("items");
        getItems();


    }

    public void getItems(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewProductList.setLayoutManager(linearLayoutManager);
        fireDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Item item = dataSnapshot.getValue(Item.class);
                    itemList.add(item);
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
        adapter = new ItemAdapter(itemList);


        recyclerViewProductList.setAdapter(adapter);
    }
}