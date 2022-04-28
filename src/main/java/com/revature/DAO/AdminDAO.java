package com.revature.DAO;

import com.revature.Connectivity.ConnectionManager;
import com.revature.Interfaces.CRUDInterface;
import com.revature.Models.User;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO implements CRUDInterface<JSONObject> {
    @Override
    public JSONObject select(JSONObject jsonObject) {
        return jsonObject;
    }

    @Override
    public JSONObject insert(JSONObject jsonObject) {
        return null;
    }

    @Override
    public JSONObject update(JSONObject jsonObject) {
        return null;
    }

    @Override
    public JSONObject delete(JSONObject jsonObject) {
        return null;
    }

    @Override
    public List<JSONObject> selectAll() {
        List<JSONObject> jsonObjectArrayList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM ers_users";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                User currentUser = new User();
                currentUser.setUsername(resultSet.getString(resultSet.findColumn("ers_username")));
                currentUser.setFirstname(resultSet.getString(resultSet.findColumn("user_first_name")));
                currentUser.setLastname(resultSet.getString(resultSet.findColumn("user_last_name")));
                currentUser.setEmail(resultSet.getString(resultSet.findColumn("user_email")));
                currentUser.setUserRoleId(resultSet.getInt(resultSet.findColumn("user_role_id")));
                JSONObject jsonObject = new JSONObject(currentUser);
                jsonObject.put("ers_user_id",resultSet.getString(resultSet.findColumn("ers_user_id")));
                jsonObjectArrayList.add(jsonObject);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }


        return jsonObjectArrayList;
    }

}
