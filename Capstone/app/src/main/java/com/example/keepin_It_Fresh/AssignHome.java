package com.example.keepin_It_Fresh;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class AssignHome extends AppCompatActivity {
    EditText joinHome;
    EditText createHome;
    GoogleSignInClient mGoogleSignInClient;
    Button submitCreate;
    Button submitJoin;
    String resp;

    String hID = "";
    String uID;

    TextView joinHomeError;

    //private static final String fileStorage = "userInformation.txt";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_home);

        setupViews();

        Intent intent = getIntent();
        final String emailBack = intent.getStringExtra(MainLogin.EXTRA_TITLE);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(AssignHome.this);

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


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            //save(useridBack);

            String user = getUserId(email);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GlobalVars vars = GlobalVars.getInstance();
            System.out.println("The userid is: "+userid);
            uID = userid;
            vars.userid = this.userid;
            OkHttpClient client = new OkHttpClient();
            String url2 = "https://cgi.sice.indiana.edu/~team39/profile.php";

            RequestBody assignParameters;


            //String userid = readFile();

            assignParameters = new FormBody.Builder()
                    .add("userid", userid)
                    .build();

            Request request = new Request.Builder()
                    .post(assignParameters)
                    .url(url2)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                                                @Override
                                                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                                    e.printStackTrace();
                                                }
                                                @Override
                                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                                    if (response.isSuccessful()) {
                                                        final String myResponse = response.body().string();
                                                        final String[] messageSplit = myResponse.split("//");
                                                        AssignHome.this.runOnUiThread(() -> {
                                                            System.out.println(myResponse);
                                                            vars.username=messageSplit[2].trim();
                                                        });
                                                    }
                                                }});
            save(userid, 0);
        }
        submitCreate.setOnClickListener(v -> {

            //joinHome.setText("Test!");

            OkHttpClient client = new OkHttpClient();

            String url = "http://cgi.soic.indiana.edu/~evwatson/capstone/insertHousehold.php";

            final String houseName = createHome.getText().toString();
            String uniqueToken = createToken();
            RequestBody reqBody = new FormBody.Builder()
                    .add("houseName", houseName)
                    .add("uniqueToken", uniqueToken)

                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(reqBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                private Character st;

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                    if (response.isSuccessful()) {
                        final String resp = Objects.requireNonNull(response.body()).string();
                        st = resp.charAt(0);

                        AssignHome.this.runOnUiThread(() -> {
                            if (st == '1') {

                                startActivity(new Intent(AssignHome.this, MainActivity.class));

                            }
                        });
                    } else {
                        createHome.setText("not running");
                    }
                }

            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            getHouseId(uniqueToken);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            insertHouseUser(v);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("house id save test: " + hID);

            save(hID,1);

            Intent intent1 = new Intent(v.getContext(), Shelf2.class);
            startActivity(intent1);
        });
        submitJoin.setOnClickListener(v -> {
            //joinHome.setText("Test!");

            final String uniqueToken = joinHome.getText().toString();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getHouseId(uniqueToken);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("houseID getting: " + hID);

            if(!hID.equals("None") && (!hID.isEmpty() && !uniqueToken.isEmpty())) {
                insertHouseUser(v);
            }
////                save(v, u[0]);
        });
    }

    public void insertHouseUser(View v){
        OkHttpClient client3 = new OkHttpClient();

        String url3 = "http://cgi.soic.indiana.edu/~team39/insertJoinHousehold.php";

        RequestBody reqBody3 = new FormBody.Builder()
                .add("houseID", hID)
                .add("userID", uID)

                .build();

        Request request3 = new Request.Builder()
                .url(url3)
                .post(reqBody3)
                .build();

        client3.newCall(request3).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }


            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if (response.isSuccessful()) {
                    resp = Objects.requireNonNull(response.body()).string();
                    //                            createHome.setText(u[0]);
                    AssignHome.this.runOnUiThread(() -> {

                    });
                } else {
                    createHome.setText("not running the insert");
                }
            }
        });
        save(hID, 1);
        Intent intent = new Intent(v.getContext(), Shelf2.class);
        startActivity(intent);
    }


    private void setupViews(){
        joinHome = findViewById(R.id.joinHome);
        createHome = findViewById(R.id.createHome);
        submitCreate = findViewById(R.id.submitCreate);
        submitJoin = findViewById(R.id.submitJoin);
        joinHomeError = findViewById(R.id.joinHomeError);
    }

    private String createToken(){

        return createHome.getText().toString().replace("'", "") + uID;

    }
    public void save(String item, int option) {

        FileOutputStream fos = null;
        if(option == 0){
        try {
            String fileStorage = "userInformation.txt";
            fos = openFileOutput(fileStorage, MODE_PRIVATE);
//            String txt = email.getText().toString();
            fos.write(item.getBytes());
        } catch (IOException e) {
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
    }

    String userid = "";
    //String useridBack= "";


    public String getUserId(String email) {
        OkHttpClient client = new OkHttpClient();


        String url2 = "http://cgi.soic.indiana.edu/~team39/selectUseridFromEmail.php";

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

                    AssignHome.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                }

            }
        });

        return userid;
    }

    public void getHouseId(String uniqueToken) {
        OkHttpClient client = new OkHttpClient();
        System.out.println("getting into getHouseID");

        String url = "http://cgi.soic.indiana.edu/~team39/selectHouseId2.php";

        RequestBody reqBody = new FormBody.Builder()
                .add("uniqueToken", uniqueToken)

                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(reqBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                System.out.println("FAIIIIILEEEEEEDDDDDDDDDd");

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if (response.isSuccessful()) {
                    final String resp = Objects.requireNonNull(response.body()).string();
                    System.out.println("resp: "+ resp);
                    hID = resp;
                    System.out.println("Received hid: " + hID);
//                            createHome.setText(u[0]);

                    AssignHome.this.runOnUiThread(() -> {

                    });
                } else {
                    System.out.println("Can't runnn");

                }
            }

        });

    }




}
