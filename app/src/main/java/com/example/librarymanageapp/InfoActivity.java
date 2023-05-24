package com.example.librarymanageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.librarymanageapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class InfoActivity extends AppCompatActivity {
    TextView email, name, dc, sdt;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        initview();
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String uid=user.getUid().toString();
        //getuser
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    User user = dataSnapshot1.getValue(User.class);
                    if(user.getUid().equals(uid)){
                        email.setText(user.getEmail().toString());
                        name.setText(user.getName().toString());
                        dc.setText(user.getAddress().toString());
                        sdt.setText(user.getPhone().toString());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initview() {
        email=findViewById(R.id.info_email);
        name=findViewById(R.id.info_ten);
        dc=findViewById(R.id.info_dc);
        sdt=findViewById(R.id.info_sdt);
        back=findViewById(R.id.info_back);
    }
}