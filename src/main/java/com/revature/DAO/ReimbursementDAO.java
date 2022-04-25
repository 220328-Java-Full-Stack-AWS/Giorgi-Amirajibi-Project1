package com.revature.DAO;

import com.revature.Connectivity.ConnectionManager;
import com.revature.Interfaces.CRUDInterface;
import com.revature.Models.Reimbursement;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ReimbursementDAO implements CRUDInterface<JSONObject> {
    @Override
    public JSONObject select(JSONObject jsonObject) {
        return null;
    }

    @Override
    public JSONObject insert(JSONObject jsonObject) {
        try {
            String sql = "SELECT ers_username FROM ers_users WHERE ers_username = ?";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,jsonObject.getString("username"));
            ResultSet usernameSet = preparedStatement.getGeneratedKeys();
            if (usernameSet.next()){
                int userId = usernameSet.getInt(1);
                System.out.println("userId = " + userId);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public JSONObject update(JSONObject jsonObject) {

        return jsonObject;
    }

    @Override
    public JSONObject delete(JSONObject jsonObject) {
        return null;
    }

    @Override
    public List<JSONObject> selectAll() {
        return null;
    }
}
