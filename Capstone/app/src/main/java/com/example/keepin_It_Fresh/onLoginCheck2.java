package com.example.keepin_It_Fresh;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Objects;

//import javafx.stage.*;

public class onLoginCheck2 extends AppCompatActivity {
    TextView houseNumber;
    TextView userNumber;
    TextView messageNumber;
    String resp;
    String uID;

    //GoogleSignInClient mGoogleSignInClient;


    String hID = "";

    private static final String fileStorage = "userInformation.txt";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlogin_check);


        houseNumber = findViewById(R.id.loginhouseID);
        userNumber = findViewById(R.id.loginuserID);
        messageNumber = findViewById(R.id.messageBack);
        Intent intent = getIntent();
        final String emailBack = intent.getStringExtra(MainLogin.EXTRA_TITLE);


        String email = readFile().trim();
        System.out.println("Email Test: " + email);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String user = getUserId(email);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        GlobalVars vars = GlobalVars.getInstance();
        System.out.println("The userid is: " + userid);
        uID = userid;
        vars.userid = this.userid;

        save(userid, 0);


////////////////////////////////////////////////////////////////
///////   Save username information to inernal storage /////////
            //String userBack = getUserId(emailBack);


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
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {

                    final String myResponse = Objects.requireNonNull(response.body()).string();
                    final String[] messageSplit = myResponse.split("--");

                    onLoginCheck2.this.runOnUiThread(() -> {
                        houseNumber.setText(messageSplit[1]);
                        userNumber.setText(userid);
                        messageNumber.setText(messageSplit[0].trim());
                        String finalMessage = messageNumber.getText().toString();
                        String finalHouseid = houseNumber.getText().toString().trim();
                        System.out.println("finalMessage = " +finalHouseid);

                        if (finalMessage.contains("Home Found")) {
                            save(finalHouseid,1);
                            Intent intent1 = new Intent(onLoginCheck2.this, Shelf2.class);
                            startActivity(intent1);
                        }
                        if (finalMessage.contains("Not Found")) {
                            save("0",1);
                            Intent intent1 = new Intent(onLoginCheck2.this, AssignHome.class);
                            startActivity(intent1);
                        }
                    });

                }
            }


        });


    }
    public String readFile() {
        try {
            String fileName = "email.txt";
            FileInputStream fileInputStream = openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuffer = new StringBuilder();

            String lines;
            while ((lines = bufferedReader.readLine()) != null) {
                stringBuffer.append(lines + "\n");
            }
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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
                    FileNotFoundException e) {
                e.printStackTrace();
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


            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if (response.isSuccessful()) {
                    resp = Objects.requireNonNull(response.body()).string();
                    userid = resp;

                    onLoginCheck2.this.runOnUiThread(() -> {
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
            }}}}







