package com.example.keepin_It_Fresh;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

//import javafx.stage.*;

public class onLoginCheck extends AppCompatActivity {
TextView houseNumber;
TextView userNumber;
TextView messageNumber;
    GoogleSignInClient mGoogleSignInClient;
    String resp;

    String hID = "";
    String uID;
    private static final String fileStorage = "userInformation.txt";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlogin_check);


        houseNumber = findViewById(R.id.loginhouseID);
        userNumber = findViewById(R.id.loginuserID);
        messageNumber = findViewById(R.id.messageBack);
        Intent intent = getIntent();
        final String emailBack = intent.getStringExtra(MainLogin.EXTRA_TITLE);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(onLoginCheck.this);

        System.out.println(acct);
        if (acct != null) {
            String fullname = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String email = acct.getEmail();
            String googleID = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
////////////////////////////////////////////////////////////////
///////   Save username information to inernal storage /////////
            //String userBack = getUserId(emailBack);

            //save(useridBack);

            String user = getUserId(email);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            String[] username = email.split("@", 2);

            String type = "google";
            Authorize ITP4auth = new Authorize(this);
            ITP4auth.execute(type, fullname, email, username[0], googleID);

        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();



        GlobalVars vars = GlobalVars.getInstance();
            System.out.println("The userid is: " + userid);
            uID = userid;
            vars.userid = this.userid;

            save(userid, 0);
        }
        String url = "https://cgi.sice.indiana.edu/~team39/homeCheck.php";
        RequestBody assignParameters = new FormBody.Builder()
                .add("userID", userid)
                .build();

        Request request = new Request.Builder()
                .post(assignParameters)
                .url(url)
                .build();
        OkHttpClient client = new OkHttpClient();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    final String myResponse = response.body().string();
                    final String[] messageSplit = myResponse.split("--");

                    onLoginCheck.this.runOnUiThread(() -> {
                        houseNumber.setText(messageSplit[1]);
                        userNumber.setText(userid);
                        messageNumber.setText(messageSplit[0].trim());
                        String finalMessage = messageNumber.getText().toString();
                        String finalHouseid = houseNumber.getText().toString().trim();
                        System.out.println("finalMessage = " +finalHouseid);

                        if (finalMessage.contains("Home Found")) {
                            save(finalHouseid,1);
                            Intent intent1 = new Intent(onLoginCheck.this, Shelf2.class);
                            startActivity(intent1);
                        }
                        if (finalMessage.contains("Not Found")) {
                            save("0",1);
                            Intent intent1 = new Intent(onLoginCheck.this, AssignHome.class);
                            startActivity(intent1);
                        }
                    });

                }
                           }


        });
        save(userid,0);

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
                    }
                }
            }
        }
        else{
            try {

                String finalHouseid = houseNumber.getText().toString().trim();
                item = finalHouseid;
                String fileStorage = "houseID.txt";
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
                    }
                }
            }}}

                String userid = "";
                String useridBack = "";



    public String getUserId(String email) {
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

            private Character st;

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if (response.isSuccessful()) {
                    resp = Objects.requireNonNull(response.body()).string();
                    userid = resp;
                    GlobalVars.getInstance().userid = userid;
                    onLoginCheck.this.runOnUiThread(() -> {
                    });
                }

            }
        });

        return userid;

    }
    public void overwrite(String item, int option) {

        FileOutputStream fos = null;
        if (option == 0) {
            try {
                String fileStorage = "houseID.txt";
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
                    }
                }
            }
        }


    }}


