package com.example.buttomnav.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.buttomnav.Fragment.CameraFragment;
import com.example.buttomnav.Fragment.HomeFragment;
import com.example.buttomnav.Fragment.FriendFragment;
import com.example.buttomnav.Fragment.LogoutFragment;
import com.example.buttomnav.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavView = findViewById(R.id.bottomNavigationView);
        bottomNavView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selecetedFragment = null;

        switch (item.getItemId()) {
            case R.id.camera:
                selecetedFragment = new CameraFragment();
                break;
            case R.id.friends:
                selecetedFragment = new FriendFragment();
                break;
            case R.id.home:
                selecetedFragment = new HomeFragment();
                break;
            case R.id.logout:
                selecetedFragment = new LogoutFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flFragment, selecetedFragment).commit();
        return true;
    };
}