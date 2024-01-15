package com.example.hraftiproject.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hraftiproject.R;
import com.example.hraftiproject.Database.Helper;

import com.google.firebase.FirebaseApp;

public class LoginActivity extends AppCompatActivity {
        EditText Email,motdepasse;
        ImageView SeConnecter;
        TextView motdpOublie;
        TextView Inscrire;
        Helper database;
       public static Boolean isLoged = false;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialisation Firebase
        FirebaseApp.initializeApp(this);

        setContentView(R.layout.activity_login);
        androidx.appcompat.app.ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflater =(LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.logo_image,null);



        actionBar.setCustomView(view);


        Email = findViewById(R.id.email);
        motdepasse = findViewById(R.id.passwordEdite);
        SeConnecter = findViewById(R.id.Seconnect);
        motdpOublie = findViewById(R.id.Oublie);
        Inscrire =findViewById(R.id.button2);
        database = new Helper(this);

        SeConnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user =Email.getText().toString();
                String pass = motdepasse.getText().toString();
                if(user.equals("")){
                    Toast.makeText(LoginActivity.this,"entrer votre email",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (pass.equals("")) {
                        Toast.makeText(LoginActivity.this, "entrer votre mot de passe", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Boolean checkpass = database.checkEmailPassword(user, pass);
                        if (checkpass) {
                            isLoged=true;
                            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                            // Storing data into SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.putString("useremail", Email.getText().toString());
                            myEdit.commit();

                            i.putExtra("useremail",user);
                            startActivity(i);
                            Toast.makeText(LoginActivity.this, "connection succès", Toast.LENGTH_SHORT).show();
                            Email.setText("");
                            motdepasse.setText("");

                        } else {
                            Toast.makeText(LoginActivity.this, "Informations invalides", Toast.LENGTH_SHORT).show();

                        }
                    }
                }

            }
        });
        motdpOublie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ForgotPswdActivity.class);
                startActivity(i);
            }
        });
        Inscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
    }
    public static Boolean IsLoged(){
        System.out.println(isLoged);
        return isLoged;

    }
    public static Boolean Logout(){
        isLoged = false;
        System.out.println(isLoged);
        return isLoged;

    }


}