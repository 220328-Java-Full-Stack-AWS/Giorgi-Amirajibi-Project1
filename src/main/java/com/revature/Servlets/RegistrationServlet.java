package com.revature.Servlets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.revature.Models.User;
import com.revature.DAO.UserDAO;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
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

        JSONObject receivedJSON = new JSONObject(req.getHeader("json"));

        username = receivedJSON.getString("username");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10,new SecureRandom());
        password = encoder.encode(receivedJSON.getString("password"));
        firstname = receivedJSON.getString("firstname");
        lastname = receivedJSON.getString("lastname");
        email = receivedJSON.getString("email");

        User currentUser = new User(username,password,firstname,lastname,email);
        JSONObject currentUserJson = new JSONObject(currentUser);
        UserDAO userDAO = new UserDAO();
        JSONObject response = userDAO.insert(currentUserJson);

        if (response.getString("status").equals("success")){
            resp.setStatus(200);
            resp.sendRedirect("./UI/login.html");
        }

    }
}
