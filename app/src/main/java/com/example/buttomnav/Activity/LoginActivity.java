package com.example.buttomnav.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;

import com.example.buttomnav.Adapters.LoginAdapter;
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
        TabLayout tabLayout = findViewById(R.id.tablayout);
        ViewPager viewPager = findViewById(R.id.viewpager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final LoginAdapter loginAdapter = new LoginAdapter(getSupportFragmentManager(), 2, 2);
        viewPager.setAdapter(loginAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOnClickListener(v -> {
            System.out.println(v);

        });
    }

}