package com.example.gfgapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gfgapp.dataadapter.HoursAdapter;
import com.example.gfgapp.modal.MainModal;
import com.example.gfgapp.modal.StudentModal;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<StudentModal> courseModelArrayList;

    MainModal mainModal = MainModal.getInstance();

    String GMail = "1krackerapp@gmail.com"; //replace with you GMail
    String GMailPass = "oskg pwzy huzb xycu"; // replace with you GMail Password


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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout


        StudentModal modal = courseModelArrayList.get(position);


        holder.itemView.setOnClickListener(v -> {
            mainModal.setUserName(modal.getName());
            Intent i = new Intent(context, Snapshots.class);

            context.startActivity(i);
        });
        HoursAdapter hours = new HoursAdapter();

        try {

            holder.studentName.setText(modal.getName());
            holder.grade.setText(modal.getGrade());

//            hours.getTotalHoursBySubjectAndCore(modal.getName(), mainModal.getUserEmail(),result -> {
//                holder.totalHours.setText(result);
//            });

        } catch (Exception e) {
            Toast.makeText(context, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return courseModelArrayList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {

        //        private final TextView studentNameCard, studentMath, studentScience, studentEnglish, studentHistory, studentPe, studentExtra, totalHours, studentMathCore, studentScienceCore, studentEnglishCore, studentHistoryCore, studentPeCore, studentExtraCore, totalHoursCore;
        private final TextView studentName, totalHours, grade;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.displayName);
            totalHours = itemView.findViewById(R.id.totalHoursInput);
            grade = itemView.findViewById(R.id.displayGrade);

        }
    }


}
