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

public class Fragment1 extends Fragment {

    FloatingActionButton add_receiver;

    public RecyclerView receiverList;
    public FirebaseFirestore fStore;
    public FirestoreRecyclerAdapter adapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment1_layout,container,false);
       return view;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        add_receiver = getActivity().findViewById(R.id.receiver_request);
        receiverList = getActivity().findViewById(R.id.receiver_list);


        // Query
        Query query = FirebaseFirestore.getInstance().collection("Demo");


        // Recycler Option
        FirestoreRecyclerOptions<DemoModel> options = new FirestoreRecyclerOptions.Builder<DemoModel>()
                .setQuery(query, DemoModel.class)
                .build();


        // RecyclerAdapter
        adapter = new FirestoreRecyclerAdapter<DemoModel, ReceiverViewHolder>(options) {

            @NonNull
            @Override
            public ReceiverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                /*View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_receiver, parent,false);*/
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_receiver,parent,false);
                return new ReceiverViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ReceiverViewHolder holder, int i, @NonNull DemoModel demoModel) {

                holder.list_city.setText(demoModel.getCity());
                holder.list_name.setText(demoModel.getName());
            }
        };

         receiverList.setHasFixedSize(true);

         receiverList.setLayoutManager(new LinearLayoutManager(getContext()));

         receiverList.setAdapter(adapter);

    }

    // View Holder Class
    public class ReceiverViewHolder extends RecyclerView.ViewHolder{

        public TextView list_name;
        public TextView list_city;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);

            list_name = itemView.findViewById(R.id.r_list_name);
            list_city = itemView.findViewById(R.id.r_list_city);

        }
    }



    @Override
    public void onStart() {
        super.onStart();

        adapter.startListening();

        // Add Receiver Floating Button
        add_receiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), Add_Receiver.class);
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
