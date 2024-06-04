package com.example.gfgapp.dataadapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.gfgapp.modal.StudentModal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddStudentDB {

    private static final String USER_COLLECTION = "students";
    private static final String NAME_FIELD = "name";
    private static final String GRADE_FIELD = "grade";
    private static final String EMAIL_FIELD = "email";

    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private Context context;

    public AddStudentDB(Context context) {
        this.context = context;
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void addUserInfo(String name, String grade, String email, Runnable onSuccess) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (currentUser != null) {
            db.collection(USER_COLLECTION).whereEqualTo(EMAIL_FIELD, email).whereEqualTo(NAME_FIELD, name)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult() != null && !task.getResult().isEmpty()) {
                                    Log.d(TAG, "Student with email " + email + " already exists.");
                                    Toast.makeText(context, "User already exists", Toast.LENGTH_SHORT).show();
                                } else {
                                    Map<String, Object> userData = new HashMap<>();
                                    userData.put(NAME_FIELD, name);
                                    userData.put(GRADE_FIELD, grade);
                                    userData.put(EMAIL_FIELD, email);

                                    db.collection(USER_COLLECTION).add(userData)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                                    Toast.makeText(context, "User added successfully", Toast.LENGTH_SHORT).show();
                                                    onSuccess.run(); // Call the onSuccess Runnable
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error adding document", e);
                                                    Toast.makeText(context, "Error adding user", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            } else {
                                Log.e(TAG, "Error checking if student exists: ", task.getException());
                                Toast.makeText(context, "Error checking user existence", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public interface FirestoreCallback {
        void onCallback(ArrayList<StudentModal> arrayValue);
    }

    public void readStudents(String email, FirestoreCallback callback) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        ArrayList<StudentModal> studentModalArrayList = new ArrayList<>();

        if (currentUser != null) {
            CollectionReference userCollection = firestore.collection(USER_COLLECTION);

            userCollection.whereEqualTo(EMAIL_FIELD, email)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String name = document.getString(NAME_FIELD);
                                    String grade = document.getString(GRADE_FIELD);
                                    studentModalArrayList.add(new StudentModal(name, grade));
                                }
                                callback.onCallback(studentModalArrayList);
                            } else {
                                Log.e("StudentDBHandler", "Error getting user information from Firestore: " + task.getException().getMessage());
                                callback.onCallback(new ArrayList<>());
                            }
                        }
                    });
        }
    }
}
