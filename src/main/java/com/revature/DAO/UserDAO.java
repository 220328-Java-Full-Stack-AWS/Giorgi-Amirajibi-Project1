package com.revature.DAO;

import com.revature.Interfaces.CRUDInterface;
import com.revature.Connectivity.ConnectionManager;
import com.revature.Models.User;
import org.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements CRUDInterface<JSONObject> {

    @Override
    public JSONObject select(JSONObject jsonObject) {

        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10,new SecureRandom());
            String sql = "SELECT ers_username, ers_password, user_role_id FROM ers_users WHERE ers_username = ?";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setString(1,jsonObject.getString("username"));
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                if (encoder.matches(jsonObject.getString("password"),resultSet.getString(resultSet.findColumn("ers_password")))){
                    jsonObject.put("status","success");
                    jsonObject.put("user_role", resultSet.getString(resultSet.findColumn("user_role_id")));
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
    public JSONObject insert(JSONObject jsonObject) {

        try {

            int rowCount;
            String sql1 = "INSERT INTO ers_users (ers_username,ers_password,user_first_name,user_last_name,user_email,user_role_id) values (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql1);
            preparedStatement.setString(1,jsonObject.getString("username"));
            preparedStatement.setString(2,jsonObject.getString("password"));
            preparedStatement.setString(3,jsonObject.getString("firstname"));
            preparedStatement.setString(4,jsonObject.getString("lastname"));
            preparedStatement.setString(5,jsonObject.getString("email"));
            preparedStatement.setInt(6,1);

            rowCount = preparedStatement.executeUpdate();

            if (rowCount > 0){
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
    public JSONObject update(JSONObject jsonObject) {
        try {
            String sql = "UPDATE ers_users SET (ers_username,ers_password,user_first_name,user_last_name,user_email, user_role_id) = (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setString(1,jsonObject.getString("username"));
            preparedStatement.setString(2,jsonObject.getString("password"));
            preparedStatement.setString(3,jsonObject.getString("firstname"));
            preparedStatement.setString(4,jsonObject.getString("lastname"));
            preparedStatement.setString(5,jsonObject.getString("email"));
            preparedStatement.setInt(6,jsonObject.getInt("userRoleId"));
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    public static void updateUserRole(String username, int userRoleId){
        try {
            String sql = "UPDATE ers_users SET user_role_id = ? WHERE ers_username = ?";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1,userRoleId);
            preparedStatement.setString(2,username);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

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
    public List<JSONObject> selectAll() {
        ResultSet resultSet;
        List<JSONObject> userList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM ers_users";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
            while(resultSet.next()){
                User tempUser = new User();
                tempUser.setUsername(resultSet.getString(resultSet.findColumn("ers_username")));
                tempUser.setPassword(resultSet.getString(resultSet.findColumn("ers_password")));
                tempUser.setFirstname(resultSet.getString(resultSet.findColumn("user_first_name")));
                tempUser.setLastname(resultSet.getString(resultSet.findColumn("user_last_name")));
                tempUser.setEmail(resultSet.getString(resultSet.findColumn("user_email")));
                tempUser.setUserRoleId(resultSet.getInt(resultSet.findColumn("user_role_id")));
                JSONObject tempUserJson = new JSONObject(tempUser);
                userList.add(tempUserJson);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }


        return userList;
    }
}

