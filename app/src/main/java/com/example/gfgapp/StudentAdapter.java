package com.example.gfgapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<StudentModal> courseModelArrayList;

    MainModal mainModal = MainActivity.mainModal;


    // Constructor
    public StudentAdapter(Context context, ArrayList<StudentModal> courseModelArrayList) {
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
    }

    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_child, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout

        StudentModal modal = courseModelArrayList.get(position);
        DBHandler dbHandler = new DBHandler(context);

        holder.itemView.setOnClickListener(v -> {
            mainModal.setUserName(modal.getName());
            Intent i = new Intent(context, ViewCourses.class);

            context.startActivity(i);
        });


        int total = dbHandler.getTotalSubjectHours(modal.getName(), "Math", mainModal.getUserEmail()) +
                dbHandler.getTotalSubjectHours(modal.getName(), "English", mainModal.getUserEmail()) +
                dbHandler.getTotalSubjectHours(modal.getName(), "Science", mainModal.getUserEmail()) +
                dbHandler.getTotalSubjectHours(modal.getName(), "History", mainModal.getUserEmail());

        holder.studentNameCard.setText(modal.getName());
        holder.studentMath.setText(String.valueOf(dbHandler.getTotalSubjectHours(modal.getName(), "Math", mainModal.getUserEmail())));
        holder.studentEnglish.setText(String.valueOf(dbHandler.getTotalSubjectHours(modal.getName(), "English", mainModal.getUserEmail())));
        holder.studentScience.setText(String.valueOf(dbHandler.getTotalSubjectHours(modal.getName(), "Science", mainModal.getUserEmail())));
        holder.studentHistory.setText(String.valueOf(dbHandler.getTotalSubjectHours(modal.getName(), "History", mainModal.getUserEmail())));
        holder.totalHours.setText(String.valueOf(total));


    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return courseModelArrayList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView studentNameCard, studentMath, studentScience, studentEnglish, studentHistory, totalHours;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentNameCard = itemView.findViewById(R.id.studentNameCard);
            studentMath = itemView.findViewById(R.id.studentMath);
            studentScience = itemView.findViewById(R.id.studentScience);
            studentEnglish = itemView.findViewById(R.id.studentEnglish);
            studentHistory = itemView.findViewById(R.id.studentHistory);
            totalHours = itemView.findViewById(R.id.totalHours);


        }
    }
}
