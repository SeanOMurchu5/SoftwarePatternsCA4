package com.example.softwarepatternsca4.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.softwarepatternsca4.Domain.Item;
import com.example.softwarepatternsca4.R;

public class detailActivity extends AppCompatActivity {
    private TextView addToCartBtn;
    private TextView titleTxt,feeTxt,descriptionTxt,numberOrderTxt;
    ImageView addBtn,minusBtn, productImage;
    private Item item;
    int numberOrder=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }
}