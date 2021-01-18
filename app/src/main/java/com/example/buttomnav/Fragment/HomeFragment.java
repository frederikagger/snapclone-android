package com.example.buttomnav.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.buttomnav.Model.Snap;
import com.example.buttomnav.R;
import com.example.buttomnav.Repository.Repository;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


public class HomeFragment extends Fragment {
    private ArrayAdapter<Snap> arrayAdapter;
    private ListView listView;
    private Repository repository = Repository.getInstance();
    private View view;
    private Context mContext;
    private ArrayList<Snap> snaps = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_home, container, false);
        return this.view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repository.getSnapsFromFirestore().addSnapshotListener((value, error) -> {
            snaps.clear();
            for (DocumentSnapshot snap : value.getDocuments()) {
                String image = snap.get("pathToImage").toString();
                String text = snap.get("text").toString();
                Date created = snap.getDate("created");
                snaps.add(new Snap(image, text, created));
            };
            Collections.sort(snaps);
            setArrayToView(snaps);
            arrayAdapter.notifyDataSetChanged();
            changeFragmentOnItemClick();
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    public void changeFragmentOnItemClick() {
        this.listView.setOnItemClickListener((parent, view, position, id) -> {
            Fragment imageFragment = new ImageFragment();
            Bundle args = new Bundle();
            args.putString("path", this.snaps.get(position).getPathToImage());
            imageFragment.setArguments(args);
            getFragmentManager().beginTransaction().replace(R.id.flFragment, imageFragment).commit();
        });
    }


    public void setArrayToView(ArrayList snaps) {
        listView = (ListView) this.view.findViewById(R.id.snapListview);
        arrayAdapter = new ArrayAdapter<>(this.mContext, android.R.layout.simple_list_item_1, snaps);
        listView.setAdapter(arrayAdapter);
    }
}