package com.example.hraftiproject.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hraftiproject.R;
import com.example.hraftiproject.Database.Helper;
import com.example.hraftiproject.Model.User;
import com.google.firebase.FirebaseApp;


public class ProfileActivity extends AppCompatActivity {
    Helper helper;
    User user;
    TextView nameC,email,ville,descreption,metier,phone,name,metierC;
    EditText edit_nomC,edit_email,edit_ville,edit_description,edit_metier,edit_phone;
    Button btnSubmit,btnCancel;
    LoginActivity login=new LoginActivity();
    ImageView imageView,itm;
    Uri imagePath;
    Bitmap imageToStore;
    private static final int PICK_IMAGE_REQUEST=99;


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        user = new User();
        super.onCreate(savedInstanceState);
        // Initialisation Firebase
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_profile);

        androidx.appcompat.app.ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflater =(LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.logo_image,null);
        actionBar.setCustomView(view);
        helper = new Helper(getApplicationContext());

        nameC = findViewById(R.id.input_name);
        email=findViewById(R.id.input_email);
        ville=findViewById(R.id.input_ville);
        descreption=findViewById(R.id.inout_description);
        metier=findViewById(R.id.input_metier);
        phone=findViewById(R.id.input_phone);
        name=findViewById(R.id.input_nomC);
        metierC=findViewById(R.id.input_metierC);
        edit_nomC=findViewById(R.id.edit_nomC);
        edit_email=findViewById(R.id.edit_email);
        edit_ville=findViewById(R.id.edit_ville);
        edit_description=findViewById(R.id.edit_description);
        edit_metier=findViewById(R.id.edit_metier);
        edit_phone=findViewById(R.id.edit_phone);
        btnSubmit=findViewById(R.id.submit);
        btnCancel=findViewById(R.id.cancel);
        imageView=findViewById(R.id.profileImage);
        Intent i = getIntent();
        email.setText(i.getStringExtra("useremail"));
        String userEmail = email.getText().toString();
        user = helper.getUser(userEmail);
        nameC.setText(user.getName());
        name.setText(user.getName());
        edit_nomC.setText(user.getName());
        email.setText(user.getEmail());
        edit_email.setText(user.getEmail());
        ville.setText(user.getVille());
        edit_ville.setText(user.getVille());
        metier.setText(user.getMetier());
        edit_metier.setText(user.getMetier());
        metierC.setText(user.getMetier());
        descreption.setText(user.getDescription());
        edit_description.setText(user.getDescription());
        phone.setText(String.valueOf(user.getPhone()));
        edit_phone.setText(String.valueOf(user.getPhone()));
        imageView.setImageBitmap(user.getImage());
        imageToStore=user.getImage();




        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (login.IsLoged() ) {
            SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
            String str = sh.getString("useremail", "");

            if(str.equals(email.getText())){
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnSubmit.setVisibility(View.VISIBLE);
                        btnCancel.setVisibility(View.VISIBLE);
                        choseImage();
                    }
                });
                name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    name.setVisibility(View.GONE);
                    edit_nomC.setVisibility(View.VISIBLE);
                    btnSubmit.setVisibility(View.VISIBLE);
                    btnCancel.setVisibility(View.VISIBLE);
                }
            });
            email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    email.setVisibility(View.GONE);
                    edit_email.setVisibility(View.VISIBLE);
                    btnSubmit.setVisibility(View.VISIBLE);
                    btnCancel.setVisibility(View.VISIBLE);
                }
            });

            ville.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ville.setVisibility(View.GONE);
                    edit_ville.setVisibility(View.VISIBLE);
                    btnSubmit.setVisibility(View.VISIBLE);
                    btnCancel.setVisibility(View.VISIBLE);
                }
            });


            metier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    metier.setVisibility(View.GONE);
                    edit_metier.setVisibility(View.VISIBLE);
                    btnSubmit.setVisibility(View.VISIBLE);
                    btnCancel.setVisibility(View.VISIBLE);
                }
            });
            descreption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    descreption.setVisibility(View.GONE);
                    edit_description.setVisibility(View.VISIBLE);
                    btnSubmit.setVisibility(View.VISIBLE);
                    btnCancel.setVisibility(View.VISIBLE);
                }
            });
            phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    phone.setVisibility(View.GONE);
                    edit_phone.setVisibility(View.VISIBLE);
                    btnSubmit.setVisibility(View.VISIBLE);
                    btnCancel.setVisibility(View.VISIBLE);
                }
            });
            }

            }
        else {

            ImageView img = (ImageView)findViewById(R.id.profileImage);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                 View dialog = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.dialog,null);

                    itm = dialog.findViewById(R.id.img);
                    itm.setImageBitmap(user.getImage());
                    builder.setView(dialog);
                    builder.setCancelable(true);
                   builder.show();
                }
            });}
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    close();
                    helper.UpdateProfessionnel(user.getId()
                            , edit_nomC.getText().toString()
                            , edit_email.getText().toString()
                            , edit_metier.getText().toString()
                            , Integer.parseInt(edit_phone.getText().toString())
                            , edit_ville.getText().toString()
                            , edit_description.getText().toString(),
                            imageToStore
                    );
                    user = helper.getUser(edit_email.getText().toString());
                    nameC.setText(user.getName());
                    name.setText(user.getName());
                    email.setText(user.getEmail());
                    ville.setText(user.getVille());
                    metier.setText(user.getMetier());
                    metierC.setText(user.getMetier());
                    descreption.setText(user.getDescription());
                    phone.setText(String.valueOf(user.getPhone()));
                    Toast.makeText(ProfileActivity.this, "Profile est modifier avec succès", Toast.LENGTH_LONG).show();
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    close();
                    edit_nomC.setText(user.getName());
                    edit_email.setText(user.getEmail());
                    edit_ville.setText(user.getVille());
                    edit_metier.setText(user.getMetier());
                    edit_description.setText(user.getDescription());
                    edit_phone.setText(String.valueOf(user.getPhone()));

                }
            });



        }






    public void close(){
        hideSoftKeyboard(this);
        btnSubmit.setVisibility(View.GONE);
        btnCancel.setVisibility(View.GONE);

        phone.setVisibility(View.VISIBLE);
        edit_phone.setVisibility(View.GONE);

        descreption.setVisibility(View.VISIBLE);
        edit_description.setVisibility(View.GONE);

        metier.setVisibility(View.VISIBLE);
        edit_metier.setVisibility(View.GONE);

        ville.setVisibility(View.VISIBLE);
        edit_ville.setVisibility(View.GONE);

        email.setVisibility(View.VISIBLE);
        edit_email.setVisibility(View.GONE);

        name.setVisibility(View.VISIBLE);
        edit_nomC.setVisibility(View.GONE);

    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }

    private void choseImage() {
        try{
            Intent intent=new Intent();
            intent.setType("image/*");
            intent.setAction(intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,PICK_IMAGE_REQUEST);
        }catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try{
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data !=null && data.getData()!=null)
            {
                imagePath=data.getData();
                imageToStore= MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
                imageView.setImageBitmap(imageToStore);
            }
        }catch(Exception e)
        {

        }
    }


}