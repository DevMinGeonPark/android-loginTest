package com.example.deitem_login;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firestore.v1.FirestoreGrpc;

import java.util.regex.Pattern;

import com.example.deitem_login.UserInfo;


public class SignUpActivity extends AppCompatActivity {

    //이벤트
    private EditText signEmail , signPassword, signName, signPhone;
    private RadioButton btnMan, btnFemale;
    private Button singIn;

    //유저 객체
    private UserInfo userInfo;

    // 파이어베이스 객체
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signEmail = (EditText) findViewById(R.id.sign_email);
        signPassword = (EditText) findViewById(R.id.sign_pwd);
        signName = (EditText) findViewById(R.id.sign_name);
        signPhone = (EditText) findViewById(R.id.sign_phone);

        singIn = (Button) findViewById(R.id.sign_up);
        btnMan = (RadioButton) findViewById(R.id.btn_man);

        firebaseAuth = FirebaseAuth.getInstance();

        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signPassword.length()<8) { //8이하의 비밀번호
                    Toast.makeText(SignUpActivity.this, "비밀번호는 8자 이상만 가능합니다.", Toast.LENGTH_SHORT).show();
                    signPassword.getText().clear();
                    return;
                } // 비밀번호 8자 이상 제한

                if (!(Pattern.matches("^[가-힣]{2,4}$", signName.getText().toString().trim()))) {
                    Toast.makeText(SignUpActivity.this, "이름을 다시 확인해 주십시오.", Toast.LENGTH_SHORT).show();
                    signName.getText().clear();
                    return;
                } //이름은 한글로만

                if (!(Pattern.matches("^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", signPhone.getText().toString().trim()))) {
                    Toast.makeText(SignUpActivity.this, "전화번호가 맞지않습니다.", Toast.LENGTH_SHORT).show();
                    signPhone.getText().clear();
                    return;
                } //전화번호 유효성 검사

                String email = signEmail.getText().toString().trim();
                String password = signPassword.getText().toString().trim();
                String name = signName.getText().toString().trim();
                String phone = signPhone.getText().toString().trim();
                String gender = (btnMan.isChecked()==true) ? "남자":"여자";

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(name.toString())
                                            .build();

                                    userInfo = new UserInfo(user.getUid(),email,name,phone,gender);
                                    Toast.makeText(SignUpActivity.this, user.getDisplayName(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SignUpActivity.this, "이메일형식이 잘못되었습니다. 다시 입력주세요.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
