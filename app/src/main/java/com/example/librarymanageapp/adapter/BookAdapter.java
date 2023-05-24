package com.example.librarymanageapp.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librarymanageapp.R;
import com.example.librarymanageapp.model.Book;
import com.example.librarymanageapp.model.Category;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{
    private ItemListener itemListener;
    private List<Book> list;

    public BookAdapter() {
        list=new ArrayList<>();
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setList(List<Book> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public Book getItem(int positon){
        return list.get(positon);
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview_book,parent,false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book b=list.get(position);
        holder.name.setText(b.getName());
        holder.author.setText(b.getAuthor());
        Picasso.get().load(b.getImg()).into(holder.img);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(view.getContext());
                builder.setTitle("Thong bao xoa");
                builder.setMessage("Ban co chac muoon xoa '"+ b.getName()+"' khong?");
                builder.setIcon(R.drawable.baseline_delete_forever_24);
                builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list.remove(b);
                        notifyDataSetChanged();
                        // delete
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("book");
                        // Remove the data
                        ref.child(b.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
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

    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img, delete;
        TextView name, author;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.item_book_image);
            name=itemView.findViewById(R.id.item_book_name);
            author=itemView.findViewById(R.id.item_book_author);
            delete=itemView.findViewById(R.id.item_book_delete);

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
