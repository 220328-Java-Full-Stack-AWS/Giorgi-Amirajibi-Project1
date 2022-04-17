package com.revature;

public class User extends AbstractUser{
    public User(){

    }
    public User(String username, String password, String firstname, String lastname, String email){
        this.userName = username;
        this.password = password;
        this.firstName = firstname;
        this.lastName = lastname;
        this.email = email;
    }

}
