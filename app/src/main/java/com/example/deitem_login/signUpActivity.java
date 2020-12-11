package com.example.deitem_login;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    //이벤트
    private EditText signEmail , signPassword, signName, signPhone, signConfirmPassword;
    private RadioButton btnMan;
    private Button singIn;
    private Spinner spinner;

    //Spinner 관련
    private String[] item = new String[]{"선택하세요","naver.com","nate.com","hanmail.com","gmail.com","직접입력"};
    private String domain;

    // 파이어베이스 객체
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signEmail = (EditText) findViewById(R.id.sign_email);
        signPassword = (EditText) findViewById(R.id.sign_pwd);
        signName = (EditText) findViewById(R.id.sign_name);
        signPhone = (EditText) findViewById(R.id.sign_phone);
        signConfirmPassword = (EditText) findViewById(R.id.sign_confirm_pwd);
        singIn = (Button) findViewById(R.id.sign_up);

        spinner = (Spinner) findViewById(R.id.spinner);
        btnMan = (RadioButton) findViewById(R.id.btn_man);

        signEmail.setOnClickListener(this);
        //signPassword.setOnClickListener(this);
        signName.setOnClickListener(this);
        signPhone.setOnClickListener(this);
        signConfirmPassword.setOnClickListener(this);
        singIn.setOnClickListener(this);

        signPassword.setOnFocusChangeListener(this);


        firebaseAuth = FirebaseAuth.getInstance();

        signPhone.addTextChangedListener( new PhoneNumberFormattingTextWatcher()); //phone Number Format

        //spinner event handling
        ArrayAdapter spinnerAdapter;
        spinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(SignUpActivity.this,"선택된 아이템 : "+spinner.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
                domain = spinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (!(Pattern.matches(getString(R.string.confirm_email),signEmail.toString()))) {
                    Toast.makeText(SignUpActivity.this, "이메일 형식이 맞지 않습니다.",Toast.LENGTH_SHORT).show();
                    return;
                }*/

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

                String email = signEmail.getText().toString().trim() + "\u0040" + domain;
                String password = signPassword.getText().toString().trim();
                String confirmPassword = signConfirmPassword.getText().toString().trim();
                String name = signName.getText().toString().trim();
                String phone = signPhone.getText().toString().trim();
                String gender = (btnMan.isChecked()) ? "남자":"여자";

                if (password!=confirmPassword) {

                }

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
                                    Toast.makeText(SignUpActivity.this, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show();
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

        signPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //포커스를 잃을때.
                if(!hasFocus) {
                    if (signPassword.length()<8) { //8이하의 비밀번호
                        Toast.makeText(SignUpActivity.this, "비밀번호는 8자 이상만 가능합니다.", Toast.LENGTH_SHORT).show();
                        signPassword.getText().clear();
                        return;
                    } // 비밀번호 8자 이상 제한
                }
            }
        });


    }

    public void sendEmailVerification() {
        // [START send_email_verification]
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        Toast.makeText(SignUpActivity.this, "메일을 발송했습니다..", Toast.LENGTH_SHORT).show();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Email 전송이 완료되었습니다. " , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END send_email_verification]
    } //확인 이메일 발송

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case :
                break;

        }
    }
    // onfocuschange를 좀더 가독성 좋게 유지보수하기
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.sign_pwd:
                break;
        }

    }
}
