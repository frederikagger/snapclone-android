package com.example.buttomnav.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.TableLayout;

import com.example.buttomnav.Fragment.CameraFragment;
import com.example.buttomnav.Fragment.LoginFragment;
import com.example.buttomnav.Fragment.RegisterFragment;
import com.example.buttomnav.Fragment.SendFragment;
import com.example.buttomnav.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new LoginFragment()).commit();
        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setOnTabSelectedListener();

        TabLayout.TabLayoutOnPageChangeListener tabLayoutOnPageChangeListener = item -> {
            Fragment selecetedFragment = null;

            switch (item.getItemId()) {
                case R.id.camera:
                    selecetedFragment = new LoginFragment();
                    break;
                case R.id.send:
                    selecetedFragment = new RegisterFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flFragment, selecetedFragment).commit();
            return true;
        };
    }

}