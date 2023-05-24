package com.example.librarymanageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddNewsActivity extends AppCompatActivity {
    EditText title, des;
    Button btnadd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);
        initview();
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t=title.getText().toString().trim();
                String d=des.getText().toString().trim();
                //db
                FirebaseDatabase db=FirebaseDatabase.getInstance();
                DatabaseReference ref=db.getReference("news");

                String id=ref.push().getKey();
                HashMap<String, String> hs=new HashMap<>();
                //id, String name, String author, String des, String price, String nxb, String img, String catID
                hs.put("id", id);
                hs.put("title", t);
                hs.put("des", d);
                //firebase

                ref.child(id).setValue(hs);
                Toast.makeText(AddNewsActivity.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void initview() {
        title=findViewById(R.id.add_news_activity_title);
        des=findViewById(R.id.add_news_activity_des);
        btnadd=findViewById(R.id.add_news_btnAdd);
    }
}