package com.revature.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.exceptions.DuplicateUserException;
import com.revature.exceptions.InvalidEmailException;
import com.revature.exceptions.UnauthorizedException;
import com.revature.persistence.UserDao;
import com.revature.pojo.User;
import com.revature.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public class UserServlet extends HttpServlet {
    private UserService service;
    private ObjectMapper mapper;

    @Override
    public void init() throws ServletException {
        service = new UserService(new UserDao());
        mapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> params = req.getParameterMap();

        List<User> users;

        //Get a single user
        if(params.containsKey("userId")){
            User user = service.getUser(Integer.parseInt(params.get("userId")[0]));
            users = new ArrayList<User>();
            users.add(user);
        }else { //Get all users
            users = service.getAllUsers();
        }

        String usersString = mapper.writeValueAsString(users);
        resp.setStatus(200);
        resp.getWriter().println(usersString);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = req.getReader();

        while(reader.ready()){
            builder.append(reader.readLine());
        }

        User user = mapper.readValue(builder.toString(), User.class);

        try {
            service.registerUser(user);
            resp.setStatus(201);
        }catch (DuplicateUserException | InvalidEmailException e){
            resp.setStatus(400);
            resp.getWriter().print(e.getMessage());
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idCookie = CookieHandler.getCookieValue("userId", req.getCookies());
        if(idCookie != "") {
            int userId = Integer.parseInt(idCookie);
            String role = CookieHandler.getCookieValue("role", req.getCookies());

            StringBuilder builder = new StringBuilder();
            BufferedReader reader = req.getReader();

            while(reader.ready()){
                builder.append(reader.readLine());
            }

            User user = mapper.readValue(builder.toString(), User.class);
            if(!role.equals("manager") && userId != user.getUserId()){
                resp.setStatus(401);
                resp.getWriter().println("Can not edit another user's account");
                return;
            }

            try {
                service.updateUser(user, role);
            } catch (UnauthorizedException e) {
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
