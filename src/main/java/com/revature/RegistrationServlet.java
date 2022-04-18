package com.revature;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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


        try {
            resp.getWriter().println(username);
            resp.getWriter().println(password);
            resp.getWriter().println(firstName);
            resp.getWriter().println(lastName);
            resp.getWriter().println(email);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
