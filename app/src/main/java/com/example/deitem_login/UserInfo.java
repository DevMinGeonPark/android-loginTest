package com.example.deitem_login;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class UserInfo {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Users");
    DatabaseReference usersRef = ref.child("users");
    Map<String, User> users = new HashMap<>();

    public String email;
    public String name;
    public String phone;
    public String id;
    public String gender;

    public UserInfo(String id, String email, String name, String phone, String gender) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.id = id;
        this.gender = gender;
        setUser();
    }

    public void setUser() {
        DatabaseReference usersRef = ref.child(id);
        Map<String, String> users = new HashMap<>();
        users.put("name", name);
        users.put("email", email );
        users.put("phone",phone);
        users.put("gender",gender);
        usersRef.setValue(users);
    }

}