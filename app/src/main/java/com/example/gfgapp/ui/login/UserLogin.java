package com.example.gfgapp.ui.login;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gfgapp.passwords.ForgotPassword;
import com.example.gfgapp.MainActivity;
import com.example.gfgapp.modal.MainModal;
import com.example.gfgapp.R;
import com.example.gfgapp.modal.StudentModal;
import com.example.gfgapp.StudentView;
import com.example.gfgapp.databases.UserDBHandler;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class UserLogin extends AppCompatActivity {

    private static final int RC_SIGN_IN = 718;
    EditText userEmail, userPassword;

    TextView signup, forgotPassword;
    Button button, buttonSignUp;
    MainModal mainModal = MainActivity.mainModal;
    private UserDBHandler userDBHandler;

    static StudentModal studentModal;

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
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        GoogleSignInClient client = GoogleSignIn.getClient(this, gso);


        //calling user database
        userDBHandler = new UserDBHandler(UserLogin.this);

        signup.setOnClickListener(v -> {
            Intent i = new Intent(UserLogin.this, SignUp.class);
            startActivity(i);
        });

        forgotPassword.setOnClickListener(V -> {
            // define Intent object with action attribute as ACTION_SEND
//            Intent intent = new Intent(Intent.ACTION_SEND);
//
//            // add three fields to intent using putExtra function
//            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailsend});
//            intent.putExtra(Intent.EXTRA_SUBJECT, "Password Reset");
//            intent.putExtra(Intent.EXTRA_TEXT, emailbody);
//
//            // set type of intent
//            intent.setType("message/rfc822");
//
//            // startActivity with intent with chooser as Email client using createChooser function
//            startActivity(Intent.createChooser(intent, "Choose an Email client :"));
            Intent i = new Intent(UserLogin.this, ForgotPassword.class);
            startActivity(i);
        });




//        click listener to login
        signInButton.setOnClickListener(v -> {
            try {


                Intent signInIntent = client.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);


            } catch (ArrayIndexOutOfBoundsException e) {
                Toast.makeText(UserLogin.this, "You need to sign-up before logging in with Google", Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnClickListener(v -> {

            //getting text of our two fields for database
            String userEmailLogin = userEmail.getText().toString();
            String userPasswordLogin = userPassword.getText().toString();

            //passing those two fields and getting our cursor back
            Cursor cursorEmail = userDBHandler.retrieveEmail(userEmailLogin);
            Cursor cursorPass = userDBHandler.retrievePassword(userPasswordLogin);

            //try statement for login, it trys to login if not the catch says no email
            try {

                //passing cursors to get an actual variable back
                @SuppressLint("Range") String strEmail = cursorEmail.getString(cursorEmail.getColumnIndex("email"));
                @SuppressLint("Range") String strPass = cursorPass.getString(cursorPass.getColumnIndex("password"));

                //Checking weather the login or signup is valid
                if (userEmailLogin.isEmpty() || userPasswordLogin.isEmpty()) {
                    Toast.makeText(UserLogin.this, "Please Fill Out All Fields", Toast.LENGTH_SHORT).show();
                } else if (!userEmailLogin.isEmpty() || !userPasswordLogin.isEmpty()) {
                    if (strEmail.equals(userEmailLogin) && strPass.equals(userPasswordLogin)) {
                        mainModal.setUserEmail(strEmail);

                        Toast.makeText(UserLogin.this, "Logged In!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(UserLogin.this, StudentView.class);
                        i.putExtra("name", userEmailLogin);
                        startActivity(i);
                    } else if (!strEmail.equals(userEmailLogin) || !strPass.equals(userPasswordLogin)) {
                        Toast.makeText(UserLogin.this, "Email Or Password Incorrect!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UserLogin.this, "Something really went wrong..", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {

                Toast.makeText(UserLogin.this, "No Account Attached To Email", Toast.LENGTH_SHORT).show();
            }
        });

//        //click listener for signup
//        buttonSignUp.setOnClickListener(v -> {
//
//            //getting text for cursor
//            String userEmailLogin = userEmail.getText().toString();
//            String userPasswordLogin = userPassword.getText().toString();
//
//            //passing text to cursor
//            Cursor cursorEmail = userDBHandler.retrieveEmail(userEmailLogin);
//            Cursor cursorPass = userDBHandler.retrievePassword(userPasswordLogin);
//
//            //trying to signup, and if theres an email already valid the try says email in use
//            try {
//                @SuppressLint("Range") String strEmail = cursorEmail.getString(cursorEmail.getColumnIndex("email"));
//
//                //Checking weather the login or signup is valid
//                if (userEmailLogin.isEmpty() || userPasswordLogin.isEmpty()) {
//                    Toast.makeText(UserLogin.this, "Please Fill Out All Fields", Toast.LENGTH_SHORT).show();
//                } else if (!userEmailLogin.isEmpty() || !userPasswordLogin.isEmpty()) {
//                    if (strEmail.equals(userEmailLogin)) {
//                        Toast.makeText(UserLogin.this, "This Email Already In Use", Toast.LENGTH_SHORT).show();
//                    } else if (strEmail.equals(userEmailLogin)) {
//                        mainModal.setUserEmail(strEmail);
//                        userDBHandler.addUserInfo(userEmailLogin, userPasswordLogin);
//
//                        Toast.makeText(UserLogin.this, "Signed Up!", Toast.LENGTH_SHORT).show();
//
//                        Intent i = new Intent(UserLogin.this, StudentView.class);
//                        startActivity(i);
//                    }
//                } else {
//                    Toast.makeText(UserLogin.this, "Something really went wrong..", Toast.LENGTH_SHORT).show();
//                }
//            } catch (Exception e) {
//
//                //Checking weather the login or signup is valid
//                if (userEmailLogin.isEmpty() || userPasswordLogin.isEmpty()) {
//                    Toast.makeText(UserLogin.this, "Please Fill Out All Fields", Toast.LENGTH_SHORT).show();
//                } else if (!userEmailLogin.isEmpty() || !userPasswordLogin.isEmpty()) {
//
//                    mainModal.setUserEmail(userEmailLogin);
//                    userDBHandler.addUserInfo(userEmailLogin, userPasswordLogin);
//
//                    Toast.makeText(UserLogin.this, "Signed Up!", Toast.LENGTH_SHORT).show();
//
//                    Intent i = new Intent(UserLogin.this, StudentView.class);
//                    startActivity(i);
//
//                } else {
//                    Toast.makeText(UserLogin.this, "Something really went wrong..", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            mainModal.setGoogleEmail(account.getEmail());
            System.out.println("account email: " + account.getEmail());

            Cursor cursorEmail = userDBHandler.retrieveEmail(account.getEmail());


            try {
                @SuppressLint("Range") String strEmail = cursorEmail.getString(cursorEmail.getColumnIndex("email"));

                if (!strEmail.equals(account.getEmail())) {
                    userDBHandler.addUserInfo(account.getEmail(), account.getId());

                }
            } catch (Exception e) {
                userDBHandler.addUserInfo(account.getEmail(), account.getId());
            }


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
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        if (account != null) {
//            Intent intent = new Intent(UserLogin.this, StudentView.class);
//            startActivity(intent);
//        }
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
}
