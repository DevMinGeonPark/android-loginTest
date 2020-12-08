package com.example.deitem_login;

import android.content.Intent;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;

public class result {
    //oncreate in
    /*
    buttonGoogle = findViewById( R.id.btn_googleSignIn );
        // 구글 로그인 변수
    private final int RC_SIGN_IN = 9001;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;
    private SignInButton buttonGoogle;

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

    */


    /*
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

    // 사용자가 정상적으로 로그인한 후에 GoogleSignInAccount 개체에서 ID 토큰을 가져와서
    //Firebase 사용자 인증 정보로 교환하고 Firebase 사용자 인증 정보를 사용해 Firebase에 인증합니다.
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Log.d(user.getPhoneNumber(),"d");
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
    */
}
