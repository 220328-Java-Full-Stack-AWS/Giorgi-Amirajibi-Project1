package com.revature.Servlets;

import com.revature.DAO.AdminDAO;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/selectUsers")
public class SelectUsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        AdminDAO adminDAO = new AdminDAO();
        List<JSONObject> jsonObjectList = adminDAO.selectAll();
        System.out.println(jsonObjectList);
        JSONArray jsonArray = new JSONArray(jsonObjectList.toArray());
        resp.setStatus(200);
        resp.setHeader("json",jsonArray.toString());

    }
}
