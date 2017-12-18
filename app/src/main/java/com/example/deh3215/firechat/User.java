package com.example.deh3215.firechat;

/**
 * Created by deh3215 on 2017/12/17.
 */

public class User {
    private String name ;
    private int age ;

    public User() {
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

//    public int getCount()   {
//        return count;
//    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
