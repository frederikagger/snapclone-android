package com.example.buttomnav.Fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.buttomnav.R;
import com.example.buttomnav.Repository.Repository;

import java.io.File;
import java.io.IOException;

public class ImageFragment extends Fragment {
    private View view;
    private ImageView imageView;
    private Repository repository = Repository.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_image, container, false);
        setFragment();
        return view;
    }

    public void setFragment() {
        this.imageView = (ImageView) view.findViewById(R.id.image);
        String path = getArguments().getString("path");
        try {
            File localFile = File.createTempFile("images", "jpg");
            repository.getStorageRef(path).getFile(localFile)
                    .addOnSuccessListener(taskSnapshot -> {
                        imageView.setImageBitmap(BitmapFactory.decodeFile(localFile.getAbsolutePath()));
                        new Handler().postDelayed(() -> imageView.setVisibility(View.INVISIBLE), 5000);
                    });
        } catch (NullPointerException | IOException e) {
            System.out.println("The image doesnt exist");
        }
    }
}