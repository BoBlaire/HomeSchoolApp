package com.example.gfgapp.dataadapter;

import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateInfo {

    private static final String TAG = "FirestoreUpdateHelper";

    public interface FirestoreUpdateCallback {
        void onSuccess();

        void onFailure(Exception e);
    }

    public static void updateDocument(String collectionName, String documentId, Map<String, Object> updates, FirestoreUpdateCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection(collectionName);
        DocumentReference documentRef = collectionRef.document(documentId);

        documentRef.update(updates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                        if (callback != null) {
                            callback.onSuccess();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.w(TAG, "Error updating document", e);
                        if (callback != null) {
                            callback.onFailure(e);
                        }
                    }
                });
    }

    public void deleteRecord(String documentId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Reference the specific document using the documentId
        DocumentReference documentReference = db.collection("homeschool").document(documentId);

        // Delete the document
        documentReference.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Handle successful deletion
                        System.out.println("Record deleted successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        // Handle failure
                        System.out.println("Error deleting record: " + e.getMessage());
                    }
                });
    }

}