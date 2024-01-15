package com.example.hraftiproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;

import android.content.Context;
import android.content.SharedPreferences;

import android.widget.ImageView;
import android.widget.SearchView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.hraftiproject.R;
import com.example.hraftiproject.Adapter.MyAdapter;
import com.example.hraftiproject.Database.Helper;
import com.example.hraftiproject.Database.FirestoreHelper;
import com.example.hraftiproject.Model.JobModel;

import java.util.ArrayList;


import com.google.firebase.FirebaseApp;

public class HomeActivity extends AppCompatActivity {

    private ArrayList<JobModel> jobModalArrayList;
    private static Helper dbHandler;

    private MyAdapter myAdapter;
    private RecyclerView recyclerView;
    RecyclerView listView;
    Button button;
    LoginActivity login=new LoginActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        // Initialisation de Firebase
        FirebaseApp.initializeApp(this);

        setContentView(R.layout.recycler_view);



        jobModalArrayList = new ArrayList<>();
        dbHandler = new Helper(HomeActivity.this);
        jobModalArrayList = dbHandler.readJobs();
        myAdapter= new MyAdapter(jobModalArrayList, HomeActivity.this);
        recyclerView = findViewById(R.id.idRC);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);
        dbHandler = new Helper(this);
        listView = findViewById(R.id.idRC);
        Actualiser("");


        androidx.appcompat.app.ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflater =(LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.logo_image,null);
        actionBar.setCustomView(view);


    }



     void Actualiser(String str){
        ArrayList<JobModel> jobs = dbHandler.returnJob(str);
        MyAdapter job = new MyAdapter(jobs);
        listView.setAdapter((RecyclerView.Adapter) job);
     }
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu, menu);
        //
        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("Metier,Nom,Ville ");
        MenuItem i=menu.findItem(R.id.inscription);
        MenuItem i1=menu.findItem(R.id.login);
        if (login.IsLoged()){
            i.setVisible(false);
            i1.setVisible(false);
        }
        MenuItem i2=menu.findItem(R.id.profil);
        MenuItem i3=menu.findItem(R.id.home);
        if (!login.IsLoged()){
            i3.setVisible(false);
            i2.setVisible(false);
        }

        MenuItem myItem = menu.findItem(R.id.search);
        SearchView sv = (SearchView) MenuItemCompat.getActionView(myItem);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Actualiser(s);
                return false;
            }
        });

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.inscription) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }
        if (id == R.id.login) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

        }
        if (id == R.id.profil) {
            Intent intent = new Intent(this, ProfileActivity.class);
            SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
            String email = sh.getString("useremail", "");
            intent.putExtra("useremail",email);
            startActivity(intent);
        }
        if (id == R.id.home) {
            login.Logout();
            finish();
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);



        }

        return super.onOptionsItemSelected(item);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView job;
        public TextView name;
        public TextView number;
        public TextView city;
        public ImageView image;
        String email;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            job = itemView.findViewById(R.id.job);
            name = itemView.findViewById(R.id.textname);
            number = itemView.findViewById(R.id.textnumber);
            city = itemView.findViewById(R.id.textcity);
           image = itemView.findViewById(R.id.image_profile);


            itemView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    email = dbHandler.getEmailByName(name.getText().toString());
                    Intent intent = new Intent(view.getContext(), ProfileActivity.class);
                    intent.putExtra("useremail", email);
                    System.out.println(email);
                    view.getContext().startActivity(intent);
                }


            });

        }


    }



}


