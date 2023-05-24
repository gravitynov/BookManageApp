package com.example.librarymanageapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librarymanageapp.AddNewsActivity;
import com.example.librarymanageapp.NewDetailActivity;
import com.example.librarymanageapp.R;
import com.example.librarymanageapp.adapter.NewsAdapter;
import com.example.librarymanageapp.model.Category;
import com.example.librarymanageapp.model.News;
import com.example.librarymanageapp.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class NewsFragment extends Fragment implements NewsAdapter.ItemListener{
    RecyclerView recyclerView;
    EditText add;
    List<News> list;
    NewsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.news_recycleview);
        add=view.findViewById(R.id.news_add);
        //init
        adapter=new NewsAdapter();
        list=new ArrayList<>();
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

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        //
        adapter.setItemListener(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), AddNewsActivity.class);
                startActivity(intent);
            }
        });
    }



    @Override
    public void onItemClick(View view, int position) {
        News news=list.get(position);
        Intent t=new Intent(getContext(), NewDetailActivity.class);
        t.putExtra("news", news);
        startActivity(t);
    }


    @Override
    public void onResume() {
        super.onResume();
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
    }
}
