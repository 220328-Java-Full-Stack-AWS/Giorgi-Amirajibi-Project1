package com.revature;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
    String username;
    String password;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        username = req.getParameter("username");
        password = req.getParameter("password");

        try {
            resp.getWriter().println(username);
            resp.getWriter().println(password);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
