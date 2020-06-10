package com.example.keepin_It_Fresh;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Objects;


public class UserProfile extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    Button sign_out;
    ImageView googlephoto;
    TextView profileName;
    TextView profileEmail;
    TextView profileUsername;
    OkHttpClient client = new OkHttpClient();
    TextView profileJoindate;
    ImageButton drawer_button;

    /*
    Button recipeButton;
    TextView googlename;
    TextView googleemail;
    TextView id;
    TextView gname;
    TextView fname;
    TextView userGoogle;
    Button button_sign_out;
    private TextView Name, Email;
    ImageButton cameraButton;
    Button toshelfTEMP;
    ImageButton catalogueButton;
    GoogleSignInClient googleClient;
    private Button buttonLogin;
    private TextView status;
    ImageButton homeBtn;
    ImageButton profileBtn;
    ImageButton cameraBtn;
    ImageButton recipeBtn;
    ImageButton forumBtn;
    EditText user, pass;
    */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        NavigationView navigationView = findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(new NavDrawer(this));
        profileName = findViewById(R.id.profileName);
        profileUsername = findViewById(R.id.profileUsername);

        googlephoto = findViewById(R.id.profilePhoto);
        profileEmail = findViewById(R.id.profileEmail);
        sign_out = findViewById(R.id.button_sign_out2);
        //id = findViewById(R.id.id);
        drawer_button = findViewById(R.id.nav_button);
        drawer_button.setOnClickListener(v -> {
            ((DrawerLayout)findViewById(R.id.profilepagenavdrawer
            )).openDrawer(Gravity.LEFT);
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(UserProfile.this);
        if (acct != null) {

            Uri personPhoto = acct.getPhotoUrl();

            Glide.with(this).load(personPhoto).into(googlephoto);

            OkHttpClient client = new OkHttpClient();
        }

        String url = "https://cgi.sice.indiana.edu/~team39/profile.php";

        RequestBody assignParameters;


        String userid = readFile();
        if(userid==null){
            if(userid.equals("")) {
                userid = GlobalVars.getInstance().userid;
            }
        }
        assignParameters = new FormBody.Builder()
                .add("userid", userid)
                .build();



        Request request = new Request.Builder()
                .post(assignParameters)
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {

                    final String myResponse = Objects.requireNonNull(response.body()).string();
                    final String[] messageSplit = myResponse.split("//");
                    UserProfile.this.runOnUiThread(() -> {
                        System.out.println(myResponse);
                        profileName = findViewById(R.id.profileName);
                        profileUsername = findViewById(R.id.profileUsername);
                        profileEmail = findViewById(R.id.profileEmail);
                        profileJoindate = findViewById(R.id.profileJoin);

                        profileName.setText(messageSplit[0]);
                        profileEmail.setText(messageSplit[1]);
                        profileUsername.setText(messageSplit[2]);
                        profileJoindate.setText(messageSplit[3]);


                    });


                }


            }







});
        sign_out.setOnClickListener(view -> signOut());


    }

    private void signOut() {


        FileOutputStream fos = null;
        try {
            String fileStorage = "userInformation.txt";
            fos = openFileOutput(fileStorage, MODE_PRIVATE);
            fos.write("".getBytes());
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
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
                    Toast.makeText(UserProfile.this, "Successfully signed out", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UserProfile.this, MainActivity.class));
                    finish();
                });
        }



    private String readFile() {
        try {
            String fileName = "userInformation.txt";
            FileInputStream fileInputStream = openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuffer = new StringBuilder();

            String lines;
            while ((lines = bufferedReader.readLine()) != null) {
                stringBuffer.append(lines).append("\n");
            }
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }







    }
