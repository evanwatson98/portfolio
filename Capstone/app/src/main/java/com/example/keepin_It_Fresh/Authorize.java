package com.example.keepin_It_Fresh;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Authorize extends AsyncTask<String, Void,String> {
    AlertDialog loginAlert;
    Context authorize;
    Authorize(Context ctx){
        authorize = ctx;
    }

    @Override
    protected void onPreExecute() {
        loginAlert = new AlertDialog.Builder(authorize).create();
        loginAlert.setTitle("Login Status");

    }

    @Override
    protected void onPostExecute(String loginMessage) {
        loginAlert.setMessage(loginMessage);


        if (loginMessage.contains("Login Success!")) {
            Intent onLoginCheck = new Intent();
            onLoginCheck.setClass(authorize.getApplicationContext(), onLoginCheck2.class);
            authorize.startActivity(onLoginCheck);
        }
        if (loginMessage.contains("Insert Successful")) {
            Intent MainActivity = new Intent();
            MainActivity.setClass(authorize.getApplicationContext(), MainLogin.class);
            authorize.startActivity(MainActivity);
        }
        if (loginMessage.contains("Reset Successful")) {
            Intent MainActivity = new Intent();
            MainActivity.setClass(authorize.getApplicationContext(), MainLogin.class);
            authorize.startActivity(MainActivity);
        }

    }
    @Override
    protected String doInBackground(String... voids) {
        String type = voids[0];
        //String loginFinal = "";
        String googleReg =  "https://cgi.sice.indiana.edu/~team39/Team39GoogleRegister.php";
        String reg = "https://cgi.sice.indiana.edu/~team39/team39Register.php";
        String phpURL = "https://cgi.sice.indiana.edu/~team39/team39Login.php";
        String phpForgot = "https://cgi.sice.indiana.edu/~team39/team39ForgotPassword.php";
        String reset = "https://cgi.sice.indiana.edu/~team39/reset.php";
        String scan = "https://cgi.sice.indiana.edu/~team39/scannerAddFood.php";
        if (type.equals("login")) {

            try {
                String email = voids[1];
                String password = voids[2];
                URL url = new URL(phpURL);
                HttpURLConnection phpWebsite = (HttpURLConnection) url.openConnection();
                phpWebsite.setRequestMethod("POST");
                phpWebsite.setDoInput(true);
                phpWebsite.setDoOutput(true);

                OutputStream loginOut = phpWebsite.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(loginOut, "UTF-8"));
                String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                writer.write(data);
                writer.flush();
                writer.close();
                loginOut.close();

                InputStream loginIn = phpWebsite.getInputStream();
                BufferedReader phpReader = new BufferedReader(new InputStreamReader(loginIn, "ISO-8859-1"));
                String content = "";
                String line = "";
                while ((content = phpReader.readLine()) != null) {
                    line += content;

                }
                phpReader.close();
                loginIn.close();
                phpWebsite.disconnect();

                return line;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("register")){

            try {
                String fullname = voids[1];
                String email = voids [2];
                String username = voids[3];
                String password = voids[4];
                URL url = new URL(reg);
                HttpURLConnection phpWebsite = (HttpURLConnection) url.openConnection();
                phpWebsite.setRequestMethod("POST");
                phpWebsite.setDoInput(true);
                phpWebsite.setDoOutput(true);

                OutputStream loginOut = phpWebsite.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(loginOut, "UTF-8"));
                String data = URLEncoder.encode("fullname", "UTF-8") +"="+ URLEncoder.encode(fullname, "UTF-8") +"&"+ URLEncoder.encode("email", "UTF-8") +"="+ URLEncoder.encode(email, "UTF-8")+"&"+URLEncoder.encode("username", "UTF-8") +"="+ URLEncoder.encode(username, "UTF-8") +"&"+URLEncoder.encode("password", "UTF-8") +"="+ URLEncoder.encode(password, "UTF-8");
                System.out.println("THE DATA WRITTEN TO THE FILE IS: "+data);
                writer.write(data);
                writer.flush();
                writer.close();
                loginOut.close();

                InputStream loginIn = phpWebsite.getInputStream();
                BufferedReader phpReader = new BufferedReader(new InputStreamReader(loginIn, "ISO-8859-1"));
                String content = "";
                String line="";
                while ((content = phpReader.readLine()) != null) {
                    line += content;

                }
                phpReader.close();
                loginIn.close();
                phpWebsite.disconnect();

                return line;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("forgot")){

            try {
                String email = voids [1];
                URL url = new URL(phpForgot);
                HttpURLConnection phpWebsite = (HttpURLConnection) url.openConnection();
                phpWebsite.setRequestMethod("POST");
                phpWebsite.setDoInput(true);
                phpWebsite.setDoOutput(true);

                OutputStream loginOut = phpWebsite.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(loginOut, "UTF-8"));
                String data = URLEncoder.encode("email", "UTF-8") +"="+ URLEncoder.encode(email, "UTF-8");
                writer.write(data);
                writer.flush();
                writer.close();
                loginOut.close();

                InputStream loginIn = phpWebsite.getInputStream();
                BufferedReader phpReader = new BufferedReader(new InputStreamReader(loginIn, "ISO-8859-1"));
                String content = "";
                String line="";
                while ((content = phpReader.readLine()) != null) {
                    line += content;

                }
                phpReader.close();
                loginIn.close();
                phpWebsite.disconnect();

                return line;

            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(type.equals("reset")){

        try {
            String email = voids [1];
            String password = voids [2];
            String token = voids [3];
            URL url = new URL(reset);
            HttpURLConnection phpWebsite = (HttpURLConnection) url.openConnection();
            phpWebsite.setRequestMethod("POST");
            phpWebsite.setDoInput(true);
            phpWebsite.setDoOutput(true);

            OutputStream loginOut = phpWebsite.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(loginOut, "UTF-8"));
            String data = URLEncoder.encode ("email", "UTF-8") +"="+ URLEncoder.encode(email, "UTF-8")+"&"+URLEncoder.encode ("password", "UTF-8") +"="+ URLEncoder.encode(password, "UTF-8")+"&"+URLEncoder.encode ("token", "UTF-8") +"="+ URLEncoder.encode(token, "UTF-8");
            writer.write(data);
            writer.flush();
            writer.close();
            loginOut.close();

            InputStream loginIn = phpWebsite.getInputStream();
            BufferedReader phpReader = new BufferedReader(new InputStreamReader(loginIn, "ISO-8859-1"));
            String content = "";
            String line="";
            while ((content = phpReader.readLine()) != null) {
                line += content;

            }
            phpReader.close();
            loginIn.close();
            phpWebsite.disconnect();
            ;
            return line;

        } catch (IOException e) {
            e.printStackTrace();
        }

        }else if(type.equals("google")) {

            try {
                String fullname = voids[1];
                String email = voids[2];
                String username = voids[3];

                String googleID = voids[4];
                System.out.println(fullname + email+username+googleID);
                GlobalVars.getInstance().username = username;
                URL url = new URL(googleReg);
                HttpURLConnection phpWebsite = (HttpURLConnection) url.openConnection();
                phpWebsite.setRequestMethod("POST");
                phpWebsite.setDoInput(true);
                phpWebsite.setDoOutput(true);

                OutputStream loginOut = phpWebsite.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(loginOut, "UTF-8"));
                String data = URLEncoder.encode("fullname", "UTF-8") + "=" + URLEncoder.encode(fullname, "UTF-8") + "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" + URLEncoder.encode("googleID", "UTF-8") + "=" + URLEncoder.encode(googleID, "UTF-8");
                writer.write(data);
                writer.flush();
                writer.close();
                loginOut.close();

                InputStream loginIn = phpWebsite.getInputStream();
                BufferedReader phpReader = new BufferedReader(new InputStreamReader(loginIn, "ISO-8859-1"));
                String content = "";
                String line = "";
                while ((content = phpReader.readLine()) != null) {
                    line += content;

                }
                phpReader.close();
                loginIn.close();
                phpWebsite.disconnect();

                return line;

            } catch (IOException e) {
                e.printStackTrace();
            }


        }


        return type;
    }}

