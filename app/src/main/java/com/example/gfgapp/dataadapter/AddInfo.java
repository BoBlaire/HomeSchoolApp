package com.example.gfgapp.dataadapter;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.gfgapp.MainActivity;
import com.example.gfgapp.ViewCourses;
import com.example.gfgapp.modal.CourseModal;
import com.example.gfgapp.modal.MainModal;
import com.example.gfgapp.modal.Modal;
import com.example.gfgapp.modal.YourData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddInfo {
    public void enterData(String name, String subject, String hours, String core, String df, String email, String descrip) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("subject", subject);
        user.put("hours", hours);
        user.put("core", core);
        user.put("df", df);
        user.put("email", email);
        user.put("description", descrip);

        // Add a new document with a generated ID
        db.collection("homeschool").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }


    public interface FirestoreCallback {
        void onCallback(ArrayList<CourseModal> arrayValue);
    }

    public void retrieveData(String userName, String userEmail, String userSubject, FirestoreCallback callback) {
        MainModal mainModal = MainModal.getInstance();


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference collectionRef = db.collection("homeschool");

        Query query = collectionRef
                .whereEqualTo("name", userName)
                .whereEqualTo("email", userEmail)
                .whereEqualTo("subject", userSubject);


        query.get().addOnCompleteListener(task -> {
            ArrayList<CourseModal> arrayValue = new ArrayList<>();
            if (task.isSuccessful()) {

                for (QueryDocumentSnapshot document : task.getResult()) {
                    YourData data = document.toObject(YourData.class);

                    // Access and organize the variables
                    String name = data.getName();
                    String subject = data.getSubject();
                    String hours = data.getHours();
                    String core = data.getCore();
                    String email = data.getEmail();
                    String date = data.getDf();
                    String desc = data.getDescription();
                    String docId = document.getId();

                    arrayValue.add(new CourseModal(name, subject, hours, core, desc, docId));
                }

                // Call the callback with the populated array.
                callback.onCallback(arrayValue);
            } else {
                // Handle the error
                Log.e("FirestoreQuery", "Error getting documents: ", task.getException());

                // Call the callback with null or an empty list in case of an error.
                callback.onCallback(new ArrayList<>());
            }
        });
    }
}
