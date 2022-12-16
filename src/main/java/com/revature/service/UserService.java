package com.revature.service;

import com.revature.exceptions.*;
import com.revature.persistence.UserDao;
import com.revature.pojo.User;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {
    private UserDao dao;

    public UserService(UserDao dao){
        this.dao = dao;
    }


    public void registerUser(User user) throws DuplicateUserException, InvalidEmailException {
        //Make sure the email is a valid email address
        Pattern regex = Pattern.compile("^(.+)@(.+)$");
        Matcher matcher = regex.matcher(user.getEmail());
        if(matcher.find()){ //if valid email

            if(user.getRole() == null)
                user.setRole("employee");

            dao.create(user);
        }else{ //if invalid email
            throw new InvalidEmailException("Email address given was not a valid email address");
        }
    }

    public User validateLogin(User user) throws UserNotFoundException, IncorrectPasswordException {
        return dao.verify(user.getEmail(), user.getPassword());
    }

    public User getUser(int userId){
        return dao.getUser(userId);
    }

    public List<User> getAllUsers(){
        return dao.getAllUsers();
    }

    public void updateUser(User user, String role) throws UnauthorizedException {
        if(!dao.getUserRole(user.getUserId()).equals(user.getRole()) && !role.equals("manager")){
            throw new UnauthorizedException("Not authorized to change your role");
        }
        dao.update(user);
    }

}
