package com.revature.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revature.exceptions.InvalidTicketException;
import com.revature.exceptions.TicketProcessingException;
import com.revature.persistence.TicketDao;
import com.revature.pojo.Ticket;
import com.revature.pojo.User;
import com.revature.service.TicketService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        String role = "";
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("role"))
                    role = cookie.getValue();
            }
        }

        if(role.equals("manager")){
            List<Ticket> tickets = null;
            if(req.getParameter("userId") != null) {
                int userId = Integer.parseInt(req.getParameter("userId"));
                tickets = service.getTickets(userId, "pending");
            }else {
                tickets = service.getTickets("pending");
            }
            String ticketsString = mapper.writeValueAsString(tickets);

            resp.setStatus(200);
            resp.getWriter().println(ticketsString);
        }else if(role.equals("employee")){
            int userId = -1;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userId"))
                    userId = Integer.parseInt(cookie.getValue());
            }

            List<Ticket> tickets = service.getTickets(userId, req.getParameter("status"));
            String ticketsString = mapper.writeValueAsString(tickets);

            resp.setStatus(200);
            resp.getWriter().println(ticketsString);

        }else{
            resp.setStatus(401);
            resp.getWriter().println("You are not logged in");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        Integer userId = null;
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userId"))
                    userId = Integer.parseInt(cookie.getValue());
            }
        }

        if(userId != null){
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = req.getReader();

            while(reader.ready()){
                builder.append(reader.readLine());
            }

            Ticket ticket = mapper.readValue(builder.toString(), Ticket.class);
            ticket.setUserId(userId);

            try {
                service.createTicket(ticket);
                resp.setStatus(201);
            }catch(InvalidTicketException e){
                resp.setStatus(400);
                resp.getWriter().println(e.getMessage());
            }


        }else{
            resp.setStatus(401);
            resp.getWriter().println("You are not logged in");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        String role = "";
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("role"))
                    role = cookie.getValue();
            }
        }

        if(role.equals("manager")){
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = req.getReader();

            while(reader.ready()){
                builder.append(reader.readLine());
            }

            Ticket ticket = mapper.readValue(builder.toString(), Ticket.class);

            try {
                service.processTicket(ticket);
                resp.setStatus(200);
            } catch (TicketProcessingException e) {
                resp.setStatus(400);
                resp.getWriter().println(e.getMessage());
            }

        }else{
            resp.setStatus(401);
            resp.getWriter().println("Not authorized to process tickets");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
