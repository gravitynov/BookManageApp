package com.example.librarymanageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.librarymanageapp.adapter.CommentAdapter;
import com.example.librarymanageapp.model.Book;
import com.example.librarymanageapp.model.Comment;
import com.example.librarymanageapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class BookDetailActivity extends AppCompatActivity {
    ImageView imageView;
    TextView name, theloai, tacgia, gia, mota;
    EditText edtcomment;
    Button edit,cart, btncomment;
    RecyclerView recyclerView;
    CommentAdapter adapter;
    ArrayList<Comment>list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        initview();
        Intent t=getIntent();

        Book b= (Book) t.getSerializableExtra("book");
        name.setText(b.getName().toString());
        tacgia.setText("Tac gia: "+b.getAuthor().toString());
        gia.setText("Gia: "+b.getPrice().toString());
        mota.setText(b.getDes().toString());
        theloai.setText("NXB: "+b.getNxb().toString());
        Picasso.get().load(b.getImg()).into(imageView);

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user.getEmail().equals("phong@gmail.com")==false){
            edit.setEnabled(false);
            cart.setEnabled(false);
        }
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BookDetailActivity.this, EditBookActivity.class);
                intent.putExtra("editbook", b);
                startActivity(intent);
                finish();
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase db=FirebaseDatabase.getInstance();
                DatabaseReference ref=db.getReference("book");
                ref.child(b.getId()).child("cart").setValue("true");
                Toast.makeText(BookDetailActivity.this, "Them thanh cong vao cart", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        DatabaseReference ref1=FirebaseDatabase.getInstance().getReference("comment");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    Comment c = dataSnapshot1.getValue(Comment.class);
                    if(c.getBid().equals(b.getId())){
                        list.add(c);
                    }
                }
                adapter.setList(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL, false );
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        btncomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commment=edtcomment.getText().toString().trim();
                FirebaseAuth auth=FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                String uid=user.getUid().toString();
                //getuser
                DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                            User user = dataSnapshot1.getValue(User.class);
                            if(user.getUid().equals(uid)){
                                String name=user.getName();
                                String img=user.getImage();
                                //firebase
                                FirebaseDatabase db=FirebaseDatabase.getInstance();
                                DatabaseReference ref=db.getReference("comment");

                                String id=ref.push().getKey();
                                HashMap<String, String> hs=new HashMap<>();
                                //id, String name, String author, String des, String price, String nxb, String img, String catID
                                hs.put("id", id);
                                hs.put("uid", name);//uid thuc chat la truyen name
                                hs.put("bid", b.getId());
                                hs.put("comment",commment);
                                hs.put("img", img);
                                //firebase
                                ref.child(id).setValue(hs);
                                Toast.makeText(BookDetailActivity.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                                finish();
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void initview() {
        imageView=findViewById(R.id.bottom_sheet_book_image);
        name=findViewById(R.id.bottom_sheet_book_name);
        theloai=findViewById(R.id.bottom_sheet_book_type);
        tacgia=findViewById(R.id.bottom_sheet_book_author);
        gia=findViewById(R.id.bottom_sheet_book_price);
        mota=findViewById(R.id.book_detail_des);
        edit=findViewById(R.id.edit_book_detail);
        cart=findViewById(R.id.add_book_to_cart);
        edtcomment=findViewById(R.id.text_comment);
        btncomment=findViewById(R.id.btn_comment);
        recyclerView=findViewById(R.id.recycleview_book_comment);
        adapter=new CommentAdapter();
        list=new ArrayList<>();
    }
}