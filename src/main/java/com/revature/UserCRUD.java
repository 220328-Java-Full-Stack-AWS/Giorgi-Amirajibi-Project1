package com.revature;
import com.revature.connectivity.ConnectionManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserCRUD implements CRUDInterface<User>{
    @Override
    public User select(User user) {

        try {
            ResultSet resultSet = null;
            String sql = "SELECT ers_username FROM ers_users WHERE ers_username = ?";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setString(1,user.userName);
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
    public User insert(User user) {

        try {

            String sql = "INSERT INTO ers_user_roles (user_role) values (?)";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,user.userRole);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();

            String sql1 = "INSERT INTO ers_users (ers_username,ers_password,user_first_name,user_last_name,user_email,user_role_id) values (?,?,?,?,?,?)";
            preparedStatement = ConnectionManager.getConnection().prepareStatement(sql1);
            preparedStatement.setString(1,user.userName);
            preparedStatement.setString(2,user.password);
            preparedStatement.setString(3,user.firstName);
            preparedStatement.setString(4,user.lastName);
            preparedStatement.setString(5,user.email);
            preparedStatement.setInt(6,resultSet.getInt(1));
            preparedStatement.executeUpdate();

            System.out.println("Insertion Successful");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User update(User userToUpdate, User userToUpdateWith) {

        try {
            String sql = "UPDATE ers_users SET ers_username = ?, ers_password = ?, user_first_name = ?, user_last_name = ?, user_email = ? WHERE ers_username = ?";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setString(1,userToUpdateWith.userName);
            preparedStatement.setString(2,userToUpdateWith.password);
            preparedStatement.setString(3,userToUpdateWith.firstName);
            preparedStatement.setString(4,userToUpdateWith.lastName);
            preparedStatement.setString(5,userToUpdateWith.email);
            preparedStatement.setString(6,userToUpdate.userName);

            preparedStatement.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return userToUpdateWith;
    }

    @Override
    public User delete(User user) {

        try {
            String sql = "DELETE FROM ers_users WHERE ers_username = ?";
            PreparedStatement preparedStatement = ConnectionManager.getConnection().prepareStatement(sql);
            preparedStatement.setString(1,user.userName);
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

        return resultSet;
    }
}
