package com.example.keepin_It_Fresh;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;

public class ForumPage extends AppCompatActivity {

    FragmentManager fm = getSupportFragmentManager();

    public FragmentManager getFm(){ return fm; }
    public Fragment core = new ViewMainForum();
    public DrawerLayout navdrawer;
    public DrawerLayout getNd() {return navdrawer;}
    public Fragment getCore() {return core;}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_page);
        NavigationView navigationView = findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(new NavDrawer(this));
        navdrawer = findViewById(R.id.forum_drawer);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.forum_fragment_container, getCore());
        ft.commit();

    }
}
