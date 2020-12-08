package com.example.deitem_login;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

import org.w3c.dom.Text;


public class SignUpActivity extends AppCompatActivity {

    //이벤트
    private EditText signEmail , signPassword, signName, signPhone;
    private TextView authEmail;
    private RadioButton btnMan, btnFemale;
    private Button singIn;
    private boolean emailConfirm = false;

    //유저 객체
    //private UserInfo userInfo;

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

        authEmail = (TextView) findViewById(R.id.auth_email);

        firebaseAuth = FirebaseAuth.getInstance();

        signPhone.addTextChangedListener( new PhoneNumberFormattingTextWatcher());;

        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (!(Pattern.matches(getString(R.string.confirm_email),signEmail.toString()))) {
                    Toast.makeText(SignUpActivity.this, "이메일 형식이 맞지 않습니다.",Toast.LENGTH_SHORT).show();
                    return;
                }*/

                if (emailConfirm) {
                    signEmail.setText("인증완료");
                }

                if (signPassword.length()<8) { //8이하의 비밀번호
                    Toast.makeText(SignUpActivity.this, "비밀번호는 8자 이상만 가능합니다.", Toast.LENGTH_SHORT).show();
                    signPassword.getText().clear();
                    return;
                } // 비밀번호 8자 이상 제한

                if (!(Pattern.matches(getString(R.string.confirm_name), signName.getText().toString().trim()))) {
                    Toast.makeText(SignUpActivity.this, "이름을 다시 확인해 주십시오.", Toast.LENGTH_SHORT).show();
                    signName.getText().clear();
                    return;
                } //이름은 한글로만

                if (!(Pattern.matches(getString(R.string.confirm_phone), signPhone.getText().toString().trim()))) {
                    Toast.makeText(SignUpActivity.this, "전화번호가 맞지않습니다.", Toast.LENGTH_SHORT).show();
                    signPhone.getText().clear();
                    return;
                } //전화번호 유효성 검사

                String email = signEmail.getText().toString().trim();
                String password = signPassword.getText().toString().trim();
                String name = signName.getText().toString().trim();
                String phone = signPhone.getText().toString().trim();
                String gender = (btnMan.isChecked()) ? "남자":"여자";


                authEmail.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendEmailVerification();
                        Toast.makeText(SignUpActivity.this, "메일을 확인해주세요." , Toast.LENGTH_SHORT).show();
                    }
                });

                //


                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(name.toString())
                                            .build();

                                    new UserInfo(user.getUid(),email,name,phone,gender);
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
    public void sendEmailVerification() {
        // [START send_email_verification]
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "인증에 성공하셨습니다. " , Toast.LENGTH_SHORT).show();
                            emailConfirm = true;
                        }
                    }
                });
        // [END send_email_verification]
    }
}
