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

@WebServlet("/UsernameValidationServlet")
public class UsernameValidationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");

        try {
            String sql = "SELECT * FROM ers_users WHERE ers_username = ?";
            Class.forName("org.postgresql.Driver");
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();

            if(resultSet.next()){
                System.out.println(resultSet.getString(resultSet.findColumn("ers_username")));
                resp.setContentType("text/html");
                PrintWriter out = resp.getWriter();
                out.println("Already Exists");
            }
            else{
                resp.setContentType("text/html");
                PrintWriter out = resp.getWriter();
                out.println("Available");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
