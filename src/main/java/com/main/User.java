package com.main;

public class User {
    private int userId;
    private String gender;
    private int age;
    private int occupation;

    public User(int userId, String gender, int age, int occupation) {
        this.userId = userId;
        this.gender = gender;
        this.age = age;
        this.occupation = occupation;
    }

    public int getUserId() {
        return userId;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public int getOccupation() {
        return occupation;
    }

}
