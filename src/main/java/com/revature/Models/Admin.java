package com.revature.Models;

import com.revature.Abstract.AbstractUser;

public class Admin extends AbstractUser {

    public Admin(String username, String password, String firstname, String lastname, String email){
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.userRoleId = 2;
    }

    public void changeUserRole(){

    }

}
