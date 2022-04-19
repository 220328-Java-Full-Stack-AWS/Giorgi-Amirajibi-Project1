package com.revature.Servlets;
import com.revature.Connectivity.ConnectionManager;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/EmailValidationServlet")
public class EmailValidationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String email = req.getParameter("email");

        try {
            String sql = "SELECT * FROM ers_users WHERE user_email = ?";
            Class.forName("org.postgresql.Driver");
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setString(1,email);
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();

            if(resultSet.next()){
                //System.out.println(resultSet.getString(resultSet.findColumn("user_email")));
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
