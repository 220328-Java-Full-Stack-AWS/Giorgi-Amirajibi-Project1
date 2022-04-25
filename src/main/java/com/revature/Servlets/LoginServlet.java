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

        JSONObject currentUser = new JSONObject(req.getHeader("json"));


        username = currentUser.getString("username");
        password = currentUser.getString("password");


        UserDAO userDAO = new UserDAO();
        JSONObject response = userDAO.select(currentUser);

        if (response.getString("status").equals("success")){
            resp.setStatus(200);
            resp.sendRedirect("./UI/userPage.html");
        }
        else {
            //exception
        }


    }
}
