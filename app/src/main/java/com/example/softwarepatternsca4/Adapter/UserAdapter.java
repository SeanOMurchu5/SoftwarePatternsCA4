package com.example.softwarepatternsca4.Adapter;

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

import com.example.softwarepatternsca4.Activity.User;
import com.example.softwarepatternsca4.Activity.showDetailActivity;
import com.example.softwarepatternsca4.Domain.Item;
import com.example.softwarepatternsca4.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    ArrayList<User> users;

    public UserAdapter(ArrayList<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_user,parent,false);
        return new UserAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        holder.userEmail.setText(users.get(position).getEmail());
        holder.uniqueId.setText(users.get(position).getUserId());
        holder.userName.setText(users.get(position).getName());
        holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.attribute_background1));

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView uniqueId,userEmail,userName;
        ConstraintLayout mainLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.userConstraint);
            uniqueId = itemView.findViewById(R.id.userUniqueId);
            userEmail = itemView.findViewById(R.id.userEmail);
            userName = itemView.findViewById(R.id.userName);

        }

        @Override
        public void onClick(View view) {
            Log.d("CREATION","Inside onclick for USER adapter ");

            int position = this.getLayoutPosition(); //get the position where the click happened
            User p = users.get(position);

            Intent intent = new Intent(view.getContext(), showDetailActivity.class);
            intent.putExtra("user",p);
            view.getContext().startActivity(intent);
        }
    }
}
