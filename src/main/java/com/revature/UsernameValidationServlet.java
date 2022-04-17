package com.revature;

import com.revature.connectivity.ConnectionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "UsernameValidationServlet", urlPatterns = {"/UsernameValidationServlet"})
public class UsernameValidationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");

        try {
            String sql = "SELECT * FROM ers_users WHERE ers_username = ?";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                resp.setContentType("text/html");
                PrintWriter out = resp.getWriter();
                out.print("Already Exists");
            }
            else{
                resp.setContentType("text/html");
                PrintWriter out = resp.getWriter();
                out.print("Available");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
