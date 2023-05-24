package com.example.librarymanageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.librarymanageapp.adapter.HistoryAdapter;
import com.example.librarymanageapp.model.Bill;
import com.example.librarymanageapp.model.BillDetail;
import com.example.librarymanageapp.model.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryBillActivity extends AppCompatActivity implements HistoryAdapter.ItemListener{
    RecyclerView recyclerView;
    SearchView searchView;
    HistoryAdapter adapter;
    List<Bill> list;
    List <Bill> listFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_bill);
        recyclerView=findViewById(R.id.history_recycleview);
        searchView=findViewById(R.id.history_search_bill);
        adapter=new HistoryAdapter();
        list=new ArrayList<>();
        listFilter=new ArrayList<>();
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("bill");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    Bill bill = dataSnapshot1.getValue(Bill.class);
                    list.add(bill);
                }
                adapter.setList(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listFilter.clear();
                for(int j=0;j<list.size();j++){
                    if(list.get(j).getMsv().toLowerCase().equals(newText.toLowerCase())){
                        listFilter.add(list.get(j));
                    }
                }
                adapter.setList(listFilter);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        //
        adapter.setItemListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("bill");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    System.out.println("Vao day getbook o oncreate");
                    Bill bill = dataSnapshot1.getValue(Bill.class);
                    list.add(bill);
                }
                adapter.setList(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Bill b=adapter.getItem(position);
        Intent intent=new Intent(HistoryBillActivity.this, DetailBillActivity.class);
        intent.putExtra("bill", b);
        startActivity(intent);
    }
}