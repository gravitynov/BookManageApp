package com.example.librarymanageapp.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.librarymanageapp.InfoActivity;
import com.example.librarymanageapp.LoginActivity;
import com.example.librarymanageapp.R;
import com.example.librarymanageapp.model.Category;
import com.example.librarymanageapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ManageFragment extends Fragment {
    ImageView img;
    TextView name, email;
    Button btnInfo, btnHand, btnchangepass, btnlogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String uid=user.getUid().toString();
        //getuser
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    User user = dataSnapshot1.getValue(User.class);
                    if(user.getUid().equals(uid)){
                        email.setText(user.getEmail().toString());
                        name.setText(user.getName().toString());
                        Picasso.get().load(user.getImage()).into(img);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent t=new Intent(getContext(), LoginActivity.class);
                startActivity(t);
            }
        });
        btnchangepass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                clickChangePass();
            }

            private void clickChangePass() {
                View viewDialog=getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_change_pass, null);

                BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(getContext());
                bottomSheetDialog.setContentView(viewDialog);
                bottomSheetDialog.show();
                //init
                EditText old_passs, new_pass;
                old_passs=viewDialog.findViewById(R.id.olf_password);
                new_pass=viewDialog.findViewById(R.id.new_password);
                String o=old_passs.getText().toString();
                String n=new_pass.getText().toString();
                Button btnChance=viewDialog.findViewById(R.id.btnChangPass);
                btnChance.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        changePass();
                    }

                    private void changePass() {
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        String userEmail = "buikhacphong@gmail.com";

                        auth.sendPasswordResetEmail(userEmail)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getContext(), "Check Email", Toast.LENGTH_SHORT).show();
                                        } else {
                                            // An error occurred while sending the password reset email.
                                        }
                                    }
                                });
                    }
                });
            }
        });
        btnHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), InfoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView(View view) {
        img=view.findViewById(R.id.user_image);
        name=view.findViewById(R.id.user_name);
        email=view.findViewById(R.id.user_email);
        btnInfo=view.findViewById(R.id.btn_thongtin);
        btnHand=view.findViewById(R.id.btn_vanmtay);
        btnchangepass=view.findViewById(R.id.btn_doimatkhau);
        btnlogout=view.findViewById(R.id.btn_dangxuat);


    }


}
