package com.example.deitem_login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import com.example.deitem_login.GoogleLoginActivity;



public class LoginActivity extends AppCompatActivity {

    private EditText email, password; //아이디 비밀번호
    private Button buttonLogin, ButtonSignIn; //로그인, 회원가입
    private SignInButton buttonGoogle;
    
    
    FirebaseAuth firebaseAuth; //파이어베이스 인증객체

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        email = findViewById( R.id.login_emain );
        password = findViewById( R.id.login_password );
        buttonLogin = findViewById( R.id.btn_login );
        ButtonSignIn = findViewById( R.id.btn_sign_up );
        buttonGoogle = findViewById( R.id.btn_googleSignIn );

        firebaseAuth = FirebaseAuth.getInstance(); //파이어베이스 인증 인스턴스

        ButtonSignIn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) { //change screen register intent
                Intent intent = new Intent( com.example.deitem_login.LoginActivity.this, com.example.deitem_login.SignUpActivity.class );
                startActivity( intent );
            }
        }); //회원가입 event listener

        buttonLogin.setOnClickListener( new View.OnClickListener() {
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
        }); // 로그인 event listener

        buttonGoogle.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "임시 테스트", Toast.LENGTH_SHORT).show();
            }
        });

    }
}