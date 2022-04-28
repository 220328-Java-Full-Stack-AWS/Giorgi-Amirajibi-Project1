package com.revature.DAO;

import com.revature.Connectivity.ConnectionManager;
import com.revature.Interfaces.CRUDInterface;
import com.revature.Models.User;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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

        int userId = jsonObject.getInt("userId");
        int userRole = 0;
        switch (jsonObject.getString("userRole")){
            case "Employee":
                userRole = 1;
                break;
            case "Financial Manager":
                userRole = 2;
                break;
            case "Admin":
                userRole = 3;
                break;
        }

        try {
            String sql = "UPDATE ers_users SET user_role_id = ? WHERE ers_user_id = ?";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1,userRole);
            preparedStatement.setInt(2,userId);
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
                switch (resultSet.getInt(resultSet.findColumn("user_role_id"))){
                    case 1: jsonObject.put("userRoleAsString","Employee");
                            break;
                    case 2: jsonObject.put("userRoleAsString","Financial Manager");
                        break;
                    case 3: jsonObject.put("userRoleAsString","Admin");
                        break;
                }
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
