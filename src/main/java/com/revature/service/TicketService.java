package com.revature.service;

import com.revature.exceptions.InvalidTicketException;
import com.revature.exceptions.TicketProcessingException;
import com.revature.persistence.TicketDao;
import com.revature.pojo.Ticket;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TicketService {
    private TicketDao dao;

    public TicketService(TicketDao dao){
        this.dao = dao;
    }

    public void createTicket(Ticket ticket) throws InvalidTicketException {
        if(ticket.getAmount() == null || ticket.getAmount() <= 0){
            throw new InvalidTicketException("Can not submit a ticket with no reimbursement amount");
        }
        if(ticket.getDescription() == null || ticket.getDescription().equals("")){
            throw new InvalidTicketException("Can not submit a ticket with no description");
        }

        ticket.setStatus("pending");
        ticket.setDateSubmitted(LocalDateTime.now());
        dao.create(ticket);
    }

    public List<Ticket> getTickets(){
        return dao.getAllTickets();
    }

    public List<Ticket> getTickets(String status){
        if(status != null) {
            return dao.getAllTickets(status);
        }
        return dao.getAllTickets();
    }

    public List<Ticket> getTickets(int userId){
        return dao.getAllTicketsForUser(userId);
    }

    public List<Ticket> getTickets(int userId, String status){
        if(status != null) {
            return dao.getAllTicketsForUser(userId, status);
        }
        return dao.getAllTicketsForUser(userId);
    }

    public List<Ticket> getTicketsByType(int userId, String type){
        return dao.getAllTicketsForUserByType(userId, type);
    }

    public List<Ticket> getTicketsByType(int userId, String type, String status){
        if(status != null) {
            return dao.getAllTicketsForUserByType(userId, type, status);
        }
        return dao.getAllTicketsForUserByType(userId, type);
    }

    public void processTicket(Ticket ticket) throws TicketProcessingException {
        if(dao.getTicketStatus(ticket.getTicketId()).equals("pending")) {
            if (ticket.getStatus().equals("approved") || ticket.getStatus().equals("rejected")) {
                ticket.setDateProcessed(LocalDateTime.now());
                dao.update(ticket);
            }
            else{
                throw new TicketProcessingException("Not a valid ticket status");
            }
        }else{
            throw new TicketProcessingException("Processed tickets can not be processed");
        }
    }
}
