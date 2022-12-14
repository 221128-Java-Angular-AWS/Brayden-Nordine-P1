package com.revature.service;

import com.revature.persistence.TicketDao;
import com.revature.pojo.Ticket;

import java.util.List;

public class TicketService {
    private TicketDao dao;

    public TicketService(TicketDao dao){
        this.dao = dao;
    }

    public void createTicket(Ticket ticket){
        dao.create(ticket);
    }

    public List<Ticket> getAllTickets(){
        return dao.getAllTickets();
    }

    public List<Ticket> getAllPendingTickets(){
        return dao.getAllPendingTickets();
    }

    public List<Ticket> getAllProcessedTickets(){
        return dao.getAllProcessedTickets();
    }

    public void updateTicket(Ticket ticket){
        dao.update(ticket);
    }

    public void deleteTicket(Ticket ticket){
        dao.delete(ticket.getTicketId());
    }

    public void deleteTicket(int ticketId){
        dao.delete(ticketId);
    }
}
