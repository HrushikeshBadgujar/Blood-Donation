package com.example.blooddonation;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Fragment4 extends Fragment {

    ImageView imageView;
    TextView full_name, email, phone, blood_grp, city;
    DocumentReference reference, reference_reference, donor_reference;
    FirebaseFirestore firestore;
    FirebaseAuth fAuth;
    Button logout, deleteReceiver, deleteDonor;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment4_layout,container,false);
        return view;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imageView = getActivity().findViewById(R.id.profile_pic);
        full_name = getActivity().findViewById(R.id.full_name);
        email = getActivity().findViewById(R.id.email);
        phone = getActivity().findViewById(R.id.phone_no);
        city = getActivity().findViewById(R.id.city);
        blood_grp = getActivity().findViewById(R.id.blood_grp);

        logout = getActivity().findViewById(R.id.logoutBtn);
        deleteReceiver = getActivity().findViewById(R.id.delete_receiver);
        deleteDonor = getActivity().findViewById(R.id.delete_donor);

        fAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();


    }

    @Override
    public void onStart() {
        super.onStart();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = user.getUid();
        final String Email = user.getEmail();


        reference = firestore.collection("Users").document(userId);

        reference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.getResult().exists()){

                            String nameResult = task.getResult().getString("Full_Name");
                            String emailResult = task.getResult().getString("email");
                            String phoneResult = task.getResult().getString("Phone_No");
                            String cityResult = task.getResult().getString("City");
                            String blood_grpResult = task.getResult().getString("Blood_Group");


                            full_name.setText(nameResult);
                            email.setText(Email);
                            phone.setText(phoneResult);
                            city.setText(cityResult);
                            blood_grp.setText(blood_grpResult);


                        }else {

                        }
                    }
                });



        // Delete Receiver Button
        deleteReceiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseFirestore.getInstance().collection("Receiver").document(userId).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "Receiver Request Deleted Successfully ! ", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Error ! "+e, Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });



        // Delete Donor Button
        deleteDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseFirestore.getInstance().collection("Donor").document(userId).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "Donor Request Deleted Successfully ! ", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Error ! "+e, Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });



        // Logout Button
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fAuth.getCurrentUser() != null)
                    fAuth.signOut();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });



    }
}
