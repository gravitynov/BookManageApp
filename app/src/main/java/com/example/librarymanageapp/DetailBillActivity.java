package com.example.librarymanageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.librarymanageapp.model.Bill;
import com.example.librarymanageapp.model.BillDetail;
import com.example.librarymanageapp.model.Book;
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

public class DetailBillActivity extends AppCompatActivity {
    TextView ma, ten, masv, lop, ngaymuon, ngaytra, danhsach;
    Button back, tra;
    List<BillDetail> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bill);
        initview();
        Intent t=getIntent();
        Bill b= (Bill) t.getSerializableExtra("bill");
        if(b.getReturned().equals(true)){
            tra.setEnabled(false);
        }
        ma.setText("Mã phiếu: "+b.getId());
        ten.setText("Tên SV: "+b.getNameU());
        masv.setText("Mã SV: "+b.getMsv());
        lop.setText("Lớp: "+b.getClassU());
        ngaymuon.setText("Ngày mượn: "+b.getSdate());
        ngaytra.setText("Ngày trả: "+b.geteDate());
        if(b.getReturned().equals("true")){
            tra.setEnabled(false);
        }
        //ds

        list=new ArrayList<>();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("billdetail");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int dem=1;
                String ds="";
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    BillDetail detail = dataSnapshot1.getValue(BillDetail.class);
                    if(detail.getBillID().equals(b.getId())){
                        ds+= String.valueOf(dem)+"."+detail.getBookName()+"\n";
                        dem+=1;
                    }
                }
                danhsach.setText(ds);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                if(user.getEmail().equals("phong@gmail.com")) {
                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference ref = db.getReference("bill");
                    ref.child(b.getId()).child("returned").setValue("true");
                    Calendar calendar = Calendar.getInstance();
                    String dat = calendar.getTime().toString();
                    ref.child(b.getId()).child("eDate").setValue(dat);
                    tra.setEnabled(false);
                    finish();
                }
            }
        });
    }

    private void initview() {
        ma=findViewById(R.id.ma_hoa_don);
        ten=findViewById(R.id.ten_sinh_vien);
        masv=findViewById(R.id.ma_sinh_vien);
        lop=findViewById(R.id.lop_sinh_vien);
        ngaymuon=findViewById(R.id.ngay_muon);
        ngaytra=findViewById(R.id.ngay_tra);
        danhsach=findViewById(R.id.ds_san_pham_phieu_muon);
        back=findViewById(R.id.back);
        tra=findViewById(R.id.tra);
    }
}