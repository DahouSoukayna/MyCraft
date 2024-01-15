package com.example.hraftiproject.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hraftiproject.Activities.HomeActivity;
import com.example.hraftiproject.R;
import com.example.hraftiproject.Database.Helper;
import com.example.hraftiproject.Model.JobModel;

import java.util.ArrayList;




public class MyAdapter  extends RecyclerView.Adapter<HomeActivity.ViewHolder> {


    private Helper dbHelper;
    private Context context;
    private ArrayList<JobModel> jobsArrayList;


    public MyAdapter(ArrayList<JobModel> jobsArrayList, Context context) {
        this.jobsArrayList = jobsArrayList;
        this.context = context;


    }

    public MyAdapter(ArrayList<JobModel> jobs) {
        this.jobsArrayList = jobs;
    }

    @NonNull
    @Override
    public HomeActivity.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home, parent, false);
        return new HomeActivity.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull HomeActivity.ViewHolder holder, int position) {
        JobModel modal = jobsArrayList.get(position);
        holder.job.setText(modal.getJob());
        holder.name.setText(modal.getName());
        holder.number.setText(modal.getNumber());
        holder.city.setText(modal.getCity());
        holder.image.setImageBitmap(modal.getImage());
    }

    @Override
    public int getItemCount() {
        return jobsArrayList.size();
    }




}







