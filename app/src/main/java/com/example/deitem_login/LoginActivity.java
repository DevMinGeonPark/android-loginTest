package com.example.deitem_login;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    private EditText email, password; //input text
    private Button btn_login, btn_sign_up; //button event
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //main
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        email = findViewById( R.id.login_emain ); //id object
        password = findViewById( R.id.login_password ); //pw object
        btn_login = findViewById( R.id.btn_login );
        btn_sign_up = findViewById( R.id.btn_sign_up ); //btn object

        firebaseAuth = FirebaseAuth.getInstance();

        btn_sign_up.setOnClickListener( new View.OnClickListener() { // sign_up
            @Override
            public void onClick(View view) { //change screen register intent
                Intent intent = new Intent( com.example.deitem_login.LoginActivity.this, com.example.deitem_login.SignUpActivity.class );
                startActivity( intent );
            }
        });

        btn_login.setOnClickListener( new View.OnClickListener() { // Login
            @Override
            public void onClick(View view) {
                if (password.length()<8) { //8이하의 비밀번호
                    Toast.makeText(LoginActivity.this, "비밀번호는 8자 이상만 가능합니다.", Toast.LENGTH_SHORT).show();
                    password.getText().clear();
                    return;
                }
                String userID = email.getText().toString().trim();
                String userPwd = password.getText().toString().trim();

                firebaseAuth.signInWithEmailAndPassword(userID, userPwd)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "로그인 성공!.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(LoginActivity.this, "아이디 혹은 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                                    password.getText().clear();
                                }
                            }
                });
            }
        });
    }
}