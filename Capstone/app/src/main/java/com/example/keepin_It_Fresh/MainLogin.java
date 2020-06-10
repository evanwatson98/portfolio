package com.example.keepin_It_Fresh;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainLogin extends AppCompatActivity {

    SignInButton GoogleButton;
    EditText user, pass;
    int userSignIn = 0;
    GoogleSignInClient googleClient;
    public static final String EXTRA_TITLE = "com.example.application.example.EXTRA_TITLE";

    /*
    private ImageButton homeBtn;
    private ImageButton catalogueBtn;
    private ImageButton cameraBtn;
    private ImageButton recipeButton;
    private ImageButton forumButton;
    private Button buttonLogin;
    private Button button_sign_out;
    private OkHttpClient postIUServer;
    private TextView status;
    TextView personName;
    Button testing;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        user = findViewById(R.id.user);
        pass = findViewById(R.id.pass);

        GoogleButton = findViewById(R.id.sign_in_button);
        //button_sign_out = (Button) findViewById(R.id.button_sign_out);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .requestEmail()
                .build();
        googleClient = GoogleSignIn.getClient(this, gso);
        GoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }


    public void buttonLogin(View view) {

        String email = user.getText().toString();
        Intent intent = new Intent(MainLogin.this, onLoginCheck.class);
        intent.putExtra(EXTRA_TITLE, email);

        String password = pass.getText().toString();
        String type = "login";
        Authorize ITP4auth = new Authorize(this);
        ITP4auth.execute(type, email, password);
        save(email,0);
//        save();
    }

    public void save(String item, int option) {

        FileOutputStream fos = null;
        if(option == 0){
            try {
                String fileStorage = "email.txt";
                fos = openFileOutput(fileStorage, MODE_PRIVATE);
//            String txt = email.getText().toString();
                fos.write(item.getBytes());
            } catch (
                    IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }}}}}


    public void OnRegister(View view) {
        startActivity(new Intent(this, Register.class));

    }

    public void OnForgot(View view) {
        startActivity(new Intent(this, ForgotPassword.class));

    }

    private void signIn() {
        Intent signInIntent = googleClient.getSignInIntent();
        startActivityForResult(signInIntent, userSignIn);
    }

    @Override
    public void onActivityResult(int request, int result, Intent credentials) {
        super.onActivityResult(request, result, credentials);

        if (request == userSignIn) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(credentials);
            result(task);
        }
    }

    private void result(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            startActivity(new Intent(MainLogin.this, GoogleProfile.class));
        } catch (ApiException e) {

            Log.w("Google Sign In Error", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(MainLogin.this, "Failed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();
            startActivity(new Intent(MainLogin.this, onLoginCheck.class));
        }
        super.onStart();
    }


    public void onClick(View v) {
        switch (v.getId()) {

            /*case R.id.button_sign_out:
                signOut();
                break;
            */
        }
    }


    private void signOut() {
        googleClient.signOut()
                .addOnCompleteListener(this, task -> {

                });
    }
}