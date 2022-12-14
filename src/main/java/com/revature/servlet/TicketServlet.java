package com.revature.servlet;

import com.revature.persistence.TicketDao;
import com.revature.service.TicketService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class TicketServlet extends HttpServlet {
    private TicketService service;

    @Override
    public void init() throws ServletException {
        service = new TicketService(new TicketDao());
    }
}
