package com.example.librarymanageapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librarymanageapp.DetailBillActivity;
import com.example.librarymanageapp.HistoryBillActivity;
import com.example.librarymanageapp.R;
import com.example.librarymanageapp.adapter.HistoryAdapter;
import com.example.librarymanageapp.adapter.NewsAdapter;
import com.example.librarymanageapp.model.Bill;
import com.example.librarymanageapp.model.News;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment implements HistoryAdapter.ItemListener{
    RecyclerView recyclerView;
    List<Bill> list;
    NewsAdapter adapter;
    HistoryAdapter adapterhistory;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.recycleview_history_bill);
        list=new ArrayList<>();
        adapter=new NewsAdapter();
        adapterhistory=new HistoryAdapter();

        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("bill");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    Bill b = dataSnapshot1.getValue(Bill.class);
                    if(b.getEmail().equals(user.getEmail())){
                        list.add(b);
                    }
                }
                adapterhistory.setList(list);
                adapterhistory.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterhistory);
        //
        adapterhistory.setItemListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Bill b=adapterhistory.getItem(position);
        Intent intent=new Intent(getContext(), DetailBillActivity.class);
        intent.putExtra("bill", b);
        startActivity(intent);
    }
}
