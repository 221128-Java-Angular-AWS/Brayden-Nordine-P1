package com.revature.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.exceptions.IncorrectPasswordException;
import com.revature.exceptions.UserNotFoundException;
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

public class AuthenticationServlet extends HttpServlet {
    private UserService service;
    private ObjectMapper mapper;

    @Override
    public void init() throws ServletException {
        service = new UserService(new UserDao());
        mapper = new ObjectMapper();
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
            user = service.validateLogin(user);
            resp.setStatus(200);
            resp.getWriter().println(mapper.writeValueAsString(user));
            Cookie auth = new Cookie("userId", user.getUserId().toString());
            auth.setMaxAge(600);
            resp.addCookie(auth);
        } catch (UserNotFoundException | IncorrectPasswordException e) {
            resp.setStatus(401);
            resp.getWriter().println(e.getMessage());
        }
    }
}
