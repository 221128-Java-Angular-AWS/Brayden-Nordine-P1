package com.revature.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.exceptions.DuplicateUserException;
import com.revature.exceptions.InvalidEmailException;
import com.revature.exceptions.UnauthorizedException;
import com.revature.persistence.UserDao;
import com.revature.pojo.User;
import com.revature.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserServlet extends HttpServlet {
    private UserService service;
    private ObjectMapper mapper;

    @Override
    public void init() throws ServletException {
        service = new UserService(new UserDao());
        mapper = new ObjectMapper();
    }

    //Get user data
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users;
        String idCookie = CookieHandler.getCookieValue("userId", req.getCookies());

        //make sure user is logged in
        if(!idCookie.equals("")){
            //Call different services based on user's role and filters
            String role = CookieHandler.getCookieValue("role", req.getCookies());
            if(!role.equals("manager")) {
                users = new ArrayList<>();
                users.add(service.getUser(Integer.parseInt(idCookie)));
            }else if (req.getParameter("userId") != null){
                users = new ArrayList<>();
                users.add(service.getUser(Integer.parseInt(req.getParameter("userId"))));
            }else{
                users = service.getAllUsers();
            }

            //return user info
            String usersString = mapper.writeValueAsString(users);
            resp.setStatus(200);
            resp.getWriter().println(usersString);
        }else{
            resp.setStatus(401);
            resp.getWriter().println("Not logged in");
        }

    }

    //Create new user
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Get user data from the request
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = req.getReader();

        while(reader.ready()){
            builder.append(reader.readLine());
        }

        User user = mapper.readValue(builder.toString(), User.class);

        //Try to create the user
        try {
            service.registerUser(user);
            resp.setStatus(201);
        }catch (DuplicateUserException | InvalidEmailException e){ //Exceptions for duplicate/invalid emails
            resp.setStatus(400);
            resp.getWriter().print(e.getMessage());
        }

    }

    //Update an existing user
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Make sure user is logged in
        String idCookie = CookieHandler.getCookieValue("userId", req.getCookies());
        if(!idCookie.equals("")) {
            int userId = Integer.parseInt(idCookie);
            String role = CookieHandler.getCookieValue("role", req.getCookies());

            //Get updated user info from the request
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = req.getReader();

            while(reader.ready()){
                builder.append(reader.readLine());
            }

            User user = mapper.readValue(builder.toString(), User.class);

            //Make sure an employee can't update another user's info
            if(!role.equals("manager") && userId != user.getUserId()){
                resp.setStatus(401);
                resp.getWriter().println("Can not edit another user's account");
                return;
            }

            //update the info
            try {
                service.updateUser(user, role);
            } catch (UnauthorizedException e) { //Exception for trying to change your role as an employee
                resp.setStatus(401);
                resp.getWriter().println(e.getMessage());
                return;
            }

            resp.setStatus(200);
        }else{
            resp.setStatus(401);
            resp.getWriter().println("Not logged in");
        }
    }
}
