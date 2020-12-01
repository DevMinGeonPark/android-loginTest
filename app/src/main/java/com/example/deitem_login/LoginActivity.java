package com.example.deitem_login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText et_id, et_pass; //input text
    private Button btn_login, btn_register; //button event

    @Override
    protected void onCreate(Bundle savedInstanceState) { //main
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        et_id = findViewById( R.id.et_id ); //id object
        et_pass = findViewById( R.id.et_pass ); //pw object

        btn_register = findViewById( R.id.btn_register ); //btn object
        btn_register.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) { //
                Intent intent = new Intent( com.example.deitem_login.LoginActivity.this, com.example.deitem_login.RegisterActivity.class );
                startActivity( intent );
            }
        });


        btn_login = findViewById( R.id.btn_login );
        btn_login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            boolean success = jsonObject.getBoolean( "success" ); //?

                            if(success) { //로그인 성공시

                                String userID = jsonObject.getString( "userID" );
                                String userPass = jsonObject.getString( "userPassword" );
                                String userName = jsonObject.getString( "userName" );
                                String userAge = jsonObject.getString( "userAge" );

                                Toast.makeText( getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT ).show();
                                Intent intent = new Intent( com.example.deitem_login.LoginActivity.this, MainActivity.class ); //intent object - > ohter activity call

                                intent.putExtra( "userID", userID ); //?
                                intent.putExtra( "userPass", userPass );
                                intent.putExtra( "userName", userName );
                                intent.putExtra( "userAge", userAge );

                                startActivity( intent );

                            } else {//로그인 실패시
                                Toast.makeText( getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT ).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                com.example.deitem_login.LoginRequest loginRequest = new com.example.deitem_login.LoginRequest( userID, userPass, responseListener ); //user create object
                RequestQueue queue = Volley.newRequestQueue( com.example.deitem_login.LoginActivity.this );
                queue.add( loginRequest );

            }
        });
    }
}