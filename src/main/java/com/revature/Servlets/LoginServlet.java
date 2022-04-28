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

        JSONObject currentUser = new JSONObject(req.getHeader("json"));
        UserDAO userDAO = new UserDAO();
        JSONObject response = userDAO.select(currentUser);

        if (response.getString("status").equals("success")){
            if (response.getInt("user_role") == 1){
                resp.setStatus(200);
                resp.setHeader("username",response.getString("username"));
                resp.sendRedirect("./UI/userPage.html");
            }
            else if (response.getInt("user_role") == 2){
                resp.setStatus(200);
                resp.setHeader("username",response.getString("username"));
                resp.sendRedirect("./UI/userPage.html");
            }
            else if (response.getInt("user_role") == 3){
                resp.setStatus(200);
                resp.setHeader("username",response.getString("username"));
                resp.sendRedirect("./UI/admin.html");
            }


        }
        else {
            //exception
        }


    }
}
