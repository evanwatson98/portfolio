package com.example.keepin_It_Fresh;

import android.app.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.*;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import org.json.JSONObject;

public class NavDrawer implements NavigationView.OnNavigationItemSelectedListener {

    AppCompatActivity myContext;
    Switch ratingSwitch;
    Switch expirationSwitch;
    public NavDrawer(AppCompatActivity context){
        GlobalVars vars = GlobalVars.getInstance();
        myContext = context;
        NavigationView navview = context.findViewById(R.id.navigation_drawer);
        ratingSwitch = navview.getMenu().findItem(R.id.switch_rating).getActionView().findViewById(R.id.nav_drawer_rating_switch);
        expirationSwitch = navview.getMenu().findItem(R.id.switch_expiration).getActionView().findViewById(R.id.nav_drawer_shelf_switch);
        ratingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            toggleNotifications(isChecked,"rateNot", vars.userid);
        });
        expirationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> toggleNotifications(isChecked,"shelfNot", vars.userid));
        getUserSettings(vars.userid);
    }
    public void getUserSettings(String id){
        final RequestQueue requestQueue = Volley.newRequestQueue(myContext);

        JSONObject rootJson = new JSONObject();
        JSONObject myjson = new JSONObject();
        try {
            myjson.put("action", "getnot");

            myjson.put("data", id);
        } catch (Exception e){
            e.printStackTrace();
        }
        try {
            rootJson.put("input", myjson);
        } catch (Exception e){
            e.printStackTrace();
        }
        JsonObjectRequest MyJSONRequest = new JsonObjectRequest("http://cgi.sice.indiana.edu/~team39/api.php", rootJson, response -> {
            try {
                ratingSwitch.setChecked(response.get("rateNot").toString().equals("1"));
                expirationSwitch.setChecked(response.get("shelfNot").toString().equals("1"));
            } catch (Exception e){
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);

        requestQueue.add(MyJSONRequest);

    }


    public void toggleNotifications(boolean isChecked, String key, String id) {
        final RequestQueue requestQueue = Volley.newRequestQueue(myContext);

        JSONObject rootJson = new JSONObject();
        JSONObject myjson = new JSONObject();
        JSONObject datajson = new JSONObject();
        try {
            myjson.put("action", "updatenot");
            datajson.put("truth",isChecked);
            datajson.put("key",key);
            datajson.put("id",id);
            myjson.put("data", datajson);
        } catch (Exception e){
            e.printStackTrace();
        }
        try {
            rootJson.put("input", myjson);
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(rootJson);
        JsonObjectRequest MyJSONRequest = new JsonObjectRequest("http://cgi.sice.indiana.edu/~team39/api.php", rootJson, response -> {
            try {
                System.out.println(response);
            } catch (Exception e){
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);

        requestQueue.add(MyJSONRequest);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;

        switch(item.getItemId()){

            case R.id.shelf_button_drawer:
                intent = new Intent(myContext, Shelf2.class);
                myContext.startActivity(intent);
                break;
            case R.id.recipe_button_drawer:
                intent = new Intent(myContext, RecipePage.class);
                myContext.startActivity(intent);
                break;
            case R.id.forum_button_drawer:
                intent = new Intent(myContext, ForumPage.class);
                myContext.startActivity(intent);
                break;
            case R.id.profile_button_drawer:
                intent = new Intent(myContext, UserProfile.class);
                myContext.startActivity(intent);
                break;
            case R.id.switch_rating:
                ratingSwitch.setChecked(!ratingSwitch.isChecked());
                break;
            case R.id.switch_expiration:
                expirationSwitch.setChecked(!expirationSwitch.isChecked());
                break;
        }
        return false;
    }
}
