package com.example.librarymanageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button btnlogin;
    TextView txtRegister;
    CheckBox ckAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initData();
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emails=username.getText().toString().trim();
                String pass=password.getText().toString().trim();
                FirebaseAuth auth=FirebaseAuth.getInstance();
                auth.signInWithEmailAndPassword(emails, pass)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Xác thực đăng nhập thành công, hiển thị thông báo
                                    FirebaseUser user = auth.getCurrentUser();

                                    if(ckAdmin.isChecked()){
                                        if(user.getEmail().equals("phong@gmail.com")){
                                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công",
                                                    Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finishAffinity();
                                        }
                                        else{
                                            Toast.makeText(LoginActivity.this, "khong phai tai khoan admin",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else{
                                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(LoginActivity.this, UserActivity.class);
                                        startActivity(intent);
                                        finishAffinity();
                                    }

                                } else {
                                    // Xác thực đăng nhập thất bại, hiển thị thông báo lỗi
                                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        username=findViewById(R.id.activity_login_edittext_username);
        password=findViewById(R.id.activity_login_edittext_password);
        btnlogin=findViewById(R.id.activity_login_button_login);
        txtRegister=findViewById(R.id.activity_login_register);
        ckAdmin=findViewById(R.id.checkbox_admin);
    }
}