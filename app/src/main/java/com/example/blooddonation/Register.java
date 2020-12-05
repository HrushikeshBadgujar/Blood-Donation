package com.example.blooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.api.LogDescriptor;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.proto.TargetGlobal;

import java.awt.font.TextAttribute;
import java.nio.file.FileStore;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {


    public static final String TAG = null;
    // Created the variables
    EditText mFullName, mEmail, mPassword, mPhone, mCity, mBloodGrp;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ProgressBar progressBar;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        mPhone = findViewById(R.id.phone);
        mCity = findViewById(R.id.city);
        mBloodGrp = findViewById(R.id.bloodGrp);

        mRegisterBtn = findViewById(R.id.registerBtn);
        mLoginBtn = findViewById(R.id.createText);

        progressBar = findViewById(R.id.progressBar);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        // If the user is already logged in then it will go to main
        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Storing the mail and password in variable which is used later for creating account
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                final String fullName = mFullName.getText().toString();
                final String phone = mPhone.getText().toString().trim();
                final String city = mCity.getText().toString().trim();
                final String bloodGrp = mBloodGrp.getText().toString().trim();


                // Checking the conditions
                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }

                if (password.length() < 6 ){
                    mPassword.setError("Password length should be >= 6 ");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);


                // Register the user in the firebase
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();

                            // Getting the user ID in userID variable which is used for storing the resp data
                            userId = fAuth.getCurrentUser().getUid();

                            // Storing the data in FireStore
                            DocumentReference documentReference = fStore.collection("Users").document(userId);

                            Map<String,Object> user = new HashMap<>();          // In firebase data is stored using the hashmap (Key,Value) pair
                            user.put("Full_Name",fullName);
                            user.put("Phone_No",phone);
                            user.put("City",city);
                            user.put("Blood_Group",bloodGrp);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess : user profile is created for : "+ userId);
                                }
                            });


                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }

                        else {
                            Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });




            }
        });


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));

            }
        });



    }
}