package com.revature;

import com.revature.connectivity.ConnectionManager;

public class Driver {


    public static void main(String[] args) {
        User user = new User("Dodona","pass","Dodo","Lazarishvili","DodonaLazarishvili@gmail.com");
        UserCRUD userCRUD = new UserCRUD();

        //userCRUD.insert(user);
        userCRUD.read(user);
    }
}
