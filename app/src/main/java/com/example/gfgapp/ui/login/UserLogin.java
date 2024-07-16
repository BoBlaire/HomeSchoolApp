package com.example.gfgapp.ui.login;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gfgapp.passwords.ForgotPassword;
import com.example.gfgapp.modal.MainModal;
import com.example.gfgapp.R;
import com.example.gfgapp.modal.StudentModal;
import com.example.gfgapp.StudentView;
import com.example.gfgapp.DEAD.UserDBHandler;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserLogin extends AppCompatActivity {

    private static final int RC_SIGN_IN = 718;
    EditText userEmail, userPassword;

    TextView signup, forgotPassword;
    Button button, buttonSignUp;
    MainModal mainModal = MainModal.getInstance();
    private UserDBHandler userDBHandler;

    static StudentModal studentModal;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //creating variables
        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        button = findViewById(R.id.buttonLogin);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        signup = findViewById(R.id.signup);
        forgotPassword = findViewById(R.id.forgotPassword);


        // Set the dimensions of the sign-in button.
//        SignInButton signInButton = findViewById(R.id.sign_in_button);
//        signInButton.setSize(SignInButton.SIZE_STANDARD);

//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//
//        // Build a GoogleSignInClient with the options specified by gso.
//        GoogleSignInClient client = GoogleSignIn.getClient(this, gso);


        //calling user database
//        userDBHandler = new UserDBHandler(UserLogin.this);

        signup.setOnClickListener(v -> {
            Intent i = new Intent(UserLogin.this, SignUp.class);
            startActivity(i);
        });

        forgotPassword.setOnClickListener(V -> {
            Intent i = new Intent(UserLogin.this, ForgotPassword.class);
            startActivity(i);
        });

        button.setOnClickListener(v -> {

            //getting text of our two fields for database
            String userEmailLogin = userEmail.getText().toString();
            String userPasswordLogin = userPassword.getText().toString();


            //try statement for login, it trys to login if not the catch says no email
            if (userEmailLogin.isEmpty() || userPasswordLogin.isEmpty()) {
                Toast.makeText(UserLogin.this, "Please Fill Out All Fields", Toast.LENGTH_SHORT).show();
            } else if (!userEmailLogin.isEmpty() || !userPasswordLogin.isEmpty()) {
                signInWithEmailAndPassword(userEmailLogin, userPasswordLogin);
            } else {
                Toast.makeText(UserLogin.this, "Something really went wrong..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            mainModal.setUserEmail(account.getEmail());


//            Cursor cursorEmail = userDBHandler.retrieveEmail(account.getEmail());


//            try {
//                @SuppressLint("Range") String strEmail = cursorEmail.getString(cursorEmail.getColumnIndex("email"));
//
//                if (!strEmail.equals(account.getEmail())) {
//                    userDBHandler.addUserInfo(account.getEmail(), account.getId());
//
//                }
//            } catch (Exception e) {
//                userDBHandler.addUserInfo(account.getEmail(), account.getId());
//            }


            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    //Change UI according to user data.
    public void updateUI(GoogleSignInAccount account) {

        if (account != null) {
            Toast.makeText(this, "You Signed In successfully", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, StudentView.class));

        } else {
            Toast.makeText(this, "You Didn't signed in", Toast.LENGTH_LONG).show();
        }

    }


    public UserLogin() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void signInWithEmailAndPassword(String email, String password) {

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // HoursAdapter successful
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            checkDatabaseForUser(user.getUid());
                        }
                    } else {
                        // HoursAdapter failed
                        // Handle the failure (e.g., show an error message)
                        Toast.makeText(UserLogin.this, "HoursAdapter failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkDatabaseForUser(String userId) {

        firebaseFirestore.collection("users").document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // User found in the Firestore database
                            // Do something with the user data

                            mainModal.setUserEmail(userEmail.getText().toString());

                            Intent i = new Intent(UserLogin.this, StudentView.class);
                            startActivity(i);
                        } else {
                            // User not found in the Firestore database
                            // Handle the case where the user is not registered
                            Toast.makeText(UserLogin.this, "User Not Found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Error accessing Firestore database
                        // Handle the error (e.g., show an error message)
                        Toast.makeText(UserLogin.this, "Error accessing Firestore: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
