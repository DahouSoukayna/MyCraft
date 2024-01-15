package com.example.hraftiproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hraftiproject.R;
import com.example.hraftiproject.Database.Helper;
import com.google.firebase.FirebaseApp;


public class ResetPasswordActivity extends AppCompatActivity {
    TextView email;
    EditText passwd,repasswd;
    Button confirm;
    Helper database;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialisation Firebase
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_reset_password);
        androidx.appcompat.app.ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflater =(LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.logo_image,null);
        actionBar.setCustomView(view);
        email = findViewById(R.id.EmailGet);
        passwd = findViewById(R.id.passwordEdite);
        repasswd= findViewById(R.id.passwordreEentrer);
        confirm = findViewById(R.id.ResetPasswd);
        Intent i = getIntent();
        email.setText(i.getStringExtra("email"));
        database = new Helper(this);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email =email.getText().toString();
                String password= passwd.getText().toString();
                String rePassword = repasswd.getText().toString();
                if (password.equals(rePassword)){
                    Boolean checkPasswordUpdate= database.updatePasswd(Email,password);
                    if (checkPasswordUpdate){
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(ResetPasswordActivity.this, "mot de passe modifier avec succes", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(ResetPasswordActivity.this, "mot de passe non modifier ", Toast.LENGTH_SHORT).show();

                    }
                }
                else {
                    Toast.makeText(ResetPasswordActivity.this, "mot de passe non verifier", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}