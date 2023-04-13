package com.example.softwarepatternsca4.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.softwarepatternsca4.Domain.Item;
import com.example.softwarepatternsca4.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class addProductActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    FirebaseStorage storage;
    StorageReference storageReference;
    String title;
    String category;
    String manufacturer;
    ImageView image;
    ImageView productImage;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;


    double price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mAuth = FirebaseAuth.getInstance();
        EditText titleET = findViewById(R.id.titleET);
        EditText categoryET = findViewById(R.id.categoryET);
        EditText manufacturerET = findViewById(R.id.manufacturerET);
        EditText priceET = findViewById(R.id.priceET);
         productImage = findViewById(R.id.ProductImage);
        Button submitProductBTN = findViewById(R.id.submitProductBTN);
        Button chooseImageBTN = findViewById(R.id.chooseImageBTN);

        DatabaseReference firebaseRef = FirebaseDatabase.getInstance().getReference();


        chooseImageBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        submitProductBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = titleET.getText().toString();
                price = Double.parseDouble(priceET.getText().toString());
                manufacturer = manufacturerET.getText().toString();
                category = categoryET.getText().toString();
                String uniqueId = UUID.randomUUID().toString();
                //pic is null as of now
                String pic="";


                Item prod = new Item(title,manufacturer,uniqueId,price,category,"productImage");
                //uploadImage(uniqueId);

                firebaseRef.child("Products").child(uniqueId).setValue(prod).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("MESSAGE", "Success at database product creation");


                        uploadImage(uniqueId);
                    }
                }).addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MESSAGE", "Failed at database product creation");
                    }
                });

            }
        });

    }

    private void chooseImage() {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                productImage.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {

                e.printStackTrace();

            }
        }
    }

    private void uploadImage(String uniqueId) {
        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference ref = storageReference.child("images/"+ uniqueId);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(addProductActivity.this, "Uploaded product", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(addProductActivity.this, "Failed Image upload "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }


}