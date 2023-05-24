package com.example.librarymanageapp.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librarymanageapp.R;
import com.example.librarymanageapp.model.Bill;
import com.example.librarymanageapp.model.BillDetail;
import com.example.librarymanageapp.model.Book;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>{
    private ItemListener itemListener;
    private List<Bill> list;

    public HistoryAdapter() {
        list=new ArrayList<>();
    }


    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history,parent,false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        Bill b=list.get(position);
        String ds="Xem them";
        holder.ma.setText("Ma phieu muon: "+b.getId());
        holder.name.setText("Ten sinh vien: "+b.getNameU());
        holder.msv.setText("Max SV: "+b.getMsv());
        holder.lop.setText("Lop: "+b.getClassU());
        holder.ngaymuon.setText("Ngay muon: "+b.getSdate());
        holder.dssach.setText(ds);

        if(b.getReturned().equals("true")){
            holder.btnDatra.setEnabled(false);
        }else {
            holder.btnDatra.setEnabled(true);
        }

        holder.btnDatra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                if(user.getEmail().equals("phong@gmail.com")){
                    FirebaseDatabase db=FirebaseDatabase.getInstance();
                    DatabaseReference ref=db.getReference("bill");
                    ref.child(b.getId()).child("returned").setValue("true");
                    Calendar calendar=Calendar.getInstance();
                    String dat=calendar.getTime().toString();
                    ref.child(b.getId()).child("eDate").setValue(dat);
                    holder.btnDatra.setEnabled(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setList(List<Bill> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public Bill getItem(int positon){
        return list.get(positon);
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView ma, name, msv, lop, ngaymuon, dssach;
        Button btnDatra;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ma=itemView.findViewById(R.id.history_id);
            name=itemView.findViewById(R.id.history_name);
            msv=itemView.findViewById(R.id.history_msv);
            lop=itemView.findViewById(R.id.history_lop);
            ngaymuon=itemView.findViewById(R.id.history_sdate);
            dssach=itemView.findViewById(R.id.history_list);
            btnDatra=itemView.findViewById(R.id.history_returned);

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
