package com.revature;

import com.revature.connectivity.ConnectionManager;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements CRUDInterface<JSONObject> {

    @Override
    public JSONObject select(JSONObject jsonObject) {
        return null;
    }

    @Override
    public JSONObject insert(JSONObject user) {
        System.out.println(user);
        JSONObject status = new JSONObject();

        try {
            /*
            String sql = "INSERT INTO ers_user_roles (user_role) values (?)";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,user.userRole);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            */
            int rowCount;
            String sql1 = "INSERT INTO ers_users (ers_username,ers_password,user_first_name,user_last_name,user_email,user_role_id) values (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql1);
            preparedStatement.setString(1,user.getString("username"));
            preparedStatement.setString(2,user.getString("password"));
            preparedStatement.setString(3,user.getString("firstname"));
            preparedStatement.setString(4,user.getString("lastname"));
            preparedStatement.setString(5,user.getString("email"));
            preparedStatement.setInt(6,1);

            rowCount = preparedStatement.executeUpdate();
            System.out.println(rowCount);

            if (rowCount > 0){
                status.put("status","success");
            }
            else{
                status.put("status","failed");
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public JSONObject update(JSONObject a, JSONObject b) {
        return null;
    }

    @Override
    public JSONObject delete(JSONObject jsonObject) {
        return null;
    }

    @Override
    public ResultSet selectAll() {
        return null;
    }
}
