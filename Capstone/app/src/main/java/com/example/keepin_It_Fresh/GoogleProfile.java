package com.example.keepin_It_Fresh;

import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

public class GoogleProfile extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    Button sign_out;
    TextView googlename;
    TextView googleemail;
    TextView userGoogle;
    ImageView googlephoto;
    Button button_sign_out;
    TextView id;
    Button toshelfTEMP;

    /*
    private ImageButton addFoodBtn;
    Button recipeButton;
    TextView gname;
    TextView fname;
    private TextView Name, Email;
    ImageButton cameraButton;
    ImageButton catalogueButton;
    GoogleSignInClient googleClient;



   New file for containing the users information

        {
            userinfo:

            deviceinfo:


        }

     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        sign_out = findViewById(R.id.button_sign_out);
        googlename = findViewById(R.id.name);
        googleemail = findViewById(R.id.email);
        id = findViewById(R.id.id);
        googlephoto = findViewById(R.id.profilePhoto);
        button_sign_out = findViewById(R.id.button_sign_out);
        toshelfTEMP = findViewById(R.id.toshelfTEMP);

        userGoogle = findViewById(R.id.userGoogle);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(GoogleProfile.this);
        if (acct != null) {
            String fullname = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String email = acct.getEmail();
            String googleID = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
////////////////////////////////////////////////////////////////
///////   Save username information to inernal storage /////////
            String user = getUserId(email);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            save(email,0);


            String[] username = email.split("@", 2);

            googlename.setText("Name: " + fullname);
            googleemail.setText("Email: " + email);
            id.setText("ID: " + googleID);
            userGoogle.setText("Username: " + username[0]);
//            Glide.with(this).load(personPhoto).into(googlephoto);


            String type = "google";
            Authorize ITP4auth = new Authorize(this);
            ITP4auth.execute(type, fullname, email, username[0], googleID);

        }
        toshelfTEMP.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), Shelf2.class);
            startActivity(intent);
        });

        sign_out.setOnClickListener(view -> signOut());


    }
    public void save(String item, int option) {

        FileOutputStream fos = null;
        if(option == 0){
            try {
                String fileStorage = "userInformation.txt";
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

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
                    Toast.makeText(GoogleProfile.this, "Successfully signed out", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(GoogleProfile.this, InsertUPC.class));
                    finish();
                });


    }

    String userid = "";

    String resp = "";

    public String getUserId(String email){
        OkHttpClient client = new OkHttpClient();


        String url2 = "http://cgi.soic.indiana.edu/~evwatson/capstone/selectUseridFromEmail.php";

        RequestBody reqBody2 = new FormBody.Builder()
                .add("email", email)
                .build();

        Request request2 = new Request.Builder()
                .url(url2)
                .post(reqBody2)
                .build();

        client.newCall(request2).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }


            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if (response.isSuccessful()) {
                    resp = Objects.requireNonNull(response.body()).string();
                    userid = resp;

                    GoogleProfile.this.runOnUiThread(() -> {
                    });
                }

            }
        });

        return userid;
    }
}
