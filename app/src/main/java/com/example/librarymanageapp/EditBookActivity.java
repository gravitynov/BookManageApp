package com.example.librarymanageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.librarymanageapp.fragment.BookFragment;
import com.example.librarymanageapp.model.Book;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditBookActivity extends AppCompatActivity {
    EditText name, author, price, nxb, des;
    Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);
        Intent t=getIntent();
        Book b= (Book) t.getSerializableExtra("editbook");
        intitview();
        name.setText(b.getName().toString());
        author.setText(b.getAuthor().toString());
        price.setText(b.getPrice().toString());
        des.setText(b.getDes().toString());
        nxb.setText(b.getNxb().toString());
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n=name.getText().toString().trim();
                String a=author.getText().toString().trim();
                String p=price.getText().toString().trim();
                String d=des.getText().toString().trim();
                String nx=nxb.getText().toString().trim();
                // setdl
                b.setName(n);
                b.setAuthor(a);
                b.setPrice(p);
                b.setNxb(nx);
                b.setDes(d);
                DatabaseReference ref=FirebaseDatabase.getInstance().getReference("book");
                ref.child(b.getId()).setValue(b).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditBookActivity.this, "Cap nhat thanh cong", Toast.LENGTH_SHORT).show();
//                        Intent t1=new Intent(EditBookActivity.this, BookFragment.class);
//                        startActivity(t1);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditBookActivity.this, "Cap nhat khong thanh cong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void intitview() {
        name=findViewById(R.id.update_book_name);
        author=findViewById(R.id.update_book_author);
        price=findViewById(R.id.update_book_price);
        nxb=findViewById(R.id.update_book_nxb);
        des=findViewById(R.id.update_book_des);
        btnUpdate=findViewById(R.id.update_book_update);

        //data

    }
}