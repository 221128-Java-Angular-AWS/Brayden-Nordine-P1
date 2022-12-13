package com.revature.servlet;

import com.revature.service.UserService;

import javax.servlet.http.HttpServlet;

public class UserServlet extends HttpServlet {
    private UserService service;

    public UserServlet(UserService service){
        this.service = service;
    }


}
