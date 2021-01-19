package com.example.buttomnav.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.buttomnav.R;
import com.example.buttomnav.Repository.Repository;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class FriendFragment extends Fragment {
    private ArrayAdapter<String> arrayAdapter;
    private ListView listView;
    private Repository repository = Repository.getInstance();
    private View view;
    private Context mContext;
    private ArrayList<String> usernames = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_friends, container, false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository.getUsernameFromFirestore().addSnapshotListener((value, error) -> {
            usernames.clear();
            try {
                for (DocumentSnapshot document : value.getDocuments()) {
                    String username = document.get("username").toString();
                    usernames.add(username);
                }
            } catch (NullPointerException e){
                e.printStackTrace();
            }
            Collections.sort(usernames);
            setArrayToView(usernames);
            arrayAdapter.notifyDataSetChanged();
        });
    }

    public void setArrayToView(ArrayList usernames) {
        listView = view.findViewById(R.id.usernamelist);
        arrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, usernames);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}