package com.example.softwarepatternsca4.Activity;

import static android.widget.Toast.LENGTH_LONG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.softwarepatternsca4.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    String email;
    String name;
    String paymentMethod;
    String shippingaddress;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        EditText emailET = findViewById(R.id.SignupEmailET);
        EditText passwordET = findViewById(R.id.passwordET);
        EditText addressET = findViewById(R.id.ShippingAddressET);
        EditText nameET = findViewById(R.id.signupNameET);
        EditText paymentMethodET = findViewById(R.id.PaymentMethodET);


        DatabaseReference firebaseUsers = FirebaseDatabase.getInstance("https://spca4-e7325-default-rtdb.europe-west1.firebasedatabase.app").getReference();

        Button signUpBtn = findViewById(R.id.signupBtn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                email = emailET.getText().toString();
                password = passwordET.getText().toString();
                shippingaddress = addressET.getText().toString();
                paymentMethod = paymentMethodET.getText().toString();
                name = nameET.getText().toString();


                mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        mUser = mAuth.getCurrentUser();
                                        Log.d("MESSAGE","email is "+email);
                                        Log.d("MESSAGE","password is "+password);
                                        String userID= mUser.getUid();
                                        User user = new User(name,shippingaddress,paymentMethod,email,userID,0);

                                        firebaseUsers.child("Users").child(userID).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Log.d("MESSAGE","Success at database user creation");

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {

                                            @Override
                                            public void onFailure(@NonNull Exception e){
                                                Log.d("MESSAGE","Failed at database user creation");
                                            }
                                        });
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);

                                    }else {

                                        Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        Log.d("MESSAGE","email "+email+ " password: "+password);

                                    }
                                }
                            });


            }
        });

    }

}