package com.revature.DAO;

import com.revature.Connectivity.ConnectionManager;
import com.revature.Interfaces.CRUDInterface;
import com.revature.Models.Reimbursement;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementAdminDAO implements CRUDInterface<JSONObject> {
    @Override
    public JSONObject select(JSONObject jsonObject) {
        return null;
    }

    @Override
    public JSONObject insert(JSONObject jsonObject) {

        try {
            int userId;
            int userRoleId;

            String sql = "SELECT ers_user_id, user_role_id FROM ers_users WHERE ers_username = ?";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, jsonObject.getString("username"));
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            userId = resultSet.getInt(resultSet.findColumn("ers_user_id"));
            userRoleId = resultSet.getInt(resultSet.findColumn("user_role_id"));

            String reimbSQL = "INSERT INTO ers_reimbursement (reimb_amount,reimb_submitted,reimb_description,reimb_author,reimb_status_id,reimb_type_id)" +
                    "VALUES(?,?,?,?,?,?)";
            PreparedStatement preparedStatement1 = ConnectionManager.getConnection().prepareStatement(reimbSQL);
            preparedStatement1.setInt(1,jsonObject.getInt("reimbAmount"));
            preparedStatement1.setTimestamp(2, Timestamp.valueOf(jsonObject.getString("reimbSubmitted")));
            preparedStatement1.setString(3,jsonObject.getString("reimbDescription"));
            preparedStatement1.setInt(4,userId);
            preparedStatement1.setInt(5,1);
            preparedStatement1.setInt(6,jsonObject.getInt("reimbTypeId"));
            preparedStatement1.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public JSONObject update(JSONObject jsonObject) {
        int reimbStatusId = jsonObject.getInt("reimbStatusId");

        try {
            String reimbResolverUserName = jsonObject.getString("reimbResolver");
            System.out.println("reimbResolver " + reimbResolverUserName);
            String getReimbResolverId = "SELECT ers_user_id FROM ers_users WHERE ers_username = ?";
            int reimbResolverId = 0;
            PreparedStatement preparedStatementForResolver = ConnectionManager.getConnection().prepareStatement(getReimbResolverId);
            preparedStatementForResolver.setString(1,reimbResolverUserName);
            ResultSet rs = preparedStatementForResolver.executeQuery();
            if (rs.next()){
                reimbResolverId = rs.getInt(rs.findColumn("ers_user_id"));
            }

            String sql = "UPDATE ers_reimbursement SET (reimb_resolved,reimb_receipt,reimb_resolver,reimb_status_id) = (?,?,?,?) WHERE reimb_id = ?";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setTimestamp(1,Timestamp.valueOf(jsonObject.getString("reimbResolved")));
            preparedStatement.setString(2, "Such a nice receipt");
            preparedStatement.setInt(3,reimbResolverId);
            preparedStatement.setInt(4,reimbStatusId);
            preparedStatement.setInt(5,jsonObject.getInt("reimbId"));
            preparedStatement.executeUpdate();
            jsonObject.put("status", "success");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    public JSONObject delete(JSONObject jsonObject) {
        try {
            String sql = "DELETE FROM ers_reimbursement WHERE reimb_id = ?";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1,Integer.parseInt(jsonObject.getString("reimbId")));
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public List<JSONObject> selectAll() {
        System.out.println("Wrong SelectAll method");
        return null;
    }

    public List<JSONObject> selectAll(String username) {
        List<JSONObject> reimbursementListJson = new ArrayList<>();
        try {
            String selectUserIdSQL = "SELECT ers_user_id, ers_username FROM ers_users";
            PreparedStatement ps = ConnectionManager.getConnection().prepareStatement(selectUserIdSQL);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
            int userId = rs.getInt(rs.findColumn("ers_user_id"));
            String dbUsername = rs.getString(rs.findColumn("ers_username"));

            String sql = "SELECT * FROM ers_reimbursement WHERE reimb_author = ?";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                    Reimbursement reimbursement = new Reimbursement();
                    reimbursement.setReimbId(resultSet.getInt(resultSet.findColumn("reimb_id")));
                    reimbursement.setReimbAmount(resultSet.getInt(resultSet.findColumn("reimb_amount")));
                    reimbursement.setReimbAuthor(resultSet.getInt(resultSet.findColumn("reimb_author")));
                    reimbursement.setReimbDescription(resultSet.getString(resultSet.findColumn("reimb_description")));
                    reimbursement.setReimbSubmitted(resultSet.getTimestamp(resultSet.findColumn("reimb_submitted")));
                    reimbursement.setReimbStatusId(resultSet.getInt(resultSet.findColumn("reimb_status_id")));
                    reimbursement.setReimbTypeId(resultSet.getInt(resultSet.findColumn("reimb_type_id")));

                    JSONObject reimbursementJson = new JSONObject(reimbursement);
                    reimbursementJson.put("reimbAuthorUserName", dbUsername);

                    switch (reimbursementJson.getInt("reimbStatusId")){
                        case 1: reimbursementJson.remove("reimbStatusId");
                            reimbursementJson.put("reimbStatus", "PENDING");
                            break;
                        case 2: reimbursementJson.remove("reimbStatusId");
                            reimbursementJson.put("reimbStatus", "DENIED");
                            break;
                        case 3: reimbursementJson.remove("reimbStatusId");
                            reimbursementJson.put("reimbStatus", "APPROVED");
                            break;
                    }
                    switch (reimbursementJson.getInt("reimbTypeId")){
                        case 1: reimbursementJson.remove("reimbTypeId");
                            reimbursementJson.put("reimbType", "LODGING");
                            break;
                        case 2: reimbursementJson.remove("reimbTypeId");
                            reimbursementJson.put("reimbType", "FOOD");
                            break;
                        case 3: reimbursementJson.remove("reimbTypeId");
                            reimbursementJson.put("reimbType", "TRAVEL");
                            break;
                    }
                    reimbursementListJson.add(reimbursementJson);
                }
            }
            return reimbursementListJson;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return reimbursementListJson;
    }
}
