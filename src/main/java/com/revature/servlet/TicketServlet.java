package com.revature.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revature.exceptions.InvalidTicketException;
import com.revature.persistence.TicketDao;
import com.revature.pojo.Ticket;
import com.revature.service.TicketService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class TicketServlet extends HttpServlet {
    private TicketService service;
    private ObjectMapper mapper;

    @Override
    public void init() throws ServletException {
        service = new TicketService(new TicketDao());
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    //Get user's tickets
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Make sure user is logged in
        String idCookie = CookieHandler.getCookieValue("userId", req.getCookies());
        if(!idCookie.equals("")){
            int userId = Integer.parseInt(idCookie);

            //Get the list of tickets, filter by type and status when appropriate
            List<Ticket> tickets;
            if(req.getParameter("type") != null){
                tickets = service.getTicketsByType(userId, req.getParameter("type"), req.getParameter("status"));
            }else {
                tickets = service.getTickets(userId, req.getParameter("status"));
            }
            String ticketsString = mapper.writeValueAsString(tickets);

            //return tickets
            resp.setStatus(200);
            resp.getWriter().println(ticketsString);

        }else{
            resp.setStatus(401);
            resp.getWriter().println("You are not logged in");
        }
    }

    //Create a new ticket
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Make sure user is logged in
        String idCookie = CookieHandler.getCookieValue("userId", req.getCookies());
        if(!idCookie.equals("")){
            int userId = Integer.parseInt(idCookie);

            //Get ticket info from request
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = req.getReader();

            while(reader.ready()){
                builder.append(reader.readLine());
            }

            Ticket ticket = mapper.readValue(builder.toString(), Ticket.class);
            ticket.setUserId(userId); //add user's ID to the ticket

            //create the ticket
            try {
                service.createTicket(ticket);
                resp.setStatus(201);
            }catch(InvalidTicketException e){ //Exception for invalid amount/description
                resp.setStatus(400);
                resp.getWriter().println(e.getMessage());
            }


        }else{
            resp.setStatus(401);
            resp.getWriter().println("You are not logged in");
        }
    }
}
