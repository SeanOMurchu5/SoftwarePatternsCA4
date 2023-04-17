package com.example.softwarepatternsca4.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softwarepatternsca4.Activity.Transaction;
import com.example.softwarepatternsca4.Activity.detailActivity;
import com.example.softwarepatternsca4.R;

import java.util.ArrayList;

public class purchaseHistoryAdapter extends RecyclerView.Adapter<purchaseHistoryAdapter.ViewHolder>{
    ArrayList<Transaction> transactions;

    public purchaseHistoryAdapter(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("CREATION", "create viewholder ");

        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_transaction,parent,false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull purchaseHistoryAdapter.ViewHolder holder, int position) {
        Log.d("CREATION", "onbind purchasehistory");

        holder.uniqueId.setText("transaction :"+position);
        holder.numberOfItems.setText(String.valueOf(transactions.get(position).getProductIdList().size()));
        holder.totalCost.setText(String.valueOf(transactions.get(position).getTotalCost()));
        holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.attribute_background1));

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView numberOfItems,totalCost,uniqueId;
        ConstraintLayout mainLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.purchaseHistoryConstraint);
            numberOfItems = itemView.findViewById(R.id.numOfItemsTxt);
            totalCost = itemView.findViewById(R.id.priceTxt);
            uniqueId = itemView.findViewById(R.id.transactionId);


        }

        @Override
        public void onClick(View view) {
            int position = this.getLayoutPosition(); //get the position where the click happened
            String uniqueId =transactions.get(position).getUniqueId();
            Transaction p = transactions.get(position);

            Intent intent = new Intent(view.getContext(), detailActivity.class);
            intent.putExtra("Transaction",p);
            view.getContext().startActivity(intent);
        }
    }
}
