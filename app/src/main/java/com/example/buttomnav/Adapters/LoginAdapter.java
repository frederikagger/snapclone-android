package com.example.buttomnav.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.buttomnav.Fragment.LoginFragment;
import com.example.buttomnav.Fragment.RegisterFragment;

public class LoginAdapter extends FragmentPagerAdapter {
    private int totalTabs;

    public LoginAdapter(@NonNull FragmentManager fm, int behavior, int totalTabs) {
        super(fm, behavior);
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new LoginFragment();

            case 1:
                return new RegisterFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.totalTabs;
    }
}
