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
import java.util.stream.Collectors;

@WebServlet("/selectUsers")
public class SelectUsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AdminDAO adminDAO = new AdminDAO();
        List<JSONObject> jsonObjectList = adminDAO.selectAll();
        JSONArray jsonArray = new JSONArray(jsonObjectList.toArray());
        resp.setStatus(200);
        resp.setHeader("json",jsonArray.toString());

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestData = req.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObject = new JSONObject(requestData);
        AdminDAO adminDAO = new AdminDAO();
        adminDAO.update(jsonObject);
        resp.setStatus(200);

    }
}
