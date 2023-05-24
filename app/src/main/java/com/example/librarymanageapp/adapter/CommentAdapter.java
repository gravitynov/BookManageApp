package com.example.librarymanageapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librarymanageapp.R;
import com.example.librarymanageapp.model.Book;
import com.example.librarymanageapp.model.Comment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private BookAdapter.ItemListener itemListener;
    private List<Comment> list;

    public CommentAdapter() {
        list=new ArrayList<>();
    }

    public void setItemListener(BookAdapter.ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setList(List<Comment> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,parent,false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment c=list.get(position);
        holder.name.setText(c.getUid());
        holder.text.setText(c.getComment());
        Picasso.get().load(c.getImg()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img;
        TextView name, text;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.item_comment_image);
            name=itemView.findViewById(R.id.item_comment_name);
            text=itemView.findViewById(R.id.item_comment_text);

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
