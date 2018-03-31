package com.example.dell.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText ruser, rpassword, rusername;
    private Button login;
    private FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUIviews();

        mauth = FirebaseAuth.getInstance();
    }

    private void setupUIviews(){
        ruser = (EditText)findViewById(R.id.email);
        rpassword = (EditText)findViewById(R.id.pwd);
        rusername = (EditText)findViewById(R.id.username);
        TextView registertxt = (TextView) findViewById(R.id.textView);
        Button register = (Button) findViewById(R.id.btn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    //upload data to database
                    String user_email = ruser.getText().toString().trim();
                    String user_password = rpassword.getText().toString().trim();

                    mauth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Resgistration successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                                MainActivity.this.startActivity(intent);
//                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, "Resgistration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    login = (Button) findViewById(R.id.btn2);
                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            finish();
                        }
                    });
                }
            }

            private Boolean validate() {
                Boolean result = false;

                String name = rusername.getText().toString();
                String mail = ruser.getText().toString();
                String password = rpassword.getText().toString();

                if (name.isEmpty() || password.isEmpty() || mail.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
                } else {
                    result = true;
                }
                return result;
            }
        });
    }
}