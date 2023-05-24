package com.example.librarymanageapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librarymanageapp.BookDetailActivity;
import com.example.librarymanageapp.LoginActivity;
import com.example.librarymanageapp.R;
import com.example.librarymanageapp.adapter.BookUserAdapter;
import com.example.librarymanageapp.adapter.CategoryUserAdapter;
import com.example.librarymanageapp.model.Book;
import com.example.librarymanageapp.model.Category;
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

public class HomeFragment extends Fragment implements CategoryUserAdapter.ItemListener, BookUserAdapter.ItemListener{
    RecyclerView recyclerViewCategory, recyclerViewBook;
    ArrayList<Category> listCategory;
    ArrayList<Book> listBook;
    CategoryUserAdapter adapter;
    BookUserAdapter adapter2;
    TextView  email;
    ImageView imageView, logout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewCategory=view.findViewById(R.id.home_user_recyclye_category);
        recyclerViewBook=view.findViewById(R.id.home_user_recyclye_book);
        listCategory=new ArrayList<>();
        listBook=new ArrayList<>();
        adapter=new CategoryUserAdapter();
        adapter2=new BookUserAdapter();
        //tt
        email=view.findViewById(R.id.user_email);
        imageView=view.findViewById(R.id.user_image);
        logout=view.findViewById(R.id.home_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent t=new Intent(getContext(), LoginActivity.class);
                startActivity(t);
            }
        });

        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String uid=user.getUid().toString();
        DatabaseReference ref2= FirebaseDatabase.getInstance().getReference("Users");
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    User user = dataSnapshot1.getValue(User.class);
                    if(user.getUid().equals(uid)){
                        email.setText(user.getEmail().toString());
                        Picasso.get().load(user.getImage()).into(imageView);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("category");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listCategory.clear();
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    Category c = dataSnapshot1.getValue(Category.class);
                    listCategory.add(c);
                }
                adapter.setList(listCategory);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference ref1= FirebaseDatabase.getInstance().getReference("book");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listBook.clear();
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    Book b = dataSnapshot1.getValue(Book.class);
                    listBook.add(b);
                }
                adapter2.setList(listBook);
                adapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        GridLayoutManager manager1 = new GridLayoutManager(getContext(),2);
        recyclerViewBook.setLayoutManager(manager1);
        recyclerViewBook.setAdapter(adapter2);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategory.setLayoutManager(manager);
        recyclerViewCategory.setAdapter(adapter);
        //
        adapter.setItemListener(this);
        adapter2.setItemListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Category category=adapter.getCategory(position);

        DatabaseReference ref1= FirebaseDatabase.getInstance().getReference("book");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listBook.clear();
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    Book b = dataSnapshot1.getValue(Book.class);
                    if(b.getCategoryID().equals(category.getId())){
                        listBook.add(b);
                    }
                }
                adapter2.setList(listBook);
                adapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClick2(View view, int position) {
        Book book =adapter2.getItem(position);
        Intent intent=new Intent(getContext(), BookDetailActivity.class);
        intent.putExtra("book", book);
        startActivity(intent);
    }
}
