package com.example.librarymanageapp.adapter;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librarymanageapp.R;
import com.example.librarymanageapp.model.Category;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private ItemListener itemListener;
    private List<Category> list;

    public CategoryAdapter() {
        list=new ArrayList<>();
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setList(List<Category> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Category getCategory(int position){
        return list.get(position);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category c=list.get(position);
        holder.name.setText(c.getName());
        Picasso.get().load(c.getImg()).into(holder.img);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(view.getContext());
                builder.setTitle("Thong bao xoa");
                builder.setMessage("Ban co chac muoon xoa '"+ c.getName()+"' khong?");
                builder.setIcon(R.drawable.baseline_delete_forever_24);
                builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list.remove(c);
                        notifyDataSetChanged();
                        // delete
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("category");
                        // Remove the data
                        ref.child(c.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Data removed successfully
                                    Toast.makeText(view.getContext(), "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                                } else {
                                    // An error occurred while removing data
                                    Toast.makeText(view.getContext(), "Xoa khong thanh cong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img, delete, update;
        TextView name;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.item_category_img);
            name=itemView.findViewById(R.id.item_category_name);
            update=itemView.findViewById(R.id.item_category_edit);
            delete=itemView.findViewById(R.id.item_category_delete);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemListener!=null){
                itemListener.onItemClick(view,getAdapterPosition());
            }
        }
    }
    public interface ItemListener{
        void onItemClick(View view, int position);
    }
}
