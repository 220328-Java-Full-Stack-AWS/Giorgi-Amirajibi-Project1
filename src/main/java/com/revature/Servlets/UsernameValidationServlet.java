package com.revature.Servlets;
import com.revature.Connectivity.ConnectionManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/UsernameValidator")
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
                //System.out.println(resultSet.getString(resultSet.findColumn("ers_username")));
                resp.setContentType("text/plain");
                PrintWriter out = resp.getWriter();
                out.println(1);
            }
            else{
                resp.setContentType("text/plain");
                PrintWriter out = resp.getWriter();
                out.println(0);
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
