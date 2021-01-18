package com.example.buttomnav.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.buttomnav.Activity.MainActivity;
import com.example.buttomnav.R;
import com.example.buttomnav.Repository.Repository;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
    private View view;
    private Context mContext;
    private Button loginButton;
    private EditText email;
    private EditText password;
    private Repository repository = Repository.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            changeActivity();
        }
        email = (EditText) view.findViewById(R.id.email);
        password = (EditText) view.findViewById(R.id.password);
        loginButton = (Button) view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(e -> login());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    public void login() {
        try {
            repository.login(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            changeActivity();
                        } else {
                            makeToast("Login failed");
                        }
                    });
        } catch (IllegalArgumentException e){
            makeToast("Please fill out password and email");
        }
    }

    public void changeActivity() {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void makeToast(String text) {
        int duration = Toast.LENGTH_LONG;
        Toast.makeText(mContext, text, duration).show();
    }
}