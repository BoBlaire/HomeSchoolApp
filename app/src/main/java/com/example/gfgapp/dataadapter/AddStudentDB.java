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

    // Firestore collection and field names
    private static final String USER_COLLECTION = "students";
    private static final String NAME_FIELD = "name";
    private static final String GRADE_FIELD = "grade";
    private static final String EMAIL_FIELD = "email";

    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private Context context;

    // Constructor initializes Firestore and FirebaseAuth instances
    public AddStudentDB(Context context) {
        this.context = context;
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    /**
     * Adds user info to Firestore.
     * @param name    The name of the student.
     * @param grade   The grade of the student.
     * @param email   The email of the student.
     * @param onSuccess A Runnable to execute on successful addition.
     */
    public void addUserInfo(String name, String grade, String email, Runnable onSuccess) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Check if the current user is authenticated
        if (currentUser != null) {
            // Query Firestore to check if a student with the same email and name already exists
            db.collection(USER_COLLECTION).whereEqualTo(EMAIL_FIELD, email).whereEqualTo(NAME_FIELD, name)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult() != null && !task.getResult().isEmpty()) {
                                    // Student already exists
                                    Log.d(TAG, "Student with email " + email + " already exists.");
                                    Toast.makeText(context, "User already exists", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Create a new student record
                                    Map<String, Object> userData = new HashMap<>();
                                    userData.put(NAME_FIELD, name);
                                    userData.put(GRADE_FIELD, grade);
                                    userData.put(EMAIL_FIELD, email);

                                    // Add the student record to Firestore
                                    db.collection(USER_COLLECTION).add(userData)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    // Successfully added the student record
                                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                                    Toast.makeText(context, "User added successfully", Toast.LENGTH_SHORT).show();
                                                    onSuccess.run(); // Call the onSuccess Runnable
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // Failed to add the student record
                                                    Log.w(TAG, "Error adding document", e);
                                                    Toast.makeText(context, "Error adding user", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            } else {
                                // Error occurred while checking if student exists
                                Log.e(TAG, "Error checking if student exists: ", task.getException());
                                Toast.makeText(context, "Error checking user existence", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    // Interface to handle Firestore callback
    public interface FirestoreCallback {
        void onCallback(ArrayList<StudentModal> arrayValue);
    }

    /**
     * Reads student data from Firestore.
     * @param email    The email of the student.
     * @param callback A callback to handle the retrieved student data.
     */
    public void readStudents(String email, FirestoreCallback callback) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        ArrayList<StudentModal> studentModalArrayList = new ArrayList<>();

        // Check if the current user is authenticated
        if (currentUser != null) {
            CollectionReference userCollection = firestore.collection(USER_COLLECTION);

            // Query Firestore to get student records with the specified email
            userCollection.whereEqualTo(EMAIL_FIELD, email)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                // Retrieve and process each student record
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String name = document.getString(NAME_FIELD);
                                    String grade = document.getString(GRADE_FIELD);
                                    studentModalArrayList.add(new StudentModal(name, grade));
                                }
                                // Pass the retrieved data to the callback
                                callback.onCallback(studentModalArrayList);
                            } else {
                                // Error occurred while retrieving student records
                                Log.e("StudentDBHandler", "Error getting user information from Firestore: " + task.getException().getMessage());
                                callback.onCallback(new ArrayList<>());
                            }
                        }
                    });
        }
    }
}
