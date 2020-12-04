package com.example.deitem_login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button test, nameTest, test3;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();

        test = findViewById( R.id.test ); //btn object
        test.setOnClickListener( new View.OnClickListener() {
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
                Toast.makeText(MainActivity.this, user.getDisplayName().toString() , Toast.LENGTH_SHORT).show();
            }
        });

        test3 = findViewById(R.id.Test3);
        test3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( com.example.deitem_login.MainActivity.this, com.example.deitem_login.TestActivity.class );
                startActivity( intent );
            }
        });

    }
}