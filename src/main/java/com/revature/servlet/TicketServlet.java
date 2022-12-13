package com.revature.servlet;

import com.revature.service.TicketService;

import javax.servlet.http.HttpServlet;

public class TicketServlet extends HttpServlet {
    private TicketService service;

    public TicketServlet(TicketService service){
        this.service = service;
    }
}
