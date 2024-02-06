package com.example.gfgapp.dataadapter;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.gfgapp.MainActivity;
import com.example.gfgapp.modal.CourseModal;
import com.example.gfgapp.modal.MainModal;
import com.example.gfgapp.modal.YourData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddUserInfo {

    public void enterData(String email, String password, String uid) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("password", password);
        user.put("uid", uid);

        DocumentReference documentReference = db.collection("users").document(uid);

        // Add a new document with a generated ID
        documentReference.set(user).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
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

    public void retrieveData(String userEmail, String userPassword) {


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference collectionRef = db.collection("homeschool");

        Query query = collectionRef
                .whereEqualTo("email", userEmail)
                .whereEqualTo("password", userPassword);


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
            } else {
                // Handle the error
                Log.e("FirestoreQuery", "Error getting documents: ", task.getException());

            }
        });
    }

}
