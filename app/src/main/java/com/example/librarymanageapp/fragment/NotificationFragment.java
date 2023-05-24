package com.example.librarymanageapp.fragment;


import android.content.Intent;
import java.time.LocalDateTime;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librarymanageapp.AddBookActivity;
import com.example.librarymanageapp.BookDetailActivity;
import com.example.librarymanageapp.HistoryBillActivity;
import com.example.librarymanageapp.R;
import com.example.librarymanageapp.adapter.CartAdapter;
import com.example.librarymanageapp.model.Book;
import com.example.librarymanageapp.model.Category;
import com.example.librarymanageapp.model.User;
import com.example.librarymanageapp.model.cart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class NotificationFragment extends Fragment implements CartAdapter.ItemListener{
    RecyclerView recyclerView;
    Button btnMuon;
    CartAdapter adapter;
    List<Book> list;
    List<String> listidBook;
    EditText name, lop, msv, email;
    ImageView history;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.cart_recycleview);
        btnMuon=view.findViewById(R.id.cart_borrow);
        name=view.findViewById(R.id.cart_user_name);
        lop=view.findViewById(R.id.cart_user_class);
        msv=view.findViewById(R.id.cart_user_msv);
        history=view.findViewById(R.id.lichsu);
        email=view.findViewById(R.id.cart_user_email);
        adapter=new CartAdapter();
        list=new ArrayList<>();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("book");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    Book book = dataSnapshot1.getValue(Book.class);
                    if(book.getCart().equals("true")){
                        list.add(book);
                        btnMuon.setEnabled(true);
                    }
                }
                adapter.setList(list);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        //
        btnMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n=name.getText().toString().trim();
                String l=lop.getText().toString().trim();
                String m=msv.getText().toString().trim();
                String e=email.getText().toString().trim();
                name.setText("");
                lop.setText("");
                msv.setText("");
                email.setText("");
                //them bill
                FirebaseDatabase db=FirebaseDatabase.getInstance();
                DatabaseReference ref=db.getReference("bill");
                String id=ref.push().getKey();
                HashMap<String, String> hs=new HashMap<>();
                //id, String name, String author, String des, String price, String nxb, String img, String catID
                hs.put("id", id);
                hs.put("nameU", n);
                hs.put("classU", l);
                hs.put("msv", m);
                hs.put("email", e);
                Calendar calendar=Calendar.getInstance();
                String dat=calendar.getTime().toString();
                hs.put("sdate", dat);
                hs.put("returned", "false");
                hs.put("edate", "");
                //firebase
                ref.child(id).setValue(hs);
                Toast.makeText(getContext(), "Them thanh cong", Toast.LENGTH_SHORT).show();

                //them ds vao bill
                thembangtrunggian(id);


            }

            private void xoaList(String idbook) {
                FirebaseDatabase db=FirebaseDatabase.getInstance();
                DatabaseReference ref=db.getReference("book");
                ref.child(idbook).child("cart").setValue("false");
                Toast.makeText(getContext(), "Xoa thanh cong khoi cart", Toast.LENGTH_SHORT).show();
            }

            private void thembangtrunggian(String id) {
                FirebaseDatabase db1=FirebaseDatabase.getInstance();
                DatabaseReference ref1=db1.getReference("billdetail");
                for(int t=0;t<list.size();t++){
                    HashMap<String, String> hs1=new HashMap<>();
                    String id1=ref1.push().getKey();
                    hs1.put("billID", id);
                    hs1.put("id", id1);
                    hs1.put("book", list.get(t).getId());
                    hs1.put("bookName", list.get(t).getName());
                    ref1.child(id1).setValue(hs1);
                    Toast.makeText(getContext(), "Them thanh cong", Toast.LENGTH_SHORT).show();
                    xoaList(list.get(t).getId());
                }
                list.clear();
                adapter.setList(list);
                adapter.notifyDataSetChanged();
                btnMuon.setEnabled(false);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), HistoryBillActivity.class);
                startActivity(intent);
            }
        });

        adapter.setItemListener(this);
    }



    @Override
    public void onItemClick(View view, int position) {

    }
}
