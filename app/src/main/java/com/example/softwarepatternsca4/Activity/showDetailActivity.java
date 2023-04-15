package com.example.softwarepatternsca4.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.softwarepatternsca4.Domain.Item;
import com.example.softwarepatternsca4.Helper.ManagementCart;
import com.example.softwarepatternsca4.R;

public class showDetailActivity extends AppCompatActivity {
    private TextView addToCartBtn;
    private TextView titleTxt,feeTxt,descriptionTxt,numberOrderTxt;
    ImageView addBtn,minusBtn, productImage;
    private Item object;
    int numberOrder=1;
    private ManagementCart managementCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        managementCart = new ManagementCart(this);
        initView();
        getBundle();
    }

    private void getBundle() {
        object= (Item) getIntent().getSerializableExtra("object");
        int drawableResourceId = this.getResources().getIdentifier(object.getImage(),"drawable",this.getPackageName());
        Glide.with(this)
                .load(drawableResourceId)
                .into(productImage);
        titleTxt.setText(object.getTitle());
        feeTxt.setText("$"+object.getPrice());
        descriptionTxt.setText(object.getManufacturer());
        numberOrderTxt.setText(String.valueOf(numberOrder));

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOrder = numberOrder+1;
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numberOrder>1){
                    numberOrder = numberOrder-1;
                }
                numberOrderTxt.setText(String.valueOf(numberOrder));

            }
        });

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                object.setNumberInCart(numberOrder);
                try {
                    managementCart.insertProduct(object);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Log.d("CREATION","ADD TO CART BUTTON CLICKED ");

            }
        });
    }

    private void initView() {
        addToCartBtn=findViewById(R.id.addToCartBtn);
        titleTxt = findViewById(R.id.titleTxt);
        feeTxt = findViewById(R.id.priceTxt);
        descriptionTxt=findViewById(R.id.descriptionTxt);
        numberOrderTxt=findViewById(R.id.numberOrderTxt);
        addBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        productImage = findViewById(R.id.productPic);

    }
}