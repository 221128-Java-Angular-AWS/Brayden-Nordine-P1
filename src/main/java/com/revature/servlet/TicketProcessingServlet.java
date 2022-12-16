package com.revature.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revature.exceptions.UnauthorizedException;
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

public class TicketProcessingServlet extends HttpServlet {
    private TicketService service;
    private ObjectMapper mapper;

    @Override
    public void init() throws ServletException {
        service = new TicketService(new TicketDao());
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    //Get pending tickets for managers
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Make sure user is logged in as a manager
        String role = CookieHandler.getCookieValue("role", req.getCookies());
        if(role.equals("manager")) {

            //Get pending tickets, filter by userId if appropriate
            List<Ticket> tickets = null;
            if (req.getParameter("userId") != null) {
                int userId = Integer.parseInt(req.getParameter("userId"));
                tickets = service.getTickets(userId, "pending");
            } else {
                tickets = service.getTickets("pending");
            }
            String ticketsString = mapper.writeValueAsString(tickets);

            //return tickets
            resp.setStatus(200);
            resp.getWriter().println(ticketsString);
        }else{
            resp.setStatus(401);
            resp.getWriter().println("Not authorized to process tickets");
        }
    }

    //Processing tickets
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Make sure user is logged in as a manager
        String role = CookieHandler.getCookieValue("role", req.getCookies());
        if(role.equals("manager")){

            //Get updated ticket info from request
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = req.getReader();

            while(reader.ready()){
                builder.append(reader.readLine());
            }

            Ticket ticket = mapper.readValue(builder.toString(), Ticket.class);

            //Process the ticket
            try {
                service.processTicket(ticket);
                resp.setStatus(200);
            } catch (UnauthorizedException e) { //Exception for trying to process a processed ticket
                resp.setStatus(400);
                resp.getWriter().println(e.getMessage());
            }

        }else{
            resp.setStatus(401);
            resp.getWriter().println("Not authorized to process tickets");
        }
    }
}
