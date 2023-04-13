package com.example.softwarepatternsca4.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.example.softwarepatternsca4.Activity.detailActivity;
import com.example.softwarepatternsca4.Domain.Item;
import com.example.softwarepatternsca4.R;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{
    ArrayList<Item> items;

    public ItemAdapter(ArrayList<Item> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_row,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(items.get(position).getTitle());
        holder.category.setText(String.valueOf(items.get(position).getCategory()));
        holder.manufacturer.setText(items.get(position).getManufacturer());
        holder.price.setText(String.valueOf(items.get(position).getPrice()));
        String picurl = items.get(position).getImage();
        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(picurl,"drawable",holder.itemView.getContext().getPackageName());
        holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.attribute_background1));



        Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.pic);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title,category,manufacturer,price;
        ImageView pic;
        TextView addBtn;
        ConstraintLayout mainLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.constraintLayout);
            title = itemView.findViewById(R.id.Titletxt);
            category = itemView.findViewById(R.id.CategoryTxt);
            pic = itemView.findViewById(R.id.itemImage);
            manufacturer = itemView.findViewById(R.id.ManufacturerTxt);
            price = itemView.findViewById(R.id.PriceTxt);


        }

        @Override
        public void onClick(View view) {
            int position = this.getLayoutPosition(); //get the position where the click happened
            String name =items.get(position).getTitle();
            Item p = items.get(position);

            Intent intent = new Intent(view.getContext(), detailActivity.class);
            intent.putExtra("product",p);
            view.getContext().startActivity(intent);
        }
    }
}
