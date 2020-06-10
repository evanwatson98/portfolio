package com.example.keepin_It_Fresh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Register extends AppCompatActivity {
    private Pattern pattern;
    private Matcher matcher;
    EditText em;
    EditText pass;
    EditText repass;
    EditText name;
    EditText user;
    Button back;
    TextView error;
    TextView errorLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        name = (EditText) findViewById(R.id.userFullname);
        em = (EditText) findViewById(R.id.userEmail);
        pass = (EditText) findViewById(R.id.userPassword);
        repass = (EditText) findViewById(R.id.repassword);
        user = (EditText) findViewById(R.id.userName);
        back = (Button) findViewById(R.id.buttonBack);
        error = (TextView) findViewById(R.id.error);
        errorLength = (TextView) findViewById(R.id.errorLength);


    }

    public void OnRegister(View view) {
        String fullname = name.getText().toString();
        String email = em.getText().toString();
        String username = user.getText().toString();
        String password = pass.getText().toString();
        String repassword = repass.getText().toString();


        int length = password.length();
        if (password.equals(repassword) && (length >= 8)) {
            Authorize ITP4auth = new Authorize(this);
            error.setText("");
            errorLength.setText("");
            String type = "register";
            ITP4auth.execute(type, fullname, email, username, password);
        } else if (!password.equals(repassword) && (length < 8)) {
            error.setText("ERROR: Passwords must match");
            errorLength.setText("ERROR: Password must be at least 8 characters long.");
        } else if (length < 8) {
            errorLength.setText("ERROR: Password must be at least 8 characters long.");
            error.setText("");
        } else if (!password.equals(repassword)) {
            error.setText("ERROR: Passwords must match");
            errorLength.setText("");
        }


    }

    public void buttonLogin(View view) {
        Intent intent = new Intent(view.getContext(), MainLogin.class);
        startActivity(intent);
    }
}
