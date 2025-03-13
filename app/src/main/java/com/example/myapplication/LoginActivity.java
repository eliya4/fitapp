package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends Activity {
    private FirebaseAuth mAuth;
    private Button loginb;
    private TextView creatb;
    private EditText emailEditText,passwordEditText;
//שיטה זו מופעלת כאשר האקטיביטי נטען. היא מבטיחה שהאקטיביטי מופעל כראוי, ומאפשרת לשמור ולהחזיר מידע מהמצב הקודם של האפליקציה.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        mAuth=FirebaseAuth.getInstance();//שמאפשר לבצע פעולות כמו התחברות, הרשמה, והתנתקות.
        loginb=findViewById(R.id.loginButton);
        creatb=findViewById(R.id.createAccountLink);///שי למה זה ככה?
        emailEditText=findViewById(R.id.emailField);
        passwordEditText=findViewById(R.id.passwordField);
        creatb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(LoginActivity.this, AddAccount.class);
                startActivity(intent);

            }
        });
        loginb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= emailEditText.getText().toString().trim();
                String password= passwordEditText.getText().toString().trim();
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this,"Log in succesfuly!",Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
