package com.revature.Servlets;

import com.revature.DAO.UserDAO;
import org.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username;
        String password;

        username = req.getParameter("username");
        password = req.getParameter("password");

        JSONObject currentUserJson = new JSONObject();
        currentUserJson.put("username",username);
        currentUserJson.put("password", password);

        UserDAO userDAO = new UserDAO();
        userDAO.select(currentUserJson);


    }
}
