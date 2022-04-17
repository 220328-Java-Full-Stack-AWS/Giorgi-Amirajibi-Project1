package com.revature;

public class User extends AbstractUser{
    public User(){

    }
    public User(String username, String password, String firstname, String lastname, String email){
        this.username = username;
        this.password = password;
        this.firstName = firstname;
        this.lastName = lastname;
        this.email = email;
        this.userRole = "Regular";
    }

    @Override
    public String toString() {
        return "Username : " + username + "\n" + "First Name: " + firstName + "\n" + "Last Name: " + lastName + "\n" + "Email: " + email;
    }
}
