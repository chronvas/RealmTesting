package com.example.chronvas.realmtesting;

import io.realm.RealmObject;

public class People extends RealmObject {
    private String Name;
    private int Age;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }
}
