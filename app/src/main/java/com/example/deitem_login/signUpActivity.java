package com.example.deitem_login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firestore.v1.FirestoreGrpc;

import java.util.regex.Pattern;


public class SignUpActivity extends AppCompatActivity {

    private EditText sign_email , sign_password, sign_name, sign_phone;
    private Button singup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sign_email = (EditText) findViewById(R.id.sign_email);
        sign_password = (EditText) findViewById(R.id.sign_pwd);
        sign_name = (EditText) findViewById(R.id.sign_name);
        sign_phone = (EditText) findViewById(R.id.sign_phone);
        singup = (Button) findViewById(R.id.sign_up);

        FirebaseAuth firebaseAuth;

        firebaseAuth = FirebaseAuth.getInstance();

        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sign_password.length()<8) { //8이하의 비밀번호
                    Toast.makeText(SignUpActivity.this, "비밀번호는 8자 이상만 가능합니다.", Toast.LENGTH_SHORT).show();
                    sign_password.getText().clear();
                    return;
                }

                if (!(Pattern.matches("^[가-힣]{2,4}$", sign_name.getText().toString().trim()))) {
                    Toast.makeText(SignUpActivity.this, "이름을 다시 확인해 주십시오.", Toast.LENGTH_SHORT).show();
                    sign_name.getText().clear();
                    return;
                }

                if (!(Pattern.matches("^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", sign_phone.getText().toString().trim()))) {
                    Toast.makeText(SignUpActivity.this, "전화번호가 맞지않습니다.", Toast.LENGTH_SHORT).show();
                    sign_phone.getText().clear();
                    return;
                }

                String email = sign_email.getText().toString().trim();
                String password = sign_password.getText().toString().trim();
                String name = sign_name.getText().toString().trim();

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(name.toString())
                                            .build();
                                    Toast.makeText(SignUpActivity.this, user.getDisplayName(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SignUpActivity.this, "이메일형식이 잘못되었습니다. 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
