package com.revature;

import com.revature.DAO.UserDAO;
import com.revature.Models.User;
import org.json.JSONObject;

public class Driver {


    public static void main(String[] args) {

        UserDAO.updateUserRole("Laflammex",1);
        System.out.println(new UserDAO().selectAll());

    }
}
