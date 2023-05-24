package com.example.librarymanageapp.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.librarymanageapp.InfoActivity;
import com.example.librarymanageapp.R;
import com.example.librarymanageapp.adapter.NewsAdapter;
import com.example.librarymanageapp.model.News;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ConnectFragment extends Fragment {
    Button btnCall, btnMess, btnTT;
    RecyclerView recyclerView;
    NewsAdapter adapter;
    List<News> list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_connect, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnCall=view.findViewById(R.id.btnCall);
        btnMess=view.findViewById(R.id.btnMess);
        btnTT=view.findViewById(R.id.btn_thongtin);
        btnTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), InfoActivity.class);
                startActivity(intent);
            }
        });
        recyclerView=view.findViewById(R.id.recycleview_baiviet);
        list=new ArrayList<>();
        adapter=new NewsAdapter();

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("news");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    System.out.println("Vao day 1");
                    News news = dataSnapshot1.getValue(News.class);
                    list.add(news);
                }
                adapter.setList(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ "0978137372"));
                if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},1);
                    return;
                }
                startActivity(t);
            }
        });
        btnMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent messIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+"0978137372"));
                    startActivity(messIntent);
            }
        });
    }
}
