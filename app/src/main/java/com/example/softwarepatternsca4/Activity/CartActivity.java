package com.example.softwarepatternsca4.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.softwarepatternsca4.Adapter.CartAdapter;
import com.example.softwarepatternsca4.Domain.Item;
import com.example.softwarepatternsca4.Interface.ChangeNumberItemsListener;
import com.example.softwarepatternsca4.Interface.OnDataReceiveCallback;
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

public class CartActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    TextView totalFeetxt,discountApplied, emptyTxt, checkoutBtn, discountBtn;
    private double tax;
    double totalcost, totaldelivery;
    private ScrollView scrollView;
    ArrayList<Item> result;
    ArrayList<Item> itemslist;
    DatabaseReference fireDB;
    DatabaseReference stockDB;
    DatabaseReference userDB;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String userID;
    ArrayList<Item> cart;
    double itemTotal;

    boolean discount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        discount = false;
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        assert mUser != null;
        userID = mUser.getUid();

        cart = new ArrayList<>();
        fireDB = FirebaseDatabase.getInstance().getReference("cart");
        stockDB = FirebaseDatabase.getInstance().getReference("items");
        userDB = FirebaseDatabase.getInstance().getReference("users");
        itemTotal = 0;
        initView();
        initList();

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


        totalFeetxt = findViewById(R.id.totalTV);
        discountApplied = findViewById(R.id.discountTxt);
        emptyTxt = findViewById(R.id.emptyTxt);
        scrollView = findViewById(R.id.scrollView3);
        recyclerViewList = findViewById(R.id.cartView);
        checkoutBtn = findViewById(R.id.CheckoutBTN);
        discountBtn = findViewById(R.id.DiscountBtn);
        DatabaseReference transactionDB;
        transactionDB = FirebaseDatabase.getInstance().getReference();

        userDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                 if(user.getUserId().equalsIgnoreCase(userID)){
                     if(user.isActiveDiscount()){
                         discountBtn.setVisibility(View.VISIBLE);
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

        discountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyDiscount();
                discount = true;
            }
        });

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Make new transaction
                double totalcost = 0;
                for(int i = 0; i < cart.size(); i++){
                    totalcost+= cart.get(i).getPrice();
                }
                String uniqueId = UUID.randomUUID().toString();


                //Get user details necessary for transaction
                Transaction transaction = new Transaction(cart,userID,totalcost,uniqueId,false);

                transactionDB.child("Transaction").child(userID).child(uniqueId).setValue(transaction).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("Transaction","Transaction added to database");
                        adjustStock(cart);

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

    public void adjustStock(ArrayList<Item> cart){

        stockDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
              Item item = snapshot.getValue(Item.class);
              for (int i = 0; i < cart.size();i++){
                  if(item.getUniqueId().equalsIgnoreCase(cart.get(i).getUniqueId())){
                      int num = item.getStock() - cart.get(i).getNumberInCart();
                      item.setStock(num);
                      stockDB.child(item.getUniqueId()).setValue(item);

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
    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        Log.d("CREATION", "start of initList ");



        getFromFirebase(new OnDataReceiveCallback(){
            @Override
            public void onDataReceived(ArrayList list) {
                Log.d("CREATION", "received data ");

                cart = list;
                adapter = new CartAdapter(list, CartActivity.this, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        Log.d("CREATION", "calc cart ");
                        calculateCart();
                    }
                });

                recyclerViewList.setAdapter(adapter);

                if(list.isEmpty()){
                    Log.d("CREATION","empty ");

                    emptyTxt.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.GONE);
                }else{
                    Log.d("CREATION","no empty ");

                    emptyTxt.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onDataReceived(int n) {

            }

        });


    };
    private void getFromFirebase(OnDataReceiveCallback callback){
        Log.d("CREATION", "get from firebase ");
        ArrayList<Item> list = new ArrayList<>();

        fireDB.child(userID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.v(TAG, "Created child " + snapshot.getKey());
                Item item = snapshot.getValue(Item.class);
                list.add(item);
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
                Log.v(TAG, "cancelled");

            }
        });

    }

    public Double getTotalFee(ArrayList<Item> list) {


        double fee =0;
        for(int i = 0;i< list.size();i++){
            fee = fee + (list.get(i).getPrice() * list.get(i).getNumberInCart());
        }

        return fee;
    }

    public void applyDiscount(){
      itemTotal = itemTotal * .8;
    }


    private void calculateCart(){
        double delivery=10;

        tax= Math.round((getTotalFee(cart))*100)/100;
        double total = Math.round((getTotalFee(cart) + delivery) * 100)/100;
        double itemTotal = Math.round(getTotalFee(cart)*100)/100;

        totalFeetxt.setText("$"+itemTotal);
        if(discount){
            discountApplied.setText("20%");

        }else{
            discountApplied.setText("No Discount Applied");

        }
    }
}

