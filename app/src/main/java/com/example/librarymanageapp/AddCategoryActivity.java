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

public class AddCategoryActivity extends AppCompatActivity {
    EditText name, des, img;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        initview();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase db=FirebaseDatabase.getInstance();
                DatabaseReference ref=db.getReference("category");
                //get data from edittext
                String n=name.getText().toString().trim();
                String d=des.getText().toString().trim();
                String i=img.getText().toString().trim();
                String id=ref.push().getKey();
                HashMap<String, String> hs=new HashMap<>();
                hs.put("id", id);
                hs.put("name", n);
                hs.put("des", d);
                hs.put("img", i);
                //firebase

                ref.child(id).setValue(hs);
                Toast.makeText(AddCategoryActivity.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void initview() {
        name=findViewById(R.id.add_category_name);
        des=findViewById(R.id.add_category_des);
        img=findViewById(R.id.add_category_img);
        btnAdd=findViewById(R.id.add_category_btnAdd);
    }
}