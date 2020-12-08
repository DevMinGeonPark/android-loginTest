package com.example.deitem_login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.deitem_login.UserInfo;

public class LoginActivity extends AppCompatActivity {

    // 이메일 로그인 변수
    private EditText email, password, phone;
    private Button buttonLogin, buttonSignIn;

    // 파이어베이스 데이터베이스 변수
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    
    // 구글 로그인 변수
    private final int RC_SIGN_IN = 9001;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;
    private SignInButton buttonGoogle;

    // 유저객체
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        // 바인딩
        email = findViewById( R.id.login_email );
        password = findViewById( R.id.login_password );
        phone = findViewById(R.id.sign_phone);
        buttonLogin = findViewById( R.id.btn_login );
        buttonSignIn = findViewById( R.id.btn_sign_up );
        buttonGoogle = findViewById( R.id.btn_googleSignIn );
        
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
                String userID = email.getText().toString().trim();
                String userPwd = password.getText().toString().trim();

                firebaseAuth.signInWithEmailAndPassword(userID, userPwd)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    Toast.makeText(LoginActivity.this, R.string.login_ok , Toast.LENGTH_SHORT).show();
                                    updateUI(user);
                                } else {
                                    Toast.makeText(LoginActivity.this, R.string.login_error, Toast.LENGTH_SHORT).show();
                                    password.getText().clear();
                                }
                            }
                });
            }
        }); // 로그인 Event Listener
        
        // 구글 로그인
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build(); //구글 로그인 코드로 토큰을 보내고 이메일 반환
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions); //해당 액티비티에 구글 클라이언트 가져오기
        
        buttonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        }); // 구글 로그인 Event Listener

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account); //인증실행
            } catch (ApiException e) {

            }
        }
    } // 구글 로그인 액티비티 실행, 이미 로그인되어있으면 생략되고 인증실행

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            //userInfo = new UserInfo(user.getUid(), user.getEmail(), user.getDisplayName(), "00000000","22");
                            Toast.makeText(LoginActivity.this, R.string.login_ok , Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            Toast.makeText(LoginActivity.this, R.string.login_fail , Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    } // 구글 인증

    private void updateUI(FirebaseUser user) { //update ui code here
        if (user != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    } //UI 업데이트
}
