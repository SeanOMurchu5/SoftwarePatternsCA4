package com.example.softwarepatternsca4.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.softwarepatternsca4.Adapter.AttributesAdapter;
import com.example.softwarepatternsca4.Adapter.ItemAdapter;
import com.example.softwarepatternsca4.Domain.AttributeDomain;
import com.example.softwarepatternsca4.Domain.Item;
import com.example.softwarepatternsca4.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewAttributesList, recyclerViewProductList;
    private RecyclerView.Adapter adapter2;
    AttributesAdapter adapter;
    ArrayList<Item> itemList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true);
        recyclerViewAttributesList = findViewById(R.id.attributesRecyclerView);
        recyclerViewAttributesList.setLayoutManager(linearLayoutManager);

        ArrayList<AttributeDomain> attributes = new ArrayList<>();

        attributes.add(new AttributeDomain("Title","title"));
        attributes.add(new AttributeDomain("Manufacturer","manufacturer"));
        attributes.add(new AttributeDomain("Category","category"));
        attributes.add(new AttributeDomain("Price","price"));

        adapter = new AttributesAdapter(attributes, this);


        recyclerViewAttributesList.setAdapter(adapter);
        recyclerViewItems();

        bottomNavigation();

    }

    private void recyclerViewItems() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewProductList = findViewById(R.id.productRecyclerView);
        recyclerViewProductList.setLayoutManager(linearLayoutManager);

        itemList = new ArrayList<Item>();
        itemList.add(new Item("Iphone 14","Apple", UUID.randomUUID().toString(),1160,"Phone","iphone"));
        itemList.add(new Item("Samsung 10","Samsung", UUID.randomUUID().toString(),1200,"Phone","samsung"));
        itemList.add(new Item("IMac 2","Apple", UUID.randomUUID().toString(),2160,"Computer","imac"));
        itemList.add(new Item("Razor laptop","Razor", UUID.randomUUID().toString(),1120,"Computer","razor"));
        adapter2 = new ItemAdapter(itemList);


        recyclerViewProductList.setAdapter(adapter2);
    }



    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout addBtn = findViewById(R.id.addBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CartActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainActivity.class));
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,addProductActivity.class));

            }
        });
    }

    public void sortItemList(String variableName) {
        Log.d("CREATION","in sortItems ");

        Collections.sort(itemList, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                switch (variableName) {
                    case "Title":
                        return o1.getTitle().compareToIgnoreCase(o2.getTitle());
                    case "Manufacturer":
                        return o1.getManufacturer().compareToIgnoreCase(o2.getManufacturer());
                    case "Category":
                        return o1.getCategory().compareToIgnoreCase(o2.getCategory());
                    case "Price":
                        return Double.compare(o1.getPrice(), o2.getPrice());
                    default:
                        return 0;
                }
            }
        });
        adapter2.notifyDataSetChanged();
    }
}