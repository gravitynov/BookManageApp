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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookUserAdapter extends RecyclerView.Adapter<BookUserAdapter.BookViewHolder>{
    private ItemListener itemListener;
    private List<Book> list;

    public BookUserAdapter() {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview_user_book,parent,false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book b=list.get(position);
        holder.name.setText(b.getName());
        holder.author.setText(b.getAuthor());
        Picasso.get().load(b.getImg()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img;
        TextView name, author;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.item_user_book_image);
            name=itemView.findViewById(R.id.item_user_book_name);
            author=itemView.findViewById(R.id.item_user_book_author);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemListener!=null){
                itemListener.onItemClick2(view,getAdapterPosition());
            }
        }
    }
    public interface ItemListener{
        void onItemClick2(View view, int position);
    }
}
