package com.example.softwarepatternsca4.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.softwarepatternsca4.Activity.MainActivity;
import com.example.softwarepatternsca4.Domain.AttributeDomain;
import com.example.softwarepatternsca4.Domain.Item;
import com.example.softwarepatternsca4.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AttributesAdapter extends RecyclerView.Adapter<AttributesAdapter.ViewHolder>{
    ArrayList<AttributeDomain> attributeDomains;
    ArrayList<Item> rc;
    RecyclerView.Adapter a;
    RecyclerView recyclerView;
    MainActivity mainActivity;
    Boolean titlebool, catbool,manubool, pricebool;

    public AttributesAdapter(ArrayList<AttributeDomain> attributeDomains) {
        this.attributeDomains = attributeDomains;
    }
    public AttributesAdapter(ArrayList<AttributeDomain> attributeDomains, MainActivity a) {
        this.attributeDomains = attributeDomains;
       this.mainActivity = a;
        this.titlebool = true;
        this.catbool = true;
        this.manubool = true;
        this.pricebool = true;

    }



    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_attributes,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull AttributesAdapter.ViewHolder holder, int position) {
        holder.categoryName.setText(attributeDomains.get(position).getTitle());
        String picurl="";
        switch (position){
            case 0:{
                picurl = "title";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.attribute_background1));
                break;
            }
            case 1:{
                picurl = "manufacturer";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.attribute_background2));
                break;}
            case 2:{
                picurl = "category";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.attribute_background3));
                break;}
            case 3:{
                picurl = "price";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.attribute_background4));
                break; }


        }
        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(picurl,"drawable",holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.categoryPic);
    }

    @Override
    public int getItemCount() {
        return attributeDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView categoryName;
        ImageView categoryPic;
        ConstraintLayout mainLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryPic = itemView.findViewById(R.id.categoryPic);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = this.getLayoutPosition(); //get the position where the click happened
            String name =attributeDomains.get(position).getTitle();
            Log.d("CREATION","in on click "+name);

            switch (name){
                case "Title":{
                    Log.d("CREATION","in title ");

                    if(titlebool){
                        mainActivity.sortItemList("Title",titlebool);
                        titlebool = false;
                    }else{
                        mainActivity.sortItemList("Title",titlebool);
                        titlebool = true;
                    }
                    break;
                }
                case "Category":{
                    if(catbool){
                        mainActivity.sortItemList("Category",catbool);
                        catbool = false;
                    }else{
                        mainActivity.sortItemList("Category",catbool);
                        catbool = true;
                    }                    break;
                }
                case "Manufacturer":{
                    if(manubool){
                        mainActivity.sortItemList("Manufacturer",manubool);
                        manubool = false;
                    }else{
                        mainActivity.sortItemList("Manufacturer",manubool);
                        manubool = true;
                    }
                    break;
                }
                case "Price":{
                    if(pricebool){
                        mainActivity.sortItemList("Price",pricebool);
                        pricebool = false;
                    }else{
                        mainActivity.sortItemList("Price",pricebool);
                        pricebool = true;
                    }
                    break;
                }

            }

        }
    }


}
