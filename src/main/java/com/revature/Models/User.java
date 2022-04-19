package com.revature.Models;

import com.revature.Abstract.AbstractUser;

public class User extends AbstractUser {
    public User(){

    }
    public User(String username, String password, String firstname, String lastname, String email){
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.userRoleId = 1;
    }

    @Override
    public String toString() {
        return "username : " + username + "\n" + "firstname: " + firstname + "\n" + "lastame: " + lastname + "\n" + "email: " + email;
    }
}
