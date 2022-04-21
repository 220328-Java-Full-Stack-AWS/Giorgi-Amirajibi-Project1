package com.revature.Servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.revature.Models.User;
import com.revature.DAO.UserDAO;
import org.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    String username;
    String password;
    String firstname;
    String lastname;
    String email;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        username = req.getParameter("username");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10,new SecureRandom());
        password = encoder.encode(req.getParameter("password"));
        firstname = req.getParameter("firstname");
        lastname = req.getParameter("lastname");
        email = req.getParameter("email");


        User currentUser = new User(username,password,firstname,lastname,email);
        JSONObject currentUserJson = new JSONObject(currentUser);
        UserDAO userDAO = new UserDAO();
        JSONObject response = userDAO.insert(currentUserJson);





        if (response.getString("status").equals("success")){
            resp.setStatus(200);
            resp.sendRedirect("login.html");
        }
        /*
        Map<String,String> user = new HashMap<>();
        user.put("username", req.getParameter("username"));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10,new SecureRandom());
        user.put("password", encoder.encode(req.getParameter("password")));

        user.put("firstname", req.getParameter("firstname"));
        user.put("lastname", req.getParameter("lastname"));
        user.put("email", req.getParameter("email"));

         */





        /*
        else{
            throw new UnableToRegisterException();
        }*/

    }
}
