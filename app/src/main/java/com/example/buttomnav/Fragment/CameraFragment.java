package com.example.buttomnav.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buttomnav.Model.Snap;
import com.example.buttomnav.R;
import com.example.buttomnav.Repository.Repository;

public class CameraFragment extends Fragment {
    private static final int CAMERA_REQUEST = 1888;
    private static final int CAMERA_ROLL_REQUEST = 1999;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int MY_CAMERA_ROLL_PERMISSION_CODE = 101;
    private ImageView imageView;
    private TextView textView;
    private AlertDialog alertDialog;
    private EditText editText;
    private Repository repository = Repository.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createAlertDialog();
        setAlertDialog();
        imageView = (ImageView) getView().findViewById(R.id.imageView);
        textView = (TextView) getView().findViewById(R.id.textview);
        Button cameraButton = (Button) getView().findViewById(R.id.cameraButton);
        Button cameraRollButton = (Button) getView().findViewById(R.id.cameraRollButton);
        Button sendButton = (Button) getView().findViewById(R.id.sendButton);
        imageView.setOnClickListener(e -> alertDialog.show());
        cameraButton.setOnClickListener(e -> openCamera());
        cameraRollButton.setOnClickListener(e -> openCameraRoll());
        sendButton.setOnClickListener(e -> send());
    }

    public void createAlertDialog() {
        alertDialog = new AlertDialog.Builder(getContext()).create();
        editText = new EditText(getContext());
        alertDialog.setTitle("Edit text");
        alertDialog.setView(editText);
    }

    public void setAlertDialog() {
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "SAVE TEXT",
                (dialog, which) -> textView.setText(editText.getText()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(getContext(), "Camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
        if (requestCode == CAMERA_ROLL_REQUEST && resultCode == Activity.RESULT_OK) {
            imageView.setImageURI(data.getData());
        }
    }

    public void openCamera() {
        if (getContext().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        } else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    public void openCameraRoll() {
        if (getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_CAMERA_ROLL_PERMISSION_CODE);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, CAMERA_ROLL_REQUEST);
        }
    }

    public void send() {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        String path = repository.saveImageToStorage(bitmap);
        repository.saveSnapToFirestore(new Snap(path, textView.getText().toString()));
        imageView.setImageURI(null);
        textView.setText("");
    }
}