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
import android.widget.Toast;

import com.example.hraftiproject.R;
import com.example.hraftiproject.Database.Helper;
import com.google.firebase.FirebaseApp;

public class ForgotPswdActivity extends AppCompatActivity {
    EditText EmailCheck;
    Button valide;
    Helper database;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialisation Firebase
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_forgot_pswd);

        androidx.appcompat.app.ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflater =(LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.logo_image,null);
        actionBar.setCustomView(view);
        EmailCheck = findViewById(R.id.editEmailAddress);
        valide = findViewById(R.id.valide);

        database = new Helper(this);

        valide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = EmailCheck.getText().toString();
                Boolean checkEmail= database.checkEmail(email);
                if (checkEmail){
                    Intent i = new Intent(getApplicationContext(), ResetPasswordActivity.class);
                    i.putExtra("email",email);//
                    startActivity(i);
                }
                else{
                    Toast.makeText(ForgotPswdActivity.this, "email non existant ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
