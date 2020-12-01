package com.example.deitem_login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test = findViewById( R.id.test ); //btn object
        test.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) { //
                Intent intent = new Intent( com.example.deitem_login.MainActivity.this, com.example.deitem_login.LoginActivity.class );
                startActivity( intent );
            }
        });
    }
}