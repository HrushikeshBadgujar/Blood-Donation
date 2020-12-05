package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Add_Donor extends AppCompatActivity {


    public static final String TAG = null;
    EditText dName, dBloodGrp, dAge, dPhone, dCity, dLocality;
    Button dMakeRequest;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ProgressBar progressBar;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__donor);


        dName = findViewById(R.id.d_name);
        dBloodGrp = findViewById(R.id.d_blood_grp);
        dAge = findViewById(R.id.d_age);
        dPhone = findViewById(R.id.d_phone_no);
        dCity = findViewById(R.id.d_city);
        dLocality = findViewById(R.id.d_locality);

        dMakeRequest = findViewById(R.id.d_requestBtn);

        progressBar = findViewById(R.id.d_progressBar);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        dMakeRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Name = dName.getText().toString();
                String BloodGrp = dBloodGrp.getText().toString();
                String Age = dAge.getText().toString();
                String Phone = dPhone.getText().toString();
                String City = dCity.getText().toString();
                String Locality = dLocality.getText().toString();


                // Checking the conditions
                if (TextUtils.isEmpty(Name)){
                    dName.setError("Please Fill this Field");
                    return;
                }

                if (TextUtils.isEmpty(BloodGrp)){
                    dBloodGrp.setError("Please Fill this Field");
                    return;
                }

                if (TextUtils.isEmpty(Age)){
                    dAge.setError("Please Fill this Field");
                    return;
                }

                if (TextUtils.isEmpty(Phone)){
                    dPhone.setError("Please Fill this Field");
                    return;
                }

                if (TextUtils.isEmpty(City)){
                    dCity.setError("Please Fill this Field");
                    return;
                }

                if (TextUtils.isEmpty(Locality)){
                    dLocality.setError("Please Fill this Field");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);


                userId = fAuth.getCurrentUser().getUid();

                // Storing the data in FireStore
                DocumentReference documentReference = fStore.collection("Donor").document(userId);

                Map<String,Object> user = new HashMap<>();          // In firebase data is stored using the hashmap (Key,Value) pair
                user.put("Full_Name",Name);
                user.put("Blood_Grp",BloodGrp);
                user.put("Age",Age);
                user.put("Phone",Phone);
                user.put("City",City);
                user.put("Locality",Locality);

                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess : Data is stored for : "+ userId);
                    }
                });

                startActivity(new Intent(getApplicationContext(), Fragment2.class));


            }
        });



    }
}