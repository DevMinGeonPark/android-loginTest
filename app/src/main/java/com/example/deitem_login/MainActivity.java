package com.example.deitem_login;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.deitem_login.UserInfo;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button loginTest, nameTest, test3;
    private FirebaseAuth firebaseAuth;
    private UserInfo userinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();

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
                //database test
                Toast.makeText(MainActivity.this, "실행완료" , Toast.LENGTH_SHORT).show();
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

    }
}