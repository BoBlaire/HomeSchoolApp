package com.example.gfgapp;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gfgapp.DEAD.DBHandler;
import com.example.gfgapp.modal.CourseModal;
import com.example.gfgapp.modal.Modal;


import java.util.ArrayList;
import java.util.List;

public class CourseRVAdapter extends RecyclerView.Adapter<CourseRVAdapter.ViewHolder> {

    // variable for our array list and context
    private List<CourseModal> courseModalArrayList;
    private Context context;
    private DBHandler dbHandler;
    private TextView name, subject, hours, core;
    Modal modals = ViewCourses.modal;

    public CourseRVAdapter(ArrayList<CourseModal> courseModalArrayList, Context context) {
        this.courseModalArrayList = courseModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       /* on below line we are inflating our layout
          file for our recycler view items.
          below line is to inflate our layout. */

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_course_rv_item, parent, false);

        //Calling resizing text code
        ViewCourses viewCourses = new ViewCourses();
        viewCourses.reziseText(view);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // I call this to resize.. am I right?

        /* on below line we are setting data
           to our views of recycler view item. */
        CourseModal modal = courseModalArrayList.get(position);


        // Set the document ID for the current item
//        holder.setDocumentId(modal.getDocumentId()); // Replace 'modal' with the appropriate model class

//        holder.studentNameRV.setText(modal.getStudentName());
        holder.studentCoreRV.setText(modal.getStudentCore());
        holder.studentSubjectRV.setText(modal.getStudentSubject());
        holder.studentHoursRV.setText(modal.getStudentHours());
        holder.date.setText(modal.getDate());

        // below line is to add on click listener for our recycler view item.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modal.setAdapterId(holder.getAdapterPosition());

                // on below line we are calling an intent.
                Intent i = new Intent(context, UpdateCourseActivity.class);

                // below we are passing all our values.
                i.putExtra("name", modal.getStudentName());
                i.putExtra("core", modal.getStudentCore());
                i.putExtra("subject", modal.getStudentSubject());
                i.putExtra("hours", modal.getStudentHours());
                i.putExtra("documentId", modal.getDocId());
                i.putExtra("description", modal.getDescrip());
//                System.out.println(modals.getView());
                // starting our activity.
                context.startActivity(i);
            }
        });
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<CourseModal> filterllist) {
        /* below line is to add our filtered
           list in our course array list. */
        courseModalArrayList = filterllist;
        /* below line is to notify our adapter
           as change in recycler view data. */
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        // returning the size of our array list
        System.out.println(courseModalArrayList);
        return courseModalArrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views and document ID.
        private TextView studentCoreRV, studentSubjectRV, studentHoursRV, date;
        private String documentId; // New variable to store document ID

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initializing our text views
//            studentNameRV = itemView.findViewById(R.id.displayName);
            studentCoreRV = itemView.findViewById(R.id.displayCore);
            studentSubjectRV = itemView.findViewById(R.id.displaySubject);
            studentHoursRV = itemView.findViewById(R.id.displayHours);
            date = itemView.findViewById(R.id.displayDate);

        }
    }
}