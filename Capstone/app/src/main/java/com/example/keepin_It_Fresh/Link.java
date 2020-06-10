package com.example.keepin_It_Fresh;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class Link extends AppCompatActivity {

    EditText pass2;
    EditText repass2;
    EditText tok;
    EditText email2;
    TextView error;
    TextView errorLength;
    EditText em;

    /*
    private Pattern pattern;
    private Matcher matcher;
    Button testing;
    EditText token;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token_page);

        em = findViewById(R.id.userEmail);
        email2 = findViewById(R.id.email2);
        pass2 = findViewById(R.id.pass2);
        repass2 = findViewById(R.id.repass2);
        error = findViewById(R.id.error);
        errorLength = findViewById(R.id.errorLength);
        tok = findViewById(R.id.token);


        // ATTENTION: This was auto-generated to handle app links.
        /*
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
         */
    }

             public void OnForgot(View view) {
                 String token = tok.getText().toString();
                 String email = email2.getText().toString();
                 String password = pass2.getText().toString();
                 String repass = repass2.getText().toString();

                 String type = "reset";
                 Authorize ITP4auth = new Authorize(this);
                 ITP4auth.execute(type, email, password, token);

             }
         }




