package com.example.blooddonation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {

    /*TextView fullName, email;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // For Bottom Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNav);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                new Fragment1()).commit();



        /*fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.textView3);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = fAuth.getCurrentUser().getUid();


        // Fetching the data from the firestore
        final DocumentReference documentReference = fStore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                // Setting the data from firestore
                fullName.setText(documentSnapshot.getString("Full_Name"));
                email.setText(documentSnapshot.getString("email"));

            }
        });*/


    }




    // For Bottom Navigation
    private BottomNavigationView.OnNavigationItemSelectedListener onNav = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selected = null;
            switch (item.getItemId()){
                case R.id.home_bottom:
                    selected = new Fragment1();
                    break;

                case R.id.ask_bottom:
                    selected = new Fragment2();
                    break;

                case R.id.queue_bottom:
                    selected = new Fragment3();
                    break;

                case R.id.profile_bottom:
                    selected = new Fragment4();
                    break;

            }

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                    selected).commit();


            return true;

        }
    };



    // For logout
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
}