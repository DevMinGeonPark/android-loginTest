package com.example.deitem_login;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.deitem_login.UserInfo;


import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // 해당 Activity는 test 코드입니다.
    private Button loginTest, nameTest, test3, signOut;
    private TextView textView;
    private EditText textFocus;
    //private FirebaseAuth firebaseAuth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//  private UserInfo userinfo;

    //test codes



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();

        loginTest = findViewById( R.id.login_test ); //btn object
        loginTest.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) { //
                Intent intent = new Intent( com.example.deitem_login.MainActivity.this, com.example.deitem_login.LoginActivity.class );
                startActivity( intent );
            }
        });




        nameTest = findViewById(R.id.nameTest);
        nameTest.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "실행중" , Toast.LENGTH_SHORT).show();
                if (user!=null) {
                    Toast.makeText(MainActivity.this, "send" , Toast.LENGTH_SHORT).show();
                    sendEmailVerification();
                }
                else {
                    Toast.makeText(MainActivity.this, "현재 로그인 된 유저가 없습니다," , Toast.LENGTH_SHORT).show();
                }
            }
        });

        test3 = findViewById(R.id.test3);
        test3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( com.example.deitem_login.MainActivity.this, com.example.deitem_login.PhoneActivity.class );
                startActivity( intent );

            }
        });


        signOut = findViewById(R.id.sign_out);
        signOut.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "로그아웃 하였습니다." , Toast.LENGTH_SHORT).show();
            }
        });

        textView = findViewById(R.id.textView);
        textView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "test" , Toast.LENGTH_SHORT).show();
            }
        });

        textFocus = findViewById(R.id.test_focus);

        textFocus.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //포커스를 잃을때.
                if(!hasFocus) {
                    Toast.makeText(MainActivity.this, "포커스 잃음 ㅇ" , Toast.LENGTH_SHORT).show();

                }
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
                            Toast.makeText(MainActivity.this, "sibal email " , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END send_email_verification]
    }
}