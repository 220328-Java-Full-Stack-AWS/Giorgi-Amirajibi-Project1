package com.revature.Models;

public class Admin extends AbstractUser{

    public Admin(String username, String password, String firstname, String lastname, String email){
        this.username = username;
        this.password = password;
        this.firstName = firstname;
        this.lastName = lastname;
        this.email = email;
        this.userRoleId = 2;
    }

}
