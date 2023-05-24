package com.example.librarymanageapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.librarymanageapp.AddCategoryActivity;
import com.example.librarymanageapp.R;
import com.example.librarymanageapp.adapter.CategoryAdapter;
import com.example.librarymanageapp.model.Category;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoryFragment extends Fragment implements CategoryAdapter.ItemListener{
    RecyclerView recyclerView;
    CategoryAdapter adapter;
    SearchView searchView;
    FloatingActionButton fab;
    ArrayList<Category> list;
    ArrayList<Category> listFilter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.category_recycleview);
        searchView=view.findViewById(R.id.category_search);
        fab=view.findViewById(R.id.category_add);

        //init data

        adapter=new CategoryAdapter();
        list=new ArrayList();
        listFilter=new ArrayList<>();
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("category");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    System.out.println("Vao day 1");
                    Category category = dataSnapshot1.getValue(Category.class);
                    list.add(category);
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


        //Floating action Button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getContext(), AddCategoryActivity.class);
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

    private List<Category> getListcategory() {
        ArrayList<Category> cList=new ArrayList<>();
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("category");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    System.out.println("Vao day 1");
                    Category category = dataSnapshot1.getValue(Category.class);
                    cList.add(category);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return cList;
    }

    @Override
    public void onItemClick(View view, int position) {
        Category c=adapter.getCategory(position);

        View viewDialog=getLayoutInflater().inflate(R.layout.bottom_sheet_edit_category, null);

        final BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(viewDialog);
        bottomSheetDialog.show();


        //init data bottom sheet
        EditText edtname, edtdes, edtimage;
        edtname=viewDialog.findViewById(R.id.bottom_sheet_name);
        edtdes=viewDialog.findViewById(R.id.bottom_sheet_des);
        edtimage=viewDialog.findViewById(R.id.bottom_sheet_image);
        String n=c.getName();
        String des=c.getDes();
        String i=c.getImg();
        edtdes.setText(des);
        edtname.setText(n);
        edtimage.setText(i);
        Button btnUpdate=viewDialog.findViewById(R.id.bottom_sheet_update);
        Button btnCancel=viewDialog.findViewById(R.id.bottom_sheet_cancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Category category=new Category(c.getId(), edtimage.getText().toString().trim(),
                        edtname.getText().toString().trim(),edtdes.getText().toString().trim());
                // update db
                DatabaseReference ref=FirebaseDatabase.getInstance().getReference("category");
                ref.child(c.getId()).setValue(category).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Cap nhat thanh cong", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Cap nhat khong thanh cong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        list.clear();
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("category");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    System.out.println("Vao day 1");
                    Category category = dataSnapshot1.getValue(Category.class);
                    list.add(category);
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
