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

import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    // 이메일 로그인 변수
    private EditText email, password;
    private Button buttonLogin, buttonSignIn;
    // 파이어베이스 객체
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        // 바인딩
        email = findViewById( R.id.login_email );
        password = findViewById( R.id.login_password );
        buttonLogin = findViewById( R.id.btn_login );
        buttonSignIn = findViewById( R.id.btn_sign_up );
        
        // 파이어베이스 인증객체
        firebaseAuth = FirebaseAuth.getInstance(); 
        
        if (firebaseAuth.getCurrentUser() != null) {
            Toast.makeText(LoginActivity.this, "환영합니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplication(), MainActivity.class);
            startActivity(intent);
            finish();
        } //현재 로그인 상태를 확인하고 로그인 중이면 Main으로 화면전환

        buttonSignIn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change Screen SignUpActivity
                Intent intent = new Intent( com.example.deitem_login.LoginActivity.this, com.example.deitem_login.SignUpActivity.class );
                startActivity( intent );
            }
        }); //회원가입 Event Listener

        buttonLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (password.length()<8) { //8이하의 비밀번호
                    Toast.makeText(LoginActivity.this, R.string.login_error_pwlength, Toast.LENGTH_SHORT).show();
                    password.getText().clear();
                    return;
                }
                String userEmail = email.getText().toString().trim();
                String userPassword = password.getText().toString().trim();

                firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    Toast.makeText(LoginActivity.this, R.string.login_suc , Toast.LENGTH_SHORT).show();
                                    updateUI(user);
                                } else {
                                    Toast.makeText(LoginActivity.this, R.string.login_error, Toast.LENGTH_SHORT).show();
                                    password.getText().clear();
                                }
                            }
                });
            }
        }); // 로그인 Event Listener

    }

    private void updateUI(FirebaseUser user) { //update ui code here
        if (user != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    } //UI 업데이트
}
