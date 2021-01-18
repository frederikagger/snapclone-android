package com.example.buttomnav.Model;

import com.google.firebase.firestore.ServerTimestamp;
import com.google.firebase.firestore.auth.User;

import java.util.Date;

public class Snap implements Comparable<Snap> {
    private String pathToImage;
    private String text;
    @ServerTimestamp
    private Date created = null;
    private User user;

    public Snap(String pathToImage, String text) {
        this.pathToImage = pathToImage;
        this.text = text;
    }

    public Snap(String pathToImage, String text, Date created) {
        this.pathToImage = pathToImage;
        this.text = text;
        this.created = created;
    }

    public Date getCreated() {
        return created;
    }

    public String getPathToImage() {
        return pathToImage;
    }

    public void setPathToImage(String pathToImage) {
        this.pathToImage = pathToImage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public int compareTo(Snap o) {
        try {
            return o.created.compareTo(this.created);
        } catch (NullPointerException e){
            return 1;
        }
    }
}
