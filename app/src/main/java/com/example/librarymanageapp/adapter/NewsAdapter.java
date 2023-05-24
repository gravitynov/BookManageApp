package com.example.librarymanageapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librarymanageapp.R;
import com.example.librarymanageapp.model.News;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{
    private ItemListener itemListener;
    private List<News> list;

    public NewsAdapter() {
        list=new ArrayList<>();
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setList(List<News> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,parent,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News news=list.get(position);
        String t=news.getTitle().toString().trim();
        if(news.getTitle().length()>50){
            t=news.getTitle().substring(0,50)+"...";
        }
        String d=news.getDes().toString().trim();
        if(news.getDes().length()>100){
            d=news.getDes().substring(0,100)+"...";
        }
        holder.title.setText(t);
        holder.des.setText(d);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img;
        TextView title, des;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.item_news_image);
            title=itemView.findViewById(R.id.item_news_title);
            des=itemView.findViewById(R.id.item_news_des);

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
