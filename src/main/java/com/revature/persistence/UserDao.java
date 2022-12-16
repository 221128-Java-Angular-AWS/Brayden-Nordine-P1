package com.revature.persistence;

import com.revature.exceptions.DuplicateUserException;
import com.revature.exceptions.IncorrectPasswordException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private Connection connection;

    public UserDao(){
        connection = ConnectionManager.getConnection();
    }

    //CRUD
    //Create new user
    //Read existing users: verify email/password, retrieve user data
    //Update an existing user, change password
    //Delete existing user

    //Create a new user
    public void create(User user) throws DuplicateUserException {
        try {
            String sql = "INSERT INTO users (email, password, role, firstname, lastname, address) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getRole());
            pstmt.setString(4, user.getFirstName());
            pstmt.setString(5, user.getLastName());
            pstmt.setString(6, user.getAddress());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            if(e.getMessage().contains("duplicate key value")){
                throw new DuplicateUserException("User with the given email already exists");
            }
        }
    }

    //Verify a given email and password, return the user if it is correct
    public User verify(String email, String password) throws UserNotFoundException, IncorrectPasswordException {
        try {
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet result = pstmt.executeQuery();

            if(!result.next()){
                throw new UserNotFoundException("No user with that email address was found");
            }

            if(!result.getString("password").equals(password)){
                throw new IncorrectPasswordException("Password is Incorrect");
            }

            User user = new User(result.getInt("user_id"), result.getString("email"),
                    result.getString("password"), result.getString("role"), result.getString("firstname"),
                    result.getString("lastname"), result.getString("address"));
            return user;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //retrieve a single user, using the userid
    public User getUser(Integer userId){
        try {
            String sql = "SELECT * FROM users WHERE user_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, userId);
            ResultSet result = pstmt.executeQuery();

            if(result.next()) {
                User user = new User(result.getInt("user_id"), result.getString("email"),
                        result.getString("password"), result.getString("role"), result.getString("firstname"),
                        result.getString("lastname"), result.getString("address"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //retrieve all users in the table
    public List<User> getAllUsers(){
        try{
            String sql = "SELECT * FROM users ORDER BY user_id";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();

            List<User> userList = new ArrayList<>();
            while(result.next()){
                User user = new User(result.getInt("user_id"), result.getString("email"),
                        result.getString("password"), result.getString("role"), result.getString("firstname"),
                        result.getString("lastname"), result.getString("address"));
                userList.add(user);
            }

            return userList;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public String getUserRole(int userId){
        try{
            String sql = "SELECT role FROM users WHERE user_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, userId);
            ResultSet result = pstmt.executeQuery();
            result.next();
            return result.getString("role");
        }catch(SQLException e){
            e.printStackTrace();
        }
        return "";
    }

    //update a single user, use values passed through a User object
    public void update(User user){
        try{
            String sql = "UPDATE users SET email=?, password=?, role=?, firstname=?, lastname=?, address=? WHERE user_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getRole());
            pstmt.setString(4, user.getFirstName());
            pstmt.setString(5, user.getLastName());
            pstmt.setString(6, user.getAddress());
            pstmt.setInt(7, user.getUserId());
            pstmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
