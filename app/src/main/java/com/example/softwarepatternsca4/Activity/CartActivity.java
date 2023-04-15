package com.example.softwarepatternsca4.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.softwarepatternsca4.Domain.Item;
import com.example.softwarepatternsca4.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

public class CartActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    TextView totalFeetxt,deliveryTxt, emptyTxt, checkoutBtn;
    private double tax;
    private ScrollView scrollView;
    ArrayList<Item> result;
    ArrayList<Item> itemslist;
    DatabaseReference fireDB;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String userID;
    ArrayList<Item> cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        assert mUser != null;
        userID = mUser.getUid();

        cart = new ArrayList<>();
        fireDB = FirebaseDatabase.getInstance().getReference("cart");
        initView();
        bottomNavigation();


    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this,CartActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this,MainActivity.class));
            }
        });
    }

    private void initView() {


        totalFeetxt = findViewById(R.id.totalFeeTv);
        deliveryTxt = findViewById(R.id.deliveryTxt);
        emptyTxt = findViewById(R.id.emptyTxt);
        scrollView = findViewById(R.id.scrollView3);
        recyclerViewList = findViewById(R.id.cartView);
        checkoutBtn = findViewById(R.id.CheckoutBTN);
        DatabaseReference transactionDB;
        transactionDB = FirebaseDatabase.getInstance().getReference();

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference userdatabase;
                userdatabase = FirebaseDatabase.getInstance().getReference("Users");
                //Make new transaction
                double totalcost = 0;
                for(int i = 0; i < cart.size(); i++){
                    totalcost+= cart.get(i).getPrice();
                }
                String uniqueId = UUID.randomUUID().toString();


                //Get user details necessary for transaction
                Transaction transaction = new Transaction(cart,userID,totalcost,uniqueId,false);

                transactionDB.child("Transaction").child(transaction.getUniqueId()).setValue(transaction).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("Transaction","Transaction added to database");

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Transaction","Transaction failed to add to database");

                    }
                });
            }
        });
    }
}

