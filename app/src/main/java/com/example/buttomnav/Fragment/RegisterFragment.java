package com.example.buttomnav.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buttomnav.Model.User;
import com.example.buttomnav.R;
import com.example.buttomnav.Repository.Repository;

public class RegisterFragment extends Fragment {
    private final Repository repository = Repository.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button register = view.findViewById(R.id.RegisterButton);
        TextView nameTextview = view.findViewById(R.id.name);
        TextView emailTextview = view.findViewById(R.id.email);
        TextView passwordTextview = view.findViewById(R.id.password);
        TextView birthdayTextview = view.findViewById(R.id.birthday);
        register.setOnClickListener(v -> {
            String name = "" + nameTextview.getText();
            String email = "" + emailTextview.getText();
            String password = "" + passwordTextview.getText();
            String birthday = "" + birthdayTextview.getText();
            User user = new User(name, email, password, birthday);
            repository.createUser(user);
            repository.saveUser(user);
            repository.saveUsername(name);
            makeToast("User created!");
        });
    }

    public void makeToast(String text) {
        int duration = Toast.LENGTH_LONG;
        Toast.makeText(getContext(), text, duration).show();
    }
}