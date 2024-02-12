package com.example.hraftiproject.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hraftiproject.R;
import com.example.hraftiproject.Database.Helper;

import com.google.firebase.FirebaseApp;

public class RegisterActivity extends AppCompatActivity  {

    private EditText nomEdt ,emailEdt,passwordEdt,villeEdt,numtelEdt,descriptionEdt ,passwordEdtConf;
    private Button submitBtn ;
    private Spinner dropdown;
    //insertion image
    private ImageView profileImage;
    private static final int PICK_IMAGE_REQUEST=99;
    Uri imagePath;
    Bitmap imageToStore;
    boolean passwordVisible1 ,passwordVisible2 ; //=true
    int increment=0 ,imageImport=0;

    String item ;
    private Helper helper;
    String[] items = new String[]{ "Sélectionner un métier","Agriculteur","Boucher", "Boulanger", "Chauffeur","Cuisinier","électricien","Femme de menage","Maçon",
            "Menuisier","Mécanicien","Jardinier",
            "Peintre", "Photographe",  "Plombier"
            ,"Serveur" ,"Securité"};
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialisation Firebase ici
        FirebaseApp.initializeApp(this);

        setContentView(R.layout.activity_register);
        //insertion image
        profileImage =findViewById(R.id.profile);

        nomEdt=findViewById(R.id.nom);
        emailEdt=findViewById(R.id.email);
        passwordEdt=findViewById(R.id.password);
        passwordEdtConf=findViewById(R.id.passwordConf);
        villeEdt=findViewById(R.id.ville);
        numtelEdt=findViewById(R.id.numtel);
        descriptionEdt=findViewById(R.id.description);
        submitBtn=findViewById(R.id.submit);
        dropdown = findViewById(R.id.spinner1);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //Drop layout style
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                if (parent.getItemAtPosition(position).equals("Sélectionner un Métier")){
                    increment=1;
                }else{
                    item=parent.getItemAtPosition(position).toString();
                    increment=0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                increment=1;
            }
        });


        helper = new Helper(RegisterActivity.this);
        //insertion image
        //chose image from device
        profileImage.setOnClickListener(view -> choseImage());

        ///////////// visibility password
        passwordEdt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right=2;
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if (motionEvent.getRawX()>=passwordEdt.getRight()-passwordEdt.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=passwordEdt.getSelectionEnd();
                        if(passwordVisible1){
                            //set drawable image here
                            passwordEdt.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.visibility_off,0);
                            //for hide password
                            passwordEdt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible1=false;
                        }else{
                            //set drawable image here
                            passwordEdt.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.visibility,0);
                            //for show password
                            passwordEdt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible1=true;

                        }
                        passwordEdt.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
        passwordEdtConf.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right=2;
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if (motionEvent.getRawX()>=passwordEdtConf.getRight()-passwordEdtConf.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=passwordEdtConf.getSelectionEnd();
                        if(passwordVisible2){
                            //set drawable image here
                            passwordEdtConf.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.visibility_off,0);
                            //for hide password
                            passwordEdtConf.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible2=false;
                        }else{
                            //set drawable image here
                            passwordEdtConf.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.visibility,0);
                            //for show password
                            passwordEdtConf.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible2=true;

                        }
                        passwordEdtConf.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        submitBtn.setOnClickListener(v -> {

            // below line is to get data from all edit text fields.
            String  nom=nomEdt.getText().toString();
            String email = emailEdt.getText().toString();
            String password=  passwordEdt.getText().toString();
            String passwordConf=  passwordEdtConf.getText().toString();
            String ville=  villeEdt.getText().toString();
            int numtel= Integer.parseInt(numtelEdt.getText().toString());
            String num = numtelEdt.getText().toString() ;
            String description= descriptionEdt.getText().toString();
           //

            // validating if the text fields are empty or not.
            if (nom.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConf.isEmpty()  || ville.isEmpty() || description.isEmpty() ||num.isEmpty() ) {
                Toast.makeText(getApplicationContext(), "remplissez tous les champs !!", Toast.LENGTH_SHORT).show(); //InscriptionActivity.this

              }else if (!password.equals(passwordConf)){
                Toast.makeText(getApplicationContext(), "le mot de passe confirmer incorrect !", Toast.LENGTH_SHORT).show(); //InscriptionActivity.this
                  //verifier format de e-mail
              }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

                    Toast.makeText(this, "E-mail incorrect!", Toast.LENGTH_SHORT).show();
              }else if(increment ==1) {

                Toast.makeText(getApplicationContext(), " Sélectionner un métier..", Toast.LENGTH_SHORT).show(); //InscriptionActivity.this
              }else if(imageImport==0){

                Toast.makeText(getApplicationContext(), " Importer une image..", Toast.LENGTH_SHORT).show(); //InscriptionActivity.this

            }else {

                try{
                    helper.addNewProfessionnel( nom ,email,password,item,numtel,ville ,description ,imageToStore);
                    Toast.makeText(RegisterActivity.this, "Registration Successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(i);
                }catch (Exception E){
                     Toast.makeText(getApplicationContext(),"registration user failed ! " , Toast.LENGTH_LONG).show();

                }

            }


        });


        androidx.appcompat.app.ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflater =(LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.logo_image,null);
        actionBar.setCustomView(view);



    }
    //insertion image
    private void choseImage() {
        try{
            Intent intent=new Intent();
            intent.setType("image/*");
            intent.setAction(intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,PICK_IMAGE_REQUEST);
            imageImport=1;
        }catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
            imageImport=0;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try{
            super.onActivityResult(requestCode, resultCode, data);
            if( data ==null || data.getData()==null){
                imageImport=0;

            }
            else if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data !=null && data.getData()!=null )//&& data !=null && data.getData()!=null
            {
                imagePath=data.getData();
                imageToStore= MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
                profileImage.setImageBitmap(imageToStore);
                imageImport=1;

            }
        }catch(Exception e)
        {

        }
    }
    public void Login(View view){
        Intent i = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(i);


    }


}
