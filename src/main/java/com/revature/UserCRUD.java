package com.revature;
import com.revature.connectivity.ConnectionManager;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserCRUD implements CRUDInterface<JSONObject>{

    @Override
    public JSONObject select(JSONObject user) {

        try {
            ResultSet resultSet = null;
            String sql = "SELECT ers_username FROM ers_users WHERE ers_username = ?";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setString(1,user.getString("username"));
            if (preparedStatement.execute()){
                resultSet = preparedStatement.getResultSet();
            }

            resultSet.next();
            System.out.println(resultSet.getString(resultSet.findColumn("ers_username")));

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public JSONObject insert(JSONObject user) {
        JSONObject jsonObject = new JSONObject();
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
                jsonObject.put("status:","success");
            }
            else{
                jsonObject.put("status","failed");
            }

            return jsonObject;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public JSONObject update(JSONObject userToUpdate, JSONObject userToUpdateWith) {
            /*
                        NEED A LOGIC FOR UPDATING USER ROLE!
            */
        try {
            String sql = "UPDATE ers_users SET ers_username = ?, ers_password = ?, user_first_name = ?, user_last_name = ?, user_email = ? WHERE ers_username = ?";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setString(1,userToUpdateWith.getString("username"));
            preparedStatement.setString(2,userToUpdateWith.getString("password"));
            preparedStatement.setString(3,userToUpdateWith.getString("firstname"));
            preparedStatement.setString(4,userToUpdateWith.getString("lastname"));
            preparedStatement.setString(5,userToUpdateWith.getString("email"));
            preparedStatement.setString(6,userToUpdate.getString("username"));

            preparedStatement.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return userToUpdateWith;
    }

    @Override
    public JSONObject delete(JSONObject user) {

        try {
            String sql = "SELECT user_role_id FROM ers_users WHERE ers_username = ?";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setString(1,user.getString("username"));
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            resultSet.next();

            String sql1 = "DELETE FROM ers_users WHERE ers_username = ?";
            preparedStatement = ConnectionManager.getConnection().prepareStatement(sql1);
            preparedStatement.setString(1,user.getString("username"));
            preparedStatement.executeUpdate();

            String sql2 = "DELETE FROM ers_user_roles WHERE ers_user_role_id = ?";
            preparedStatement = ConnectionManager.getConnection().prepareStatement(sql2);
            preparedStatement.setInt(1,resultSet.getInt(resultSet.findColumn("user_role_id")));
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public ResultSet selectAll() {
        ResultSet resultSet = null;

        try {
            String sql = "SELECT * FROM ers_users";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
            while(resultSet.next()){
                System.out.println(resultSet.getString(resultSet.findColumn("ers_username")));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }


        return resultSet;
    }
}
