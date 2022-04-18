package com.revature;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;

@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
    String username;
    String password;
    String firstName;
    String lastName;
    String email;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        username = req.getParameter("username");
        password = req.getParameter("password");
        firstName = req.getParameter("firstname");
        lastName = req.getParameter("lastname");
        email = req.getParameter("email");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10,new SecureRandom());
        password = encoder.encode(password);

        User newUser = new User(username,password,firstName,lastName,email);
        UserCRUD userCRUD = new UserCRUD();
        userCRUD.insert(newUser);


    }
}
