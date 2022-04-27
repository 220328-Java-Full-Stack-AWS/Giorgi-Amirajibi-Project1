package com.revature.Servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.Connectivity.ConnectionManager;
import com.revature.DAO.ReimbursementDAO;
import com.revature.Models.Reimbursement;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/reimbursement")
public class ReimbursementServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String requestData = req.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObject = new JSONObject(requestData);
        Reimbursement reimbursement = new Reimbursement();

        switch (jsonObject.getString("reimbType")){
            case "LODGING": reimbursement.setReimbTypeId(1);
                            break;
            case "FOOD": reimbursement.setReimbTypeId(2);
                         break;
            case "TRAVEL": reimbursement.setReimbTypeId(3);
                           break;
        }

        reimbursement.setReimbAmount(jsonObject.getInt("reimbAmount"));
        reimbursement.setReimbDescription(jsonObject.getString("reimbDescription"));
        reimbursement.setReimbSubmitted(Timestamp.valueOf(jsonObject.getString("reimbSubmitted")));

        JSONObject reimbursementJson = new JSONObject(reimbursement);
        reimbursementJson.put("username",jsonObject.getString("username"));

        ReimbursementDAO reimbursementDAO = new ReimbursementDAO();
        reimbursementDAO.insert(reimbursementJson);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getHeader("username");
        ReimbursementDAO reimbursementDAO = new ReimbursementDAO();
        List<JSONObject> list = reimbursementDAO.selectAll(username);
        JSONArray jsonArray = new JSONArray(list.toArray());
        resp.setHeader("json", jsonArray.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("reimbId",req.getHeader("json"));
        System.out.println(jsonObject);
        ReimbursementDAO reimbursementDAO = new ReimbursementDAO();
        reimbursementDAO.delete(jsonObject);
        resp.setHeader("status","success");

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestData = req.getReader().lines().collect(Collectors.joining());
        JSONObject jsonObject = new JSONObject(requestData);
        System.out.println(jsonObject);
    }
}
