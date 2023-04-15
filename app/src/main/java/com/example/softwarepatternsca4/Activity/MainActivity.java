package com.example.softwarepatternsca4.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
    private ItemAdapter adapter2;
    AttributesAdapter adapter;
    ArrayList<Item> itemList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemList = new ArrayList<Item>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                // getting search view of our item.
                MenuItem searchTitleItem = toolbar.getMenu().findItem(R.id.action_searchTitle);
                MenuItem searchCategoryItem = toolbar.getMenu().findItem(R.id.action_searchCategory);
                MenuItem searchManufacturerItem = toolbar.getMenu().findItem(R.id.action_searchManufacturer);

                SearchView searchTitleView = (SearchView) searchTitleItem.getActionView();
                SearchView searchCategoryView = (SearchView) searchCategoryItem.getActionView();
                SearchView searchManufacturerView = (SearchView) searchManufacturerItem.getActionView();


                switch (item.getItemId()) {
                    case R.id.action_searchTitle:
                        searchTitleView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {
                                // inside on query text change method we are
                                // calling a method to filter our recycler view.
                                filter(newText, "Title");
                                return false;
                            }
                        });
                        break;
                    case R.id.action_searchCategory:
                        searchCategoryView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {
                                // inside on query text change method we are
                                // calling a method to filter our recycler view.
                                filter(newText, "Category");
                                return false;
                            }
                        });
                        break;
                    case R.id.action_searchManufacturer:
                        searchManufacturerView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {
                                // inside on query text change method we are
                                // calling a method to filter our recycler view.
                                filter(newText, "Manufacturer");
                                return false;
                            }
                        });
                        break;


                }
                return true;

            }
        });
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

        itemList.add(new Item("Iphone 14","Apple", UUID.randomUUID().toString(),1160,"Phone","iphone",10));
        itemList.add(new Item("Samsung 10","Samsung", UUID.randomUUID().toString(),1200,"Phone","samsung",10));
        itemList.add(new Item("IMac 2","Apple", UUID.randomUUID().toString(),2160,"Computer","imac",10));
        itemList.add(new Item("Razor laptop","Razor", UUID.randomUUID().toString(),1120,"Computer","razor",10));
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

    public void sortItemList(String variableName,boolean b) {
        Log.d("CREATION","in sortItems ");
        if(b){
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
        }else{
            Collections.reverse(itemList);
        }

        adapter2.notifyDataSetChanged();
    }

    private void filter(String text,String method) {
        // creating a new array list to filter our data.
        ArrayList<Item> filteredlist = new ArrayList<Item>();

        // running a for loop to compare elements.
        for (Item item : itemList) {
            if(method.equalsIgnoreCase("Title")) {
                // checking if the entered string matched with any item of our recycler view.
                if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                    // if the item is matched we are
                    // adding it to our filtered list.
                    filteredlist.add(item);
                }
            }
            if(method.equalsIgnoreCase("Category")){
                if (item.getCategory().toLowerCase().contains(text.toLowerCase())) {
                    // if the item is matched we are
                    // adding it to our filtered list.
                    filteredlist.add(item);
                }
            }
            if(method.equalsIgnoreCase("Manufacturer")){
                if (item.getManufacturer().toLowerCase().contains(text.toLowerCase())) {
                    // if the item is matched we are
                    // adding it to our filtered list.
                    filteredlist.add(item);
                }
            }
        }

        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            adapter2.filterList(filteredlist);
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter2.filterList(filteredlist);
        }
    }
}