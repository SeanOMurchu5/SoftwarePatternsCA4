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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class searchStock extends AppCompatActivity {
    private RecyclerView recyclerViewItems;
    ItemAdapter adapter;
    ArrayList<Item> itemList;
    DatabaseReference fireDB;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_stock);
        itemList = new ArrayList<Item>();
        recyclerViewItems = findViewById(R.id.stockRecyclerView);
        fireDB = FirebaseDatabase.getInstance().getReference("Items");

        getItems();

    }

    public void getItems(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewItems.setLayoutManager(linearLayoutManager);
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


        recyclerViewItems.setAdapter(adapter);
    }
}