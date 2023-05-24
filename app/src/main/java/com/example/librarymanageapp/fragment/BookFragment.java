package com.example.librarymanageapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librarymanageapp.AddBookActivity;
import com.example.librarymanageapp.BookDetailActivity;
import com.example.librarymanageapp.R;
import com.example.librarymanageapp.adapter.BookAdapter;
import com.example.librarymanageapp.model.Book;
import com.example.librarymanageapp.model.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BookFragment extends Fragment implements BookAdapter.ItemListener{
    SearchView searchView;
    RecyclerView recyclerView;
    Spinner spinner;
    FloatingActionButton fab;
    BookAdapter adapter;
    ArrayList<Book> list;
    ArrayList<Book> listFilter;
    ArrayList<Category> list1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.book_recycleview);
        searchView=view.findViewById(R.id.book_search);
        fab=view.findViewById(R.id.book_add);
//        spinner=view.findViewById(R.id.spinner_catrgory);
        //init data
        initView();

        //init data recycleview
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("book");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    System.out.println("Vao day getbook o oncreate");
                    Book book = dataSnapshot1.getValue(Book.class);
                    list.add(book);
                }
                adapter.setList(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        //
        adapter.setItemListener(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), AddBookActivity.class);
                startActivity(intent);
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
                    if(list.get(j).getName().toLowerCase().contains(newText.toLowerCase())){
                        listFilter.add(list.get(j));
                    }
                }
                adapter.setList(listFilter);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private void initView() {
        adapter=new BookAdapter();
        list=new ArrayList<>();
        list1=new ArrayList<>();
        listFilter=new ArrayList<>();
    }


    @Override
    public void onItemClick(View view, int position) {
        Book book =adapter.getItem(position);
        Intent intent=new Intent(getContext(), BookDetailActivity.class);
        intent.putExtra("book", book);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        //get category
        list1.clear();
        DatabaseReference ref1=FirebaseDatabase.getInstance().getReference("book");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    System.out.println("Vao day getbook");
                    Book book = dataSnapshot1.getValue(Book.class);
                    list.add(book);
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
