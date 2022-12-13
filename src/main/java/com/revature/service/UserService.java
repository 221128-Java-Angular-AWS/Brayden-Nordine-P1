package com.revature.service;

import com.revature.exceptions.IncorrectPasswordException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.persistence.UserDao;
import com.revature.pojo.User;

import java.util.List;

public class UserService {
    private UserDao dao;

    public UserService(UserDao dao){
        this.dao = dao;
    }


    public void registerUser(User user){
        dao.create(user);
    }

    public User validateLogin(String username, String password) throws UserNotFoundException, IncorrectPasswordException {
        return dao.verify(username, password);
    }

    public User getUser(int userId){
        return dao.getUser(userId);
    }

    public List<User> getAllUsers(){
        return dao.getAllUsers();
    }

    public void updateUser(User user){
        dao.update(user);
    }

    public void deleteUser(User user){
        dao.delete(user);
    }

}
