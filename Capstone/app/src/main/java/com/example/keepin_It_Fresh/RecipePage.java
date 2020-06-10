package com.example.keepin_It_Fresh;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;

public class RecipePage extends AppCompatActivity {

    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction ft;

    public FragmentManager getFm(){ return fm; }
    public Fragment core = new RecipeMain();
    public DrawerLayout navdrawer;
    public DrawerLayout getNd() {return navdrawer;}
    public Fragment getCore() {return core;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        NavigationView navigationView = findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(new NavDrawer(this));


        navdrawer = findViewById(R.id.recipe_drawer);
        ft = fm.beginTransaction();
        ft.replace(R.id.recipe_fragment_container, getCore());
        ft.commit();


    }

}
