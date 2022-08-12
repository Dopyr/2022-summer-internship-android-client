package com.example.mysqlapp;

public class Login {

    public static Integer id;
    public static Boolean isAdmin;

    Login() {


    }

    Login(Integer id, Boolean isAdmin) {

        this.id = id;
        this.isAdmin = isAdmin;

    }

    public Boolean isAdmin() {
        return isAdmin;
    }
}
