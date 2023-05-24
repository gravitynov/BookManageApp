package com.example.librarymanageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.librarymanageapp.model.News;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewDetailActivity extends AppCompatActivity {
    TextView title, des;
    Button del;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_detail);
        title=findViewById(R.id.new_detail_title);
        des=findViewById(R.id.new_detail_detail);
        del=findViewById(R.id.new_detail_del);
        Intent intent=getIntent();
        News n= (News) intent.getSerializableExtra("news");
        title.setText(n.getTitle().toString());
        des.setText(n.getDes().toString());
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("news");

                dataRef.child(n.getId()).removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                System.out.println("Data deleted successfully");
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.err.println("Error deleting data: " + e.getMessage());
                            }
                        });
            }
        });
    }
}