package com.revature.DAO;

import com.revature.Interfaces.CRUDInterface;
import com.revature.Connectivity.ConnectionManager;
import org.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements CRUDInterface<JSONObject> {

    @Override
    public JSONObject select(JSONObject jsonObject) {

        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10,new SecureRandom());
            String sql = "SELECT ers_username, ers_password FROM ers_users WHERE ers_username = ?";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setString(1,jsonObject.getString("username"));
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                if (encoder.matches(jsonObject.getString("password"),resultSet.getString(resultSet.findColumn("ers_password")))){
                    jsonObject.put("status","success");
                }
                else{
                    jsonObject.put("status","failed");
                }

            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }


        return jsonObject;
    }

    @Override
    public JSONObject insert(JSONObject user) {

        JSONObject status = new JSONObject();
        //System.out.println(user);
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

            if (rowCount > 0){
                user.put("status","success");
            }
            else{
                user.put("status","failed");
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public JSONObject update(JSONObject a, JSONObject b) {
        return null;
    }

    @Override
    public JSONObject delete(JSONObject jsonObject) {

        try {
            String sql = "DELETE FROM ers_users WHERE ers_username = ?";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setString(1,jsonObject.getString("username"));
            if (preparedStatement.executeUpdate() > 0) {
                jsonObject.put("status","success");
            }
            else{
                jsonObject.put("status","failed");
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    public ResultSet selectAll() {
        return null;
    }
}
