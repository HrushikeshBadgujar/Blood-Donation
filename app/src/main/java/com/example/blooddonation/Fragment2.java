package com.example.blooddonation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Fragment2 extends Fragment {

    FloatingActionButton add_donor;

    public RecyclerView donorList;
    public FirebaseFirestore fStore;
    public FirestoreRecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment2_layout,container,false);
        return view;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        add_donor = getActivity().findViewById(R.id.donor_request);
        donorList = getActivity().findViewById(R.id.donor_list);


        // Query
        Query query = FirebaseFirestore.getInstance().collection("Donor");


        // Recycler Option
        FirestoreRecyclerOptions<DonorModel> options = new FirestoreRecyclerOptions.Builder<DonorModel>()
                .setQuery(query, DonorModel.class)
                .build();


        // RecyclerAdapter
        adapter = new FirestoreRecyclerAdapter<DonorModel, DonorViewHolder>(options) {

            @NonNull
            @Override
            public DonorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                /*View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_receiver, parent,false);*/
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_donor,parent,false);
                return new DonorViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull DonorViewHolder holder, int i, @NonNull DonorModel donorModel) {

                holder.list_name.setText(" Name : "+donorModel.getFull_Name());
                holder.list_blood.setText(" Blood Group : "+donorModel.getBlood_Grp());
                holder.list_phone.setText(" Phone : "+donorModel.getPhone());
                holder.list_age.setText(donorModel.getAge()+" Years");
                holder.list_city.setText(" City : "+donorModel.getCity());
                holder.list_locality.setText(" Locality : "+donorModel.getLocality());
            }
        };

        donorList.setHasFixedSize(true);

        donorList.setLayoutManager(new LinearLayoutManager(getContext()));

        donorList.setAdapter(adapter);
    }


    // View Holder Class
    public class DonorViewHolder extends RecyclerView.ViewHolder{

        public TextView list_name;
        public TextView list_blood;
        public TextView list_phone;
        public TextView list_age;
        public TextView list_city;
        public TextView list_locality;

        public DonorViewHolder(@NonNull View itemView) {
            super(itemView);

            list_name = itemView.findViewById(R.id.d_list_name);
            list_blood = itemView.findViewById(R.id.d_list_blood_grp);
            list_phone = itemView.findViewById(R.id.d_list_phone);
            list_age = itemView.findViewById(R.id.d_list_age);
            list_city = itemView.findViewById(R.id.d_list_city);
            list_locality = itemView.findViewById(R.id.d_list_locality);

        }
    }




    @Override
    public void onStart() {
        super.onStart();

        adapter.startListening();

        // Add Receiver Floating Button
        add_donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), Add_Donor.class);
                startActivity(intent);

            }
        });

    }


    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
