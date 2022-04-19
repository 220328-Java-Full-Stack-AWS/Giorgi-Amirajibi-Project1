package com.revature.Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Ping")
public class PingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //resp.setStatus(200);
        resp.setHeader("This is header key","This is also header");
        resp.setStatus(200);
        resp.getWriter().println("This is a test line.");
        //resp.sendRedirect("registration.html");
    }
}
