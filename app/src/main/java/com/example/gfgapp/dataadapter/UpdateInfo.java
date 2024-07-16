package com.example.gfgapp.dataadapter;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class UpdateInfo {

    // Log tag for debugging
    private static final String TAG = "FirestoreUpdateHelper";

    // Callback interface for update operations
    public interface FirestoreUpdateCallback {
        void onSuccess(); // Called on successful update

        void onFailure(Exception e); // Called on update failure
    }

    /**
     * Updates a document in Firestore.
     * @param collectionName The name of the Firestore collection.
     * @param documentId     The ID of the document to update.
     * @param updates        A map containing the fields to update.
     * @param callback       The callback to notify the result.
     */
    public static void updateDocument(String collectionName, String documentId, Map<String, Object> updates, FirestoreUpdateCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance(); // Get Firestore instance
        CollectionReference collectionRef = db.collection(collectionName); // Reference to the collection
        DocumentReference documentRef = collectionRef.document(documentId); // Reference to the document

        // Update the document with the provided updates
        documentRef.update(updates)
                .addOnSuccessListener(aVoid -> {
                    // Log and notify on successful update
                    Log.d(TAG, "DocumentSnapshot successfully updated!");
                    if (callback != null) {
                        callback.onSuccess(); // Trigger callback success
                    }
                })
                .addOnFailureListener(e -> {
                    // Log and notify on update failure
                    Log.w(TAG, "Error updating document", e);
                    if (callback != null) {
                        callback.onFailure(e); // Trigger callback failure
                    }
                });
    }

    /**
     * Deletes a document from Firestore.
     * @param documentId The ID of the document to delete.
     */
    public void deleteRecord(String documentId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance(); // Get Firestore instance

        // Reference to the specific document to delete
        DocumentReference documentReference = db.collection("homeschool").document(documentId);

        // Delete the document
        documentReference.delete()
                .addOnSuccessListener(aVoid -> {
                    // Handle and log successful deletion
                    System.out.println("Record deleted successfully");
                })
                .addOnFailureListener(e -> {
                    // Handle and log deletion failure
                    System.out.println("Error deleting record: " + e.getMessage());
                });
    }
}
