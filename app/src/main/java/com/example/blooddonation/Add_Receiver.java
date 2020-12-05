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

public class Add_Receiver extends AppCompatActivity {

    public static final String TAG = null;
    EditText rName, rBloodGrp, rReason, rPhone, rCity, rLocality;
    Button rMakeRequest;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ProgressBar progressBar;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__receiver);


        rName = findViewById(R.id.r_name);
        rBloodGrp = findViewById(R.id.r_blood_grp);
        rReason = findViewById(R.id.r_reason);
        rPhone = findViewById(R.id.r_phone_no);
        rCity = findViewById(R.id.r_city);
        rLocality = findViewById(R.id.r_locality);

        rMakeRequest = findViewById(R.id.r_requestBtn);

        progressBar = findViewById(R.id.r_progressBar);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();



        rMakeRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Name = rName.getText().toString();
                String BloodGrp = rBloodGrp.getText().toString();
                String Reason = rReason.getText().toString();
                String Phone = rPhone.getText().toString();
                String City = rCity.getText().toString();
                String Locality = rLocality.getText().toString();


                // Checking the conditions
                if (TextUtils.isEmpty(Name)){
                    rName.setError("Please Fill this Field");
                    return;
                }

                if (TextUtils.isEmpty(BloodGrp)){
                    rBloodGrp.setError("Please Fill this Field");
                    return;
                }

                if (TextUtils.isEmpty(Reason)){
                    rReason.setError("Please Fill this Field");
                    return;
                }

                if (TextUtils.isEmpty(Phone)){
                    rPhone.setError("Please Fill this Field");
                    return;
                }

                if (TextUtils.isEmpty(City)){
                    rCity.setError("Please Fill this Field");
                    return;
                }

                if (TextUtils.isEmpty(Locality)){
                    rLocality.setError("Please Fill this Field");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);


                userId = fAuth.getCurrentUser().getUid();

                // Storing the data in FireStore
                DocumentReference documentReference = fStore.collection("Receiver").document(userId);

                Map<String,Object> user = new HashMap<>();          // In firebase data is stored using the hashmap (Key,Value) pair
                user.put("Full_Name",Name);
                user.put("Blood_Grp",BloodGrp);
                user.put("Reason",Reason);
                user.put("Phone",Phone);
                user.put("City",City);
                user.put("Locality",Locality);

                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess : Data is stored for : "+ userId);
                    }
                });

                startActivity(new Intent(getApplicationContext(), MainActivity.class));


            }
        });









    }
}