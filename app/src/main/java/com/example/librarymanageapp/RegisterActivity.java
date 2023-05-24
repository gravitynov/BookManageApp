package com.example.librarymanageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    EditText edtName, edtEmail, edtPass, edtAddress, edtphone;
    Button btnRegister, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initview();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=edtName.getText().toString().trim();
                String email=edtEmail.getText().toString().trim();
                String password=edtPass.getText().toString().trim();
                String address=edtAddress.getText().toString().trim();
                String phone=edtphone.getText().toString().trim();
                //register
                FirebaseAuth auth=FirebaseAuth.getInstance();
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user=auth.getCurrentUser();
                                String mail=user.getEmail();
                                String uid=user.getUid();
                                HashMap<Object, String> users=new HashMap<>();
                                users.put("email", mail);
                                users.put("uid", uid);
                                users.put("name", name);
                                users.put("address", address);
                                users.put("phone", phone);
                                users.put("image", "https://th.bing.com/th/id/OIP.9SKfV5dAQ-SahY_Hau0W2AHaHa?pid=ImgDet&rs=1");

                                FirebaseDatabase db=FirebaseDatabase.getInstance();
                                DatabaseReference ref=db.getReference("Users");
                                ref.child(uid).setValue(users);
                                // Đăng ký thành công, hiển thị thông báo
                                Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                                Intent intent=new Intent(RegisterActivity.this, UserActivity.class);
                                startActivity(intent);
                                finishAffinity();
                            } else {
                                // Đăng ký thất bại, hiển thị thông báo lỗi
                                Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initview() {
        edtName=findViewById(R.id.register_activity_name);
        edtEmail=findViewById(R.id.register_activity_email);
        edtPass=findViewById(R.id.register_activity_password);
        edtAddress=findViewById(R.id.register_activity_address);
        edtphone=findViewById(R.id.register_activity_phone);
        btnRegister=findViewById(R.id.register_activity_register);
        btnCancel=findViewById(R.id.register_activity_cancel);
    }
}