package com.example.gfgapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gfgapp.databases.DBHandler;
import com.example.gfgapp.modal.MainModal;
import com.example.gfgapp.modal.StudentModal;

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


        try {
            double total = dbHandler.getTotalSubjectHours(modal.getName(), "Math", mainModal.getUserEmail(), "No") +
                    dbHandler.getTotalSubjectHours(modal.getName(), "English", mainModal.getUserEmail(), "No") +
                    dbHandler.getTotalSubjectHours(modal.getName(), "Science", mainModal.getUserEmail(), "No") +
                    dbHandler.getTotalSubjectHours(modal.getName(), "History", mainModal.getUserEmail(), "No");

            double totalCore = dbHandler.getTotalSubjectHours(modal.getName(), "Math", mainModal.getUserEmail(), "Yes") +
                    dbHandler.getTotalSubjectHours(modal.getName(), "English", mainModal.getUserEmail(), "Yes") +
                    dbHandler.getTotalSubjectHours(modal.getName(), "Science", mainModal.getUserEmail(), "Yes") +
                    dbHandler.getTotalSubjectHours(modal.getName(), "History", mainModal.getUserEmail(), "Yes");


            holder.studentNameCard.setText(modal.getName());
            holder.studentMathCore.setText(String.valueOf(dbHandler.getTotalSubjectHours(modal.getName(), "Math", mainModal.getUserEmail(), "No")));
            holder.studentEnglishCore.setText(String.valueOf(dbHandler.getTotalSubjectHours(modal.getName(), "English", mainModal.getUserEmail(), "No")));
            holder.studentScienceCore.setText(String.valueOf(dbHandler.getTotalSubjectHours(modal.getName(), "Science", mainModal.getUserEmail(), "No")));
            holder.studentHistoryCore.setText(String.valueOf(dbHandler.getTotalSubjectHours(modal.getName(), "History", mainModal.getUserEmail(), "No")));
            holder.studentPeCore.setText(String.valueOf(dbHandler.getTotalSubjectHours(modal.getName(), "Pe", mainModal.getUserEmail(), "No")));
            holder.studentExtraCore.setText(String.valueOf(dbHandler.getTotalSubjectHours(modal.getName(), "Extracurriculars", mainModal.getUserEmail(), "No")));
            holder.totalHoursCore.setText(String.valueOf(total));

            holder.studentMath.setText(String.valueOf(dbHandler.getTotalSubjectHours(modal.getName(), "Math", mainModal.getUserEmail(), "Yes")));
            holder.studentEnglish.setText(String.valueOf(dbHandler.getTotalSubjectHours(modal.getName(), "English", mainModal.getUserEmail(), "Yes")));
            holder.studentScience.setText(String.valueOf(dbHandler.getTotalSubjectHours(modal.getName(), "Science", mainModal.getUserEmail(), "Yes")));
            holder.studentHistory.setText(String.valueOf(dbHandler.getTotalSubjectHours(modal.getName(), "History", mainModal.getUserEmail(), "Yes")));
            holder.studentPe.setText(String.valueOf(dbHandler.getTotalSubjectHours(modal.getName(), "Pe", mainModal.getUserEmail(), "Yes")));
            holder.studentExtra.setText(String.valueOf(dbHandler.getTotalSubjectHours(modal.getName(), "Extracurriculars", mainModal.getUserEmail(), "Yes")));
            holder.totalHours.setText(String.valueOf(totalCore));
        } catch (Exception e) {
            double total = dbHandler.getTotalSubjectHours(modal.getName(), "Math", mainModal.getGoogleEmail(), "No") +
                    dbHandler.getTotalSubjectHours(modal.getName(), "English", mainModal.getGoogleEmail(), "No") +
                    dbHandler.getTotalSubjectHours(modal.getName(), "Science", mainModal.getGoogleEmail(), "No") +
                    dbHandler.getTotalSubjectHours(modal.getName(), "History", mainModal.getGoogleEmail(), "No");

            double totalCore = dbHandler.getTotalSubjectHours(modal.getName(), "Math", mainModal.getGoogleEmail(), "Yes") +
                    dbHandler.getTotalSubjectHours(modal.getName(), "English", mainModal.getGoogleEmail(), "Yes") +
                    dbHandler.getTotalSubjectHours(modal.getName(), "Science", mainModal.getGoogleEmail(), "Yes") +
                    dbHandler.getTotalSubjectHours(modal.getName(), "History", mainModal.getGoogleEmail(), "Yes");


            holder.studentNameCard.setText(modal.getName());
            holder.studentMathCore.setText(String.valueOf(dbHandler.getTotalSubjectHours(modal.getName(), "Math", mainModal.getGoogleEmail(), "No")));
            holder.studentEnglishCore.setText(String.valueOf(dbHandler.getTotalSubjectHours(modal.getName(), "English", mainModal.getGoogleEmail(), "No")));
            holder.studentScienceCore.setText(String.valueOf(dbHandler.getTotalSubjectHours(modal.getName(), "Science", mainModal.getGoogleEmail(), "No")));
            holder.studentHistoryCore.setText(String.valueOf(dbHandler.getTotalSubjectHours(modal.getName(), "History", mainModal.getGoogleEmail(), "No")));
            holder.studentPeCore.setText(String.valueOf(dbHandler.getTotalSubjectHours(modal.getName(), "Pe", mainModal.getGoogleEmail(), "No")));
            holder.studentExtraCore.setText(String.valueOf(dbHandler.getTotalSubjectHours(modal.getName(), "Extracurriculars", mainModal.getGoogleEmail(), "No")));
            holder.totalHoursCore.setText(String.valueOf(total));

            holder.studentMath.setText(String.valueOf(dbHandler.getTotalSubjectHours(modal.getName(), "Math", mainModal.getGoogleEmail(), "Yes")));
            holder.studentEnglish.setText(String.valueOf(dbHandler.getTotalSubjectHours(modal.getName(), "English", mainModal.getGoogleEmail(), "Yes")));
            holder.studentScience.setText(String.valueOf(dbHandler.getTotalSubjectHours(modal.getName(), "Science", mainModal.getGoogleEmail(), "Yes")));
            holder.studentHistory.setText(String.valueOf(dbHandler.getTotalSubjectHours(modal.getName(), "History", mainModal.getGoogleEmail(), "Yes")));
            holder.studentPe.setText(String.valueOf(dbHandler.getTotalSubjectHours(modal.getName(), "Pe", mainModal.getGoogleEmail(), "Yes")));
            holder.studentExtra.setText(String.valueOf(dbHandler.getTotalSubjectHours(modal.getName(), "Extracurriculars", mainModal.getGoogleEmail(), "Yes")));
            holder.totalHours.setText(String.valueOf(totalCore));

        }


    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return courseModelArrayList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView studentNameCard, studentMath, studentScience, studentEnglish, studentHistory, studentPe, studentExtra, totalHours, studentMathCore, studentScienceCore, studentEnglishCore, studentHistoryCore, studentPeCore, studentExtraCore, totalHoursCore;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentNameCard = itemView.findViewById(R.id.studentNameCard);
            studentMath = itemView.findViewById(R.id.studentMath);
            studentScience = itemView.findViewById(R.id.studentScience);
            studentEnglish = itemView.findViewById(R.id.studentEnglish);
            studentHistory = itemView.findViewById(R.id.studentHistory);
            studentPe = itemView.findViewById(R.id.peHours);
            studentExtra = itemView.findViewById(R.id.extraHours);
            totalHours = itemView.findViewById(R.id.totalHours);

            studentMathCore = itemView.findViewById(R.id.studentMathCore);
            studentScienceCore = itemView.findViewById(R.id.studentScienceCore);
            studentEnglishCore = itemView.findViewById(R.id.studentEnglishCore);
            studentHistoryCore = itemView.findViewById(R.id.studentHistoryCore);
            studentPeCore = itemView.findViewById(R.id.peHoursCore);
            studentExtraCore = itemView.findViewById(R.id.extraHoursCore);
            totalHoursCore = itemView.findViewById(R.id.totalHoursCore);


        }
    }
}
