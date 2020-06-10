package com.example.keepin_It_Fresh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;


public class ForgotPassword extends AppCompatActivity {

    EditText em;
    Button userPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword_page);
        em = findViewById(R.id.userEmail);
    }

    public void OnForgot(View view) {

        String email = em.getText().toString();

        String type = "forgot";
        Authorize ITP4auth = new Authorize(this);
        ITP4auth.execute(type, email);

        }

    public void buttonLogin(View view) {
        Intent intent = new Intent(view.getContext(), MainLogin.class);
        startActivity(intent);
    }
}
