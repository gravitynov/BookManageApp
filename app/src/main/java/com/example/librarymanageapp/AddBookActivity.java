package com.example.librarymanageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.librarymanageapp.model.Category;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddBookActivity extends AppCompatActivity {
    Spinner spinner;
    EditText name, des, author, price , img, nxb;
    Button btnAdd, btnCancel;
    List<Category> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        initview();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("category");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    System.out.println("Vao day 1");
                    Category category = dataSnapshot1.getValue(Category.class);
                    list.add(category);
                }
                System.out.println("-----------------------------------\nList Size: "+list.size());
                List<String> nameCat=new ArrayList<>();
                String [] listCategory=new String[list.size()];
                for(int i=0;i<list.size();i++){
                    nameCat.add(list.get(i).getName());
                }
                nameCat.toArray(listCategory);
                ArrayAdapter<String> adapterCat=new ArrayAdapter<>(getApplicationContext(), R.layout.item_spinner,listCategory);
                spinner.setAdapter(adapterCat);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase db=FirebaseDatabase.getInstance();
                DatabaseReference ref=db.getReference("book");
                //get data from edittext and spinner
                String n=name.getText().toString().trim();
                String d=des.getText().toString().trim();
                String i=img.getText().toString().trim();
                String a=author.getText().toString().trim();
                String p=price.getText().toString().trim();
                String category=spinner.getSelectedItem().toString().trim();
                String categoryID="";
                for(int k=0;k<list.size();k++){
                    if(list.get(k).getName().equals(category)){
                        categoryID=list.get(k).getId();
                    }
                }
                String nxban=nxb.getText().toString().trim();
                String id=ref.push().getKey();
                HashMap<String, String> hs=new HashMap<>();
                //id, String name, String author, String des, String price, String nxb, String img, String catID
                hs.put("id", id);
                hs.put("name", n);
                hs.put("author", a);
                hs.put("des", d);
                hs.put("price", p);
                hs.put("img", i);
                hs.put("nxb", nxban);
                hs.put("categoryID", categoryID);
                hs.put("cart", "false");
                //firebase

                ref.child(id).setValue(hs);
                Toast.makeText(AddBookActivity.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private List<Category> getCatList() {
        List<Category> list1=new ArrayList<>();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("category");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    System.out.println("Vao day 1");
                    Category category = dataSnapshot1.getValue(Category.class);
                    list1.add(category);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list1;
    }

    private void initview() {
        spinner=findViewById(R.id.spinnerCategory);
        name=findViewById(R.id.add_book_name);
        des=findViewById(R.id.add_book_des);
        author=findViewById(R.id.add_book_author);
        price=findViewById(R.id.add_book_price);
        img=findViewById(R.id.add_book_image);
        nxb=findViewById(R.id.add_book_nxb);
        btnAdd=findViewById(R.id.add_book);
        btnCancel=findViewById(R.id.add_book_cancel);
        list=new ArrayList<>();
        // init spinner

    }
}