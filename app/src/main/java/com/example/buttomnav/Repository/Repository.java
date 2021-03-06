package com.example.buttomnav.Repository;

import android.graphics.Bitmap;
import android.os.AsyncTask;


import com.example.buttomnav.Model.Snap;
import com.example.buttomnav.Model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Repository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();
    private static Repository single_instance = null;

    public FirebaseUser getUser() {
        return user;
    }

    private Repository() {
    }

    public static Repository getInstance() {
        if (single_instance == null)
            single_instance = new Repository();
        return single_instance;
    }

    public Task<AuthResult> login(String email, String password) {
        return mAuth.signInWithEmailAndPassword(email, password);
    }

    public String saveImageToStorage(Bitmap bitmap) {
        String path = System.currentTimeMillis() + ".jpg";
        StorageReference imageRef = storageRef.child(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(e -> e.printStackTrace())
                .addOnSuccessListener(e -> System.out.println("Success!"));
        return path;
    }

    public StorageReference getStorageRef(String path) {
        if (!path.equals("")) {
            StorageReference pathReference = storageRef.child(path);
            try {
                return pathReference;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void saveSnap(Snap snap) {
        db.collection("snaps").add(snap);
    }

    public void createUser(User user) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword());
    }

    public void saveUser(User user) {
        db.collection("users").add(user);
    }

    public void saveUsername(String username) {
        HashMap<String, String> user = new HashMap<>();
        user.put("username", username);
        db.collection("username").add(user);
    }

    public CollectionReference getSnapsFromFirestore() {
        return db.collection("snaps");
    }

    public CollectionReference getUsersFromFirestore() {
        return db.collection("users");
    }

    public CollectionReference getUsernameFromFirestore() {
        return db.collection("username");
    }
}
