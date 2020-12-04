package com.example.deitem_login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.deitem_login.GoogleLoginActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.auth.User;


public class LoginActivity extends AppCompatActivity {

    //Email 로그인 변수
    private EditText email, password; //아이디 비밀번호
    private Button buttonLogin, ButtonSignIn; //로그인, 회원가입
    private GoogleLoginActivity googleLoginActivity;

    //Google 로그인 변수
    private static final int RC_SIGN_IN = 9001;
    //구글 api 클라이언트
    private GoogleSignInClient googleSignInClient;
    // 파이어베이스 인증 객체
    private FirebaseAuth firebaseAuth;
    //구글 로그인버튼
    private SignInButton buttonGoogle;


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

//        if (firebaseAuth.getCurrentUser() != null) {
//            Intent intent = new Intent(getApplication(), MainActivity.class);
//            startActivity(intent);
//            finish();
//        }

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
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName("박민건")
                                            .build();
                                    Toast.makeText(LoginActivity.this, R.string.login_ok , Toast.LENGTH_SHORT).show();
                                    updateUI(user);
                                } else {
                                    Toast.makeText(LoginActivity.this, R.string.login_error, Toast.LENGTH_SHORT).show();
                                    password.getText().clear();
                                }
                            }
                });
            }
        }); // 로그인 event listener

        //Google Sign-In Code

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        buttonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, R.string.login_ok , Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            Toast.makeText(LoginActivity.this, R.string.login_fail , Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) { //update ui code here
        if (user != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}